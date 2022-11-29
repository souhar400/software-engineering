#include <jni.h>
#include <string>
#include <fluidsynth.h>
#include <unistd.h>

bool initialized = false;
bool soundfontLoaded = false;
bool loopsPlaying[12];
fluid_settings_t *settings;
fluid_synth_t *synth = NULL;
fluid_audio_driver_t *adriver = NULL;

jstring getJString(JNIEnv *env, char *string);
void initialize();
void cleanup();


extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_MainActivity_playFluidSynthSound(JNIEnv *env, jobject thiz,
                                                             jstring soundfont_path, jint channel,
                                                             jint key, jint velocity, jint preset,
                                                             jboolean toggle) {
    const char *soundfontPath = env->GetStringUTFChars(soundfont_path, nullptr);
    initialize();
    if (!initialized) {
        return;
    }

    // Load the soundfont
    if (!soundfontLoaded && fluid_synth_sfload(synth, soundfontPath, 0) == -1) {
        fprintf(stderr, "Failed to load soundfont\n");
        cleanup();
        return;
    }

    fluid_synth_program_change(synth, channel, preset);

    // Play the sound
    fluid_synth_noteoff(synth, channel, key);

    if (toggle && loopsPlaying[preset]) {
        loopsPlaying[preset] = false;
        return;
    }

    fluid_synth_noteon(synth, channel, key, velocity);
    loopsPlaying[preset] = true;
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_MainActivity_cleanupFluidSynth(JNIEnv *env, jobject thiz) {
    cleanup();
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_ButtonData_setChannelVolume(JNIEnv *env, jobject thiz, jint channel, jint volume) {
    fluid_synth_cc(synth, channel, 7, volume);
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_MainMenu_adjustGain(JNIEnv *env, jobject thiz, jfloat gain) {
    fluid_synth_set_gain(synth, gain);
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_MainActivity_playMidi(JNIEnv *env, jobject thiz, jstring midi_file, jstring soundfont_file) {
    const char *midi_file_path = env->GetStringUTFChars(midi_file, nullptr);
    const char *soundfont_file_path = env->GetStringUTFChars(soundfont_file, nullptr);

    fluid_settings_t* fluidSettings;
    fluid_synth_t* fluidSynth;
    fluid_player_t* fluidPlayer;
    fluid_audio_driver_t* fluidAudioDriver;

    fluidSettings = new_fluid_settings();
    fluidSynth = new_fluid_synth(fluidSettings);
    fluidPlayer = new_fluid_player(fluidSynth);

    if (fluidPlayer == NULL) {
        return;
    }
    // Register midi and soundfont file
    if (fluid_is_midifile(midi_file_path)) {
        if (fluid_player_add(fluidPlayer, midi_file_path) == FLUID_FAILED) {
            return;
        }
    } else {
        return;
    }
    if (fluid_is_soundfont(soundfont_file_path)) {
        if (fluid_synth_sfload(fluidSynth, soundfont_file_path, 0) == -1) {
            return;
        }
    } else {
        return;
    }
    fluidAudioDriver = new_fluid_audio_driver(fluidSettings, fluidSynth);

    // Play midi file
    if (fluid_player_play(fluidPlayer) == FLUID_FAILED) {
        return;
    }
    // Wait for termination
    fluid_player_join(fluidPlayer);

    // Cleanup
    delete_fluid_audio_driver(fluidAudioDriver);
    delete_fluid_player(fluidPlayer);
    delete_fluid_synth(fluidSynth);
    delete_fluid_settings(fluidSettings);
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

void initialize() {
    if (!initialized) {
        // Create the settings object with default values
        settings = new_fluid_settings();
        if (settings == NULL) {
            fprintf(stderr, "Failed to create the settings\n");
            cleanup();
            return;
        }

        // Create the synthesizer
        synth = new_fluid_synth(settings);
        if (synth == NULL) {
            fprintf(stderr, "Failed to create the synthesizer\n");
            cleanup();
            return;
        }

        // Create the audio driver to play the synthesizer
        adriver = new_fluid_audio_driver(settings, synth);
        if (adriver == NULL) {
            fprintf(stderr, "Failed to create the audio driver\n");
            cleanup();
            return;
        }
        initialized = true;
    }
}

void cleanup() {
    if (adriver) {
        delete_fluid_audio_driver(adriver);
    }

    if (synth) {
        delete_fluid_synth(synth);
    }

    if (settings) {
        delete_fluid_settings(settings);
    }
    initialized = false;
}