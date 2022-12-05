#include <jni.h>
#include <string>
#include <fluidsynth.h>
#include <unistd.h>

/*
 * Use for debug purposes. Returns jstring that can be handed over to Java method to print it out
 * because console output is not possible here.
 */
jstring getJString(JNIEnv *env, char *string) {
    char *buf = (char *) malloc(200);
    strcpy(buf, string); // with the null terminator the string adds up to 10 bytes
    return env->NewStringUTF(buf);
}

#define NUMBER_OF_BUTTONS 20

struct button_data {
    fluid_settings_t *fluidSettings;
    fluid_synth_t *fluidSynth;
    fluid_player_t *fluidPlayer;
    fluid_audio_driver_t *fluidAudioDriver;
    bool initialized = false;
    bool loop = false;
};

button_data buttonData[NUMBER_OF_BUTTONS];

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_services_SynthService_play__Ljava_lang_String_2Ljava_lang_String_2IZ(
        JNIEnv *env, jobject thiz, jstring midi_path, jstring soundfont_path, jint button_number,
        jboolean toggle) {
    const char *midi_file_path = env->GetStringUTFChars(midi_path, nullptr);
    const char *soundfont_file_path = env->GetStringUTFChars(soundfont_path, nullptr);

    if (!buttonData[button_number].initialized) {
        buttonData[button_number].fluidSettings = new_fluid_settings();
        if (buttonData[button_number].fluidSettings == NULL) {
            fprintf(stderr, "Failed to create the settings\n");
            return;
        }

        buttonData[button_number].fluidSynth = new_fluid_synth(
                buttonData[button_number].fluidSettings);
        if (buttonData[button_number].fluidSynth == NULL) {
            fprintf(stderr, "Failed to create the synthesizer\n");
            return;
        }

        // Create the player
        buttonData[button_number].fluidPlayer = new_fluid_player(
                buttonData[button_number].fluidSynth);
        if (buttonData[button_number].fluidPlayer == NULL) {
            fprintf(stderr, "Failed to create the player");
            return;
        }

        // Register midi file
        if (fluid_is_midifile(midi_file_path)) {
            if (fluid_player_add(buttonData[button_number].fluidPlayer, midi_file_path) ==
                FLUID_FAILED) {
                fprintf(stderr, "Failed to add midi file\n");
                return;
            }
        } else {
            fprintf(stderr, "Not a midi file\n");
            return;
        }

        // Register soundfont file
        if (fluid_is_soundfont(soundfont_file_path)) {
            if (fluid_synth_sfload(buttonData[button_number].fluidSynth, soundfont_file_path, 0) ==
                -1) {
                fprintf(stderr, "Failed to load the soundfont file\n");
                return;
            }
        } else {
            fprintf(stderr, "Not a soundfont file\n");
            return;
        }

        // Create the audio driver to play the synthesizer
        buttonData[button_number].fluidAudioDriver = new_fluid_audio_driver(
                buttonData[button_number].fluidSettings, buttonData[button_number].fluidSynth);
        if (buttonData[button_number].fluidAudioDriver == NULL) {
            fprintf(stderr, "Failed to create the audio driver\n");
            return;
        }

        // Play midi file
        if (fluid_player_play(buttonData[button_number].fluidPlayer) == FLUID_FAILED) {
            fprintf(stderr, "Failed to play midi file\n");
            return;
        }
        buttonData[button_number].initialized = true;
    } else {
        fluid_player_stop(buttonData[button_number].fluidPlayer);
        fluid_player_seek(buttonData[button_number].fluidPlayer, 0);
    }
}
extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_services_SynthService_play__Ljava_lang_String_2IIIIZ(JNIEnv *env,
                                                                                 jobject thiz,
                                                                                 jstring soundfont_path,
                                                                                 jint button_number,
                                                                                 jint key,
                                                                                 jint velocity,
                                                                                 jint preset,
                                                                                 jboolean toggle) {
    const char *soundfontPath = env->GetStringUTFChars(soundfont_path, nullptr);

    if (!buttonData[button_number].initialized) {
        // Create the settings object with default values
        buttonData[button_number].fluidSettings = new_fluid_settings();
        if (buttonData[button_number].fluidSettings == NULL) {
            fprintf(stderr, "Failed to create the settings\n");
            return;
        }

        // Create the synthesizer
        buttonData[button_number].fluidSynth = new_fluid_synth(
                buttonData[button_number].fluidSettings);
        if (buttonData[button_number].fluidSynth == NULL) {
            fprintf(stderr, "Failed to create the synthesizer\n");
            return;
        }

        // Create the audio driver to play the synthesizer
        buttonData[button_number].fluidAudioDriver = new_fluid_audio_driver(
                buttonData[button_number].fluidSettings, buttonData[button_number].fluidSynth);
        if (buttonData[button_number].fluidAudioDriver == NULL) {
            fprintf(stderr, "Failed to create the audio driver\n");
            return;
        }

        // Load the soundfont
        if (fluid_synth_sfload(buttonData[button_number].fluidSynth, soundfontPath, 0) == -1) {
            fprintf(stderr, "Failed to load soundfont\n");
            return;
        }

        buttonData[button_number].initialized = true;
    }

    fluid_synth_program_change(buttonData[button_number].fluidSynth, button_number, preset);

    // Play the sound
    fluid_synth_noteoff(buttonData[button_number].fluidSynth, button_number, key);

    if (toggle && buttonData[button_number].loop) {
        buttonData[button_number].loop = false;
        return;
    }

    fluid_synth_noteon(buttonData[button_number].fluidSynth, button_number, key, velocity);
    buttonData[button_number].loop = true;
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_model_ButtonData_setChannelVolume(JNIEnv *env, jobject thiz,
                                                              jint button_number, jint volume) {
    fluid_synth_cc(buttonData[button_number].fluidSynth, button_number, 7, volume);
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