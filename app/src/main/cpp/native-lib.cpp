#include <jni.h>
#include <string>
#include <fluidsynth.h>
#include <unistd.h>

extern "C"
JNIEXPORT void JNICALL
Java_de_gruppe_e_klingklang_MainActivity_playFluidSynthSound(JNIEnv *env, jobject thiz,
                                                             jstring soundfont_path,
                                                             jint sound_length) {
    fluid_settings_t *settings;
    fluid_synth_t *synth = NULL;
    fluid_audio_driver_t *adriver = NULL;
    const char *soundfontPath = env->GetStringUTFChars(soundfont_path, nullptr);

    // Create the settings object with default values
    settings = new_fluid_settings();
    if (settings == NULL) {
        fprintf(stderr, "Failed to create the settings\n");
        goto cleanup;
    }

    // Create the synthesizer
    synth = new_fluid_synth(settings);
    if (synth == NULL) {
        fprintf(stderr, "Failed to create the synthesizer\n");
        goto cleanup;
    }

    // Create the audio driver to play the synthesizer
    adriver = new_fluid_audio_driver(settings, synth);
    if (adriver == NULL) {
        fprintf(stderr, "Failed to create the audio driver\n");
        goto cleanup;
    }

    // Load the soundfont
    if (fluid_synth_sfload(synth, soundfontPath, 1) == -1) {
        fprintf(stderr, "Failed to load soundfont\n");
        goto cleanup;
    }

    // Play the sound
    fluid_synth_noteon(synth, 0, 62, 127);
    sleep(sound_length);
    fluid_synth_noteoff(synth, 0, 62);

    cleanup:
    if (adriver) {
        delete_fluid_audio_driver(adriver);
    }

    if (synth) {
        delete_fluid_synth(synth);
    }

    if (settings) {
        delete_fluid_settings(settings);
    }
}