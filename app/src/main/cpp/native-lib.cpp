#include <jni.h>
#include <string>
#include <fluidsynth.h>
#include <unistd.h>

bool initialized = false;
fluid_settings_t *settings;
fluid_synth_t *synth = NULL;
fluid_audio_driver_t *adriver = NULL;

void initialize();

void cleanup();

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_MainActivity_playFluidSynthSound(JNIEnv *env, jobject thiz,
                                                             jstring soundfont_path, jint channel,
                                                             jint key, jint velocity) {
    initialize();

    const char *soundfontPath = env->GetStringUTFChars(soundfont_path, nullptr);
    // Load the soundfont
    if (fluid_synth_sfload(synth, soundfontPath, 1) == -1) {
        fprintf(stderr, "Failed to load soundfont\n");
        cleanup();
    }

    // Play the sound
    fluid_synth_noteoff(synth, channel, key);
    fluid_synth_noteon(synth, channel, key, velocity);
}

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_MainActivity_cleanupFluidSynth(JNIEnv *env, jobject thiz) {
    cleanup();
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