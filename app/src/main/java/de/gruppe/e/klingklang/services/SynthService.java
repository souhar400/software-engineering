package de.gruppe.e.klingklang.services;

import android.app.Activity;
import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.Recorder;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class SynthService {
    private final Activity activity;

    public SynthService(Activity activity) {
        this.activity = activity;
    }

    public void register(ButtonData buttonData) {
        String tempSoundfontPath = copyAssetToTmpFile(buttonData.getSoundfontPath());
        String tempMidiPath = null;
        if (buttonData.getMidiPath() != null) {
            tempMidiPath = copyAssetToTmpFile(buttonData.getMidiPath());
        }
        register(buttonData.getButtonNumber(), tempSoundfontPath, tempMidiPath, buttonData.isLoop());
    }



    public void play(ButtonData buttonData) {
        System.out.println("BNUMBER: " + buttonData.getButtonNumber());
        if (buttonData.getMidiPath() != null) {
            // Play midi
            play(buttonData.getButtonNumber());
        } else {
            // Play soundfont
            play(buttonData.getButtonNumber(), buttonData.getKey(), buttonData.getVelocity(), buttonData.getPreset());
        }

        Recorder.getInstance().addTrackComponent(
                buttonData.getMidiPath(),
                buttonData.getSoundfontPath(),
                buttonData.getButtonNumber(),
                buttonData.getKey(),
                buttonData.getVelocity(),
                buttonData.getPreset(),
                buttonData.isLoop());
    }

    public void play(String midiPath, String soundfontPath, int buttonNumber, int key, int velocity, int preset, boolean toggle) {
        if (!midiPath.equals("null")) {
            play(buttonNumber);
        } else {
            play(buttonNumber, key, velocity, preset);
        }
    }

    private native void register(int buttonNumber, String soundfontPath, String midiPath, boolean isLoop);

    private native void play(int buttonNumber);

    private native void play(int buttonNumber, int key, int velocity, int preset);

    /**
     * Turns a assets file that is a series of bytes in the compressed APK into a playable temporary
     * file.
     *
     * @param fileName Name of the .sf2 file in /assets
     * @return Path of the temporary file
     */
    public String copyAssetToTmpFile(String fileName) {
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
