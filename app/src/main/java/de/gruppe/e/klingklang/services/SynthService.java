package de.gruppe.e.klingklang.services;

import android.app.Activity;
import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SynthService {
    private final Activity Activity;

    public SynthService(Activity Activity){
        this.Activity = Activity;
    }

    /**
     * Cleans up the driver, synth and settings.
     */
    public native void cleanupFluidSynth();

    /**
     * Turns a assets file that is a series of bytes in the compressed APK into a playable temporary
     * file.
     *
     * @param fileName Name of the .sf2 file in /assets
     * @return Path of the temporary file
     * @throws IOException IOException when file does not exist or is not openable
     */
    public String copyAssetToTmpFile(String fileName) throws IOException {
        try (InputStream is = Activity.getAssets().open(fileName)) {
            String tempFileName = "tmp_" + fileName;
            try (FileOutputStream fos = Activity.openFileOutput(tempFileName, Context.MODE_PRIVATE)) {
                int bytes_read;
                byte[] buffer = new byte[4096];
                while ((bytes_read = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytes_read);
                }
            }
            return Activity.getFilesDir() + "/" + tempFileName;
        }
    }

    /**
     * Native method that calls methods from the FluidSynth library to play a synth.
     *
     * @param soundfontPath Path of the .sf2 soundfont file to be played (in /assets folder)
     * @param channel       MIDI channel number (0 - 16)
     * @param key           MIDI note number (0 - 127)
     * @param velocity      MIDI velocity (0 - 127, 0 = note off)
     */
    public native void playFluidSynthSound(String soundfontPath, int channel, int key, int velocity, int preset, boolean toggle);

}
