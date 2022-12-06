#include <jni.h>
#include <string>
#include <fluidsynth.h>
#include <unistd.h>

jstring getJString(JNIEnv *env, char *string);

void cleanup(int button_number);

#define NUMBER_OF_BUTTONS 20

struct button_data {
    const char *soundfontPath = NULL;
    const char *midiPath = NULL;
    int volume = 100;
    fluid_settings_t *fluidSettings = NULL;
    fluid_synth_t *fluidSynth = NULL;
    fluid_player_t *fluidPlayer = NULL;
    fluid_audio_driver_t *fluidAudioDriver = NULL;
    bool isLoop = false;
    bool isClicked = false;
    bool initialized = false;
};

button_data buttonData[NUMBER_OF_BUTTONS];

void initialize(int button_number) {
    if (buttonData[button_number].initialized) {
        return;
    }

    // Create the settings object with default values
    buttonData[button_number].fluidSettings = new_fluid_settings();
    if (buttonData[button_number].fluidSettings == NULL) {
        fprintf(stderr, "Failed to create the settings\n");
        return;
    }

    // Create the synthesizer
    buttonData[button_number].fluidSynth = new_fluid_synth(buttonData[button_number].fluidSettings);
    if (buttonData[button_number].fluidSynth == NULL) {
        fprintf(stderr, "Failed to create the synthesizer\n");
        return;
    }

    // Register soundfont file
    if (fluid_is_soundfont(buttonData[button_number].soundfontPath)) {
        if (fluid_synth_sfload(buttonData[button_number].fluidSynth,
                               buttonData[button_number].soundfontPath, 0) ==
            -1) {
            fprintf(stderr, "Failed to load the soundfont file\n");
            return;
        }
    } else {
        fprintf(stderr, "Not a soundfont file\n");
        return;
    }

    // Register midi file
    if (buttonData[button_number].midiPath) {
        // Create the player
        buttonData[button_number].fluidPlayer = new_fluid_player(
                buttonData[button_number].fluidSynth);
        if (buttonData[button_number].fluidPlayer == NULL) {
            fprintf(stderr, "Failed to create the player");
            return;
        }

        if (buttonData[button_number].isLoop) {
            // Set loop
            fluid_player_set_loop(buttonData[button_number].fluidPlayer, -1);
        }

        if (fluid_is_midifile(buttonData[button_number].midiPath)) {
            if (fluid_player_add(buttonData[button_number].fluidPlayer,
                                 buttonData[button_number].midiPath) == FLUID_FAILED) {
                fprintf(stderr, "Failed to add midi file\n");
                return;
            }
        } else {
            fprintf(stderr, "Not a midi file\n");
            return;
        }
    } else {
        // Create the audio driver to play the synthesizer
        buttonData[button_number].fluidAudioDriver = new_fluid_audio_driver(
                buttonData[button_number].fluidSettings, buttonData[button_number].fluidSynth);
        if (buttonData[button_number].fluidAudioDriver == NULL) {
            fprintf(stderr, "Failed to create the audio driver\n");
            cleanup(button_number);
            return;
        }
    }

    buttonData[button_number].isClicked = false;
    buttonData[button_number].initialized = true;
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_services_SynthService_register(JNIEnv *env, jobject thiz,
                                                           jint button_number,
                                                           jstring soundfont_path,
                                                           jstring midi_path, jboolean is_loop) {
    buttonData[button_number].soundfontPath = env->GetStringUTFChars(soundfont_path, nullptr);
    if (midi_path) {
        buttonData[button_number].midiPath = env->GetStringUTFChars(midi_path, nullptr);
    }
    buttonData[button_number].isLoop = is_loop;

    initialize(button_number);
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_services_SynthService_play__I(JNIEnv *env, jobject thiz,
                                                          jint button_number) {
    if (buttonData[button_number].initialized) {
        if (buttonData[button_number].isClicked) {
            // Stop playing
            cleanup(button_number);
            // The midi button has to be re-registered because of a bug in fluidsynth
            initialize(button_number);
        } else {
            // Create the audio driver to play the synthesizer (audio unfortunately instantly start playing)
            buttonData[button_number].fluidAudioDriver = new_fluid_audio_driver(
                    buttonData[button_number].fluidSettings, buttonData[button_number].fluidSynth);
            if (buttonData[button_number].fluidAudioDriver == NULL) {
                fprintf(stderr, "Failed to create the audio driver\n");
                cleanup(button_number);
                return;
            }
            fluid_synth_cc(buttonData[button_number].fluidSynth, 0, 7,
                           buttonData[button_number].volume);

            // Play midi file (audio driver already plays it automatically)
            /* if (fluid_player_play(buttonData[button_number].fluidPlayer) == FLUID_FAILED) {
                fprintf(stderr, "Failed to play midi file\n");
                cleanup(button_number);
                return;
            } */
            buttonData[button_number].isClicked = true;
        }
    }
}
extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_services_SynthService_play__IIII(JNIEnv *env, jobject thiz,
                                                             jint button_number, jint key,
                                                             jint velocity, jint preset) {
    if (buttonData[button_number].initialized) {
        fluid_synth_program_change(buttonData[button_number].fluidSynth, button_number, preset);

        // Play the sound
        fluid_synth_noteoff(buttonData[button_number].fluidSynth, button_number, key);

        if (buttonData[button_number].isLoop && buttonData[button_number].isClicked) {
            buttonData[button_number].isClicked = false;
            return;
        }

        fluid_synth_cc(buttonData[button_number].fluidSynth, button_number, 7,
                       buttonData[button_number].volume);
        fluid_synth_noteon(buttonData[button_number].fluidSynth, button_number, key, velocity);
        buttonData[button_number].isClicked = true;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_model_ButtonData_setChannelVolume(JNIEnv *env, jobject thiz,
                                                              jint button_number, jint volume) {
    buttonData[button_number].volume = volume;
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_view_MainMenu_adjustGain(JNIEnv *env, jobject thiz, jfloat gain) {
    for (int i = 0; i < NUMBER_OF_BUTTONS; ++i) {
        if (buttonData[i].fluidSynth != NULL) {
            fluid_synth_set_gain(buttonData[i].fluidSynth, gain);
        }
    }
}

void cleanup(int button_number) {
    //fluid_player_stop(buttonData[button_number].fluidPlayer);
    //fluid_player_seek(buttonData[button_number].fluidPlayer, 0);
    if (buttonData[button_number].fluidAudioDriver) {
        delete_fluid_audio_driver(buttonData[button_number].fluidAudioDriver);
    }
    if (buttonData[button_number].fluidPlayer) {
        delete_fluid_player(buttonData[button_number].fluidPlayer);
    }
    if (buttonData[button_number].fluidSynth) {
        delete_fluid_synth(buttonData[button_number].fluidSynth);
    }
    if (buttonData[button_number].fluidSettings) {
        delete_fluid_settings(buttonData[button_number].fluidSettings);
    }
    buttonData[button_number].initialized = false;
}

/*
 * Use for debug purposes. Returns jstring that can be handed over to Java method to print it out
 * because console output is not possible here.
 */
jstring getJString(JNIEnv *env, char *string) {
    char *buf = (char *) malloc(200);
    strcpy(buf, string); // with the null terminator the string adds up to 10 bytes
    return env->NewStringUTF(buf);
}