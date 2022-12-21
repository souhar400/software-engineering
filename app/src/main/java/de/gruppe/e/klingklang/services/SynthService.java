package de.gruppe.e.klingklang.services;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.Recorder;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class SynthService {
    private final Activity activity;

    private List<ButtonData> buttons = new ArrayList<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

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
        boolean add = true;
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).getButtonNumber() == buttonData.getButtonNumber()) {
                add = false;
            }
        }
        if(add) {
            buttons.add(buttonData);
        }
    }

    public void play(ButtonData buttonData) {
        if (buttonData.getMidiPath() != null) {
            // Play midi
            boolean faded = false;
            for(int i = 0; i < buttons.size(); i++) {

                if (buttons.get(i).getLinearCrossfade()) {
                    faded = true;
                    ButtonData b = buttons.get(i);
                    executor.execute(() -> {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for(int j = 127; j > 0; j--) {
                            b.setDirectVolume(j);
                            try {
                                sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        play(b.getButtonNumber());
                    });
                    b.setLinearCrossfade(!b.getLinearCrossfade());
                    buttonData.setShowFadeOptions(false);
                    executor.execute(() -> {
                        play(buttonData.getButtonNumber());
                        for(int j = 0; j < 127; j++) {
                            buttonData.setDirectVolume(j);
                            try {
                                sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else if (buttons.get(i).getNonLinearCrossfade()) {
                    faded = true;
                    ButtonData b = buttons.get(i);
                    executor.execute(() -> {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for(double j = 1; j > 0; j -= 0.05) {
                            b.setDirectVolume(Math.toIntExact(Math.round(Math.sqrt(j) * 127)));
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        play(b.getButtonNumber());
                    });
                    b.setNonLinearCrossfade(!b.getNonLinearCrossfade());
                    buttonData.setShowFadeOptions(false);
                    executor.execute(() -> {
                        play(buttonData.getButtonNumber());
                        for(double j = 0; j < 1; j += 0.05) {
                            buttonData.setDirectVolume(Math.toIntExact(Math.round(Math.sqrt(j) * 127)));
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            if(!faded) {
                play(buttonData.getButtonNumber());
                buttonData.setShowFadeOptions(true);
            }
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
