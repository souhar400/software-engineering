package de.gruppe.e.klingklang.model;

import android.content.Context;

import com.google.android.gms.common.util.WorkSourceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.gruppe.e.klingklang.services.SynthService;

public class Recorder {
    Context context;
    private File currentTrackFile;
    private boolean isRecording;
    private long startOfRecording;
    List<TrackComponent> trackComponents;
    SynthService synthService;

    public Recorder(Context context, SynthService synthService) {
        this.context = context;
        this.isRecording = false;
        this.synthService = synthService;
    }

    public void startRecording() {
        System.out.println("Start Recording");
        isRecording = true;
        currentTrackFile = createTrackFile();
        trackComponents = new ArrayList<>();
        startOfRecording = System.currentTimeMillis();
    }

    public void stopRecording() {
        System.out.println("Stop Recording");
        exportTrackComponents(this.currentTrackFile);
        isRecording = false;
    }

    public void playTrack(File track) {
        System.out.println("playTrack: " + track.getName());
        System.out.println("\nFile content\n" + readFromFile(track) + "\n");
        List<TrackComponent> trackComponents = importTrackComponents(track);
        long startTime = System.currentTimeMillis();

        while (!trackComponents.isEmpty()) {
            if (System.currentTimeMillis() - startTime >= trackComponents.get(0).momentPlayed) {
                try {
                    String tempSoundfontPath = synthService.copyAssetToTmpFile(trackComponents.get(0).soundfontPath);
                    synthService.playFluidSynthSound(tempSoundfontPath, trackComponents.get(0).channel, trackComponents.get(0).key, trackComponents.get(0).velocity, trackComponents.get(0).preset, trackComponents.get(0).toggle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                trackComponents.remove(0);
            }
        }
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

    public boolean isRecording() {
        return this.isRecording;
    }

    private List<TrackComponent> importTrackComponents(File file) {
        List<TrackComponent> trackComponents = new ArrayList<>();
        String track = readFromFile(file);
        String[] trackComponentStrings = track.split("\n");

        for (String trackComponentString : trackComponentStrings) {
            String[] values = trackComponentString.split(",");
            trackComponents.add(new TrackComponent(
                    Long.parseLong(values[0]),
                    values[1],
                    Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]),
                    Integer.parseInt(values[4]),
                    Integer.parseInt(values[5]),
                    Boolean.parseBoolean(values[6])
            ));
        }
        return trackComponents;
    }

    private void exportTrackComponents(File file) {
        for (TrackComponent trackComponent : this.trackComponents) {
            writeToFile(file, String.format(
                    "%s,%s,%s,%s,%s,%s,%s\n",
                    trackComponent.momentPlayed,
                    trackComponent.soundfontPath,
                    trackComponent.channel,
                    trackComponent.key,
                    trackComponent.velocity,
                    trackComponent.preset,
                    trackComponent.toggle
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
    public File[] getTracks() {
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

    private void deleteAllTracks() {
        File[] tracks = getTracks();
        for (File track : tracks) {
            deleteFile(track);
        }
    }

    private void deleteFile(File fdelete) {
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + fdelete.getPath());
            } else {
                System.out.println("file not Deleted :" + fdelete.getPath());
            }
        }
    }

    private void writeToFile(File file, String data) {
        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(data.getBytes());
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(File file)  {
        // File file = new File(context.getFilesDir(), fileName + ".kk");
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
