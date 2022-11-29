package de.gruppe.e.klingklang.model;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Recorder {
    Context context;
    private File currentTrackFile;
    private boolean isRecording;
    private long startOfRecording;
    List<TrackComponent> trackComponents;

    public Recorder(Context context) {
        this.context = context;
        this.isRecording = false;
    }



    public void startRecording() {
        isRecording = true;
        currentTrackFile = createTrackFile();
        trackComponents = new ArrayList<>();
        startOfRecording = System.currentTimeMillis();
    }

    public void stopRecording() {

        isRecording = false;
    }

    public void debug() {
        currentTrackFile = createTrackFile();
        getTracks();
    }

    /**
     * Needs to be called in the Button Listeners for it to log when a button is pressed
     */
    public void addTrackComponent(String soundfontPath, int channel, int key, int velocity, int preset, boolean toggle) {
        if (isRecording) {
            long momentPlayed = System.currentTimeMillis() - this.startOfRecording;
            this.trackComponents.add(new TrackComponent(momentPlayed, soundfontPath, channel, key, velocity, preset, toggle));
        }
    }

    public void playTrack() {

    }

    private void exportTrackComponents() {
        for (TrackComponent trackComponent : this.trackComponents) {
            writeToFile(this.currentTrackFile, String.format(
                    "%s;%s;%s;%s;%s,%s,%s\n",
                    Long.toString(trackComponent.momentPlayed),
                    trackComponent.soundfontPath,
                    Integer.toString(trackComponent.channel),
                    Integer.toString(trackComponent.key),
                    Integer.toString(trackComponent.velocity),
                    Integer.toString(trackComponent.preset),
                    Boolean.toString(trackComponent.toggle)
            ));
        }
    }


    public File createTrackFile() {
        File file = new File(context.getFilesDir(), "Recording_" + getDate() + ".kk");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @return A File Array with all .kk files
     */
    private File[] getTracks() {
        File[] files = context.getFilesDir().listFiles();
        List<File> tracks = new ArrayList<>();

        assert files != null;
        for (File f : files) {
            if(f.getName().contains(".kk"))
                tracks.add(f);
        }
        File[] t = new File[tracks.size()];
        for (int i = 0; i < tracks.size(); i++) {
            t[i] = tracks.get(i);
        }
        return t;
    }

    private void writeToFile(File file, String data) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(data.getBytes());
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(String fileName)  {
        File file = new File(context.getFilesDir(), fileName + ".kk");
        FileInputStream inputStream;
        byte[] bytes = new byte[(int) file.length()];

        try {
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }

    /**
     * @return Example output: 2022-11-28_15-49-00
     */
    private String getDate() {
        String date = LocalDateTime.now().toString();
        date = date.replace("T", "_");
        date = date.substring(0, date.indexOf("."));
        date = date.replaceAll(":", "-");
        return date;
    }

}
