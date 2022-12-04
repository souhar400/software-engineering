package de.gruppe.e.klingklang.services;

import android.app.Activity;
import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.gruppe.e.klingklang.model.ButtonData;

public class SynthService {
    private final Activity activity;

    public SynthService(Activity activity) {
        this.activity = activity;
    }

    public void play(ButtonData buttonData) {
        String soundfontPath = copyAssetToTmpFile(buttonData.getSoundfontPath());
        if (buttonData.getMidiPath() != null) {
            // Play midi
            String midiPath = copyAssetToTmpFile(buttonData.getMidiPath());
            play(midiPath, soundfontPath, buttonData.getButtonNumber(), buttonData.isToggle());
        } else {
            play(soundfontPath, buttonData.getButtonNumber(), buttonData.getKey(), buttonData.getVelocity(), buttonData.getPreset(), buttonData.isToggle());
        }
    }

    private native void play(String midiPath, String soundfontPath, int buttonNumber, boolean toggle);

    private native void play(String soundfontPath, int buttonNumber, int key, int velocity, int preset, boolean toggle);

    /**
     * Turns a assets file that is a series of bytes in the compressed APK into a playable temporary
     * file.
     *
     * @param fileName Name of the .sf2 file in /assets
     * @return Path of the temporary file
     */
    private String copyAssetToTmpFile(String fileName) {
        try (InputStream is = activity.getAssets().open(fileName)) {
            String tempFileName = "tmp_" + fileName;
            try (FileOutputStream fos = activity.openFileOutput(tempFileName, Context.MODE_PRIVATE)) {
                int bytes_read;
                byte[] buffer = new byte[4096];
                while ((bytes_read = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytes_read);
                }
            }
            return activity.getFilesDir() + "/" + tempFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
