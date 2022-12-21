package de.gruppe.e.klingklang.model;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.arthenica.ffmpegkit.FFmpegKit.*;
import com.arthenica.ffmpegkit.*;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.viewmodel.MainActivity;
import de.gruppe.e.klingklang.viewmodel.ViewModelFactory;

public class Recorder {
    private static Recorder instance;
    Context context;
    private File currentTrackFile;
    private boolean isRecording;
    private long startOfRecording;
    List<TrackComponent> trackComponents;
    List<TrackComponent> notUntoggledTrackComponents;
    List<TrackComponent> notUntoggledTrackComponentsPreRecording;
    SynthService synthService;
    ExecutorService executor = Executors.newFixedThreadPool(1);
    MainActivity mainActivity;


    private Recorder(Context context, SynthService synthService, MainActivity mainActivity) {
        this.context = context;
        this.isRecording = false;
        this.synthService = synthService;
        trackComponents = new ArrayList<>();
        notUntoggledTrackComponents = new ArrayList<>();
        notUntoggledTrackComponentsPreRecording = new ArrayList<>();
        this.mainActivity = mainActivity;



    }

    private Map<Integer, Integer> mapButtonNumberToR_ID() {
        Map<Integer, Integer> desiredMap = new HashMap<>();

        desiredMap.put(0, R.id.button1);
        desiredMap.put(1, R.id.button2);
        desiredMap.put(2, R.id.button3);
        desiredMap.put(3, R.id.button4);
        desiredMap.put(4, R.id.button5);
        desiredMap.put(5, R.id.button6);
        desiredMap.put(6, R.id.button7);
        desiredMap.put(7, R.id.button8);
        desiredMap.put(8, R.id.button9);
        desiredMap.put(9, R.id.button10);

        return desiredMap;
    }

    public File renderTrack(File track) {
        List<TrackComponent> trackComponents = importTrackComponents(track);
        TrackRenderer trackRenderer = new TrackRenderer(context, trackComponents);

        return trackRenderer.renderTrack();
    }


    public static Recorder getInstance() {
        if (instance == null)
            System.err.println("Instance is null!!!");
        return instance;
    }

    public static Recorder createInstance(Context context, SynthService synthService, MainActivity mainActivity) {
        if (instance == null)
            instance = new Recorder(context, synthService, mainActivity);
        return instance;
    }

    public void startRecording() {
        System.out.println("Start Recording");
        isRecording = true;
        untoggleToggledTrackComponentsPreRecording();
        currentTrackFile = createTrackFile();
        startOfRecording = System.currentTimeMillis();
    }

    public void stopRecording() {
        System.out.println("Stop Recording");
        untoggleToggledTrackComponents();
        exportTrackComponents(this.currentTrackFile);
        trackComponents = new ArrayList<>();
        notUntoggledTrackComponents = new ArrayList<>();
        isRecording = false;
    }

    private void doEffect(Integer R_ID) {
    }

    public void playTrack(File track) {
        if(track.length() == 0)
            return;

        executor.execute(() -> {
            List<TrackComponent> trackComponents = importTrackComponents(track);
            long startTime = System.currentTimeMillis();

            Map<Integer, Integer> buttonNumberToR_ID = mapButtonNumberToR_ID();

            while (!trackComponents.isEmpty()) {
                if (System.currentTimeMillis() - startTime >= trackComponents.get(0).momentPlayed) {

                    Integer R_ID = buttonNumberToR_ID.get(trackComponents.get(0).buttonNumber);

                    doEffect(R_ID);

                    synthService.play(
                            trackComponents.get(0).midiPath,
                            trackComponents.get(0).soundfontPath,
                            trackComponents.get(0).buttonNumber,
                            trackComponents.get(0).key,
                            trackComponents.get(0).velocity,
                            trackComponents.get(0).preset,
                            trackComponents.get(0).isLoop);
                    trackComponents.remove(0);
                }
            }
        });
    }

    private void untoggleToggledTrackComponents() {
        for (TrackComponent trackComponent : notUntoggledTrackComponents) {
            long momentPlayed = System.currentTimeMillis() - this.startOfRecording;
            trackComponents.add(new TrackComponent(momentPlayed, trackComponent.midiPath, trackComponent.soundfontPath, trackComponent.buttonNumber, trackComponent.key, trackComponent.velocity, trackComponent.preset, trackComponent.isLoop));


            trackComponent.midiPath = trackComponent.midiPath == null ? "null" : trackComponent.midiPath;
            trackComponent.soundfontPath = trackComponent.soundfontPath == null ? "null" : trackComponent.soundfontPath;

            synthService.play(
                    trackComponent.midiPath,
                    trackComponent.soundfontPath,
                    trackComponent.buttonNumber,
                    trackComponent.key,
                    trackComponent.velocity,
                    trackComponent.preset,
                    trackComponent.isLoop);
        }
    }

    private void untoggleToggledTrackComponentsPreRecording() {
        for (TrackComponent trackComponent : notUntoggledTrackComponentsPreRecording) {
            synthService.play(
                    trackComponent.midiPath,
                    trackComponent.soundfontPath,
                    trackComponent.buttonNumber,
                    trackComponent.key,
                    trackComponent.velocity,
                    trackComponent.preset,
                    trackComponent.isLoop);
        }
        notUntoggledTrackComponentsPreRecording = new ArrayList<>();
    }


    /**
     * Needs to be called in the Button Listeners for it to log when a button is pressed
     */
    public void addTrackComponent(String midiPath, String soundfontPath, int buttonNumber, int key, int velocity, int preset, boolean isLoop) {
        long momentPlayed = System.currentTimeMillis() - this.startOfRecording;
        TrackComponent newTrackComponent = new TrackComponent(momentPlayed, midiPath, soundfontPath, buttonNumber, key, velocity, preset, isLoop);

        if (isRecording) {
            this.trackComponents.add(newTrackComponent);
            // Keeps track of loop buttons that where activated, but not deactivated.
            if (isLoop) {
                if (notUntoggledTrackComponents.contains(newTrackComponent)) {
                    notUntoggledTrackComponents.remove(newTrackComponent);
                } else {
                    notUntoggledTrackComponents.add(newTrackComponent);
                }
            }
        } else {
            if (isLoop) {
                newTrackComponent.momentPlayed = 0;
                newTrackComponent.midiPath = newTrackComponent.midiPath == null ? "null" : newTrackComponent.midiPath;
                newTrackComponent.soundfontPath = newTrackComponent.soundfontPath == null ? "null" : newTrackComponent.soundfontPath;
                if (notUntoggledTrackComponentsPreRecording.contains(newTrackComponent)) {
                    notUntoggledTrackComponentsPreRecording.remove(newTrackComponent);
                } else {
                    notUntoggledTrackComponentsPreRecording.add(newTrackComponent);
                }
            }
        }
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    private List<TrackComponent> importTrackComponents(File file) {
        if (file.length() == 0)
            return new ArrayList<>();

        List<TrackComponent> trackComponents = new ArrayList<>();
        String track = readFromFile(file);
        String[] trackComponentStrings = track.split("\n");

        for (String trackComponentString : trackComponentStrings) {
            String[] values = trackComponentString.split(",");
            trackComponents.add(new TrackComponent(
                    Long.parseLong(values[0]),
                    values[1],
                    values[2],
                    Integer.parseInt(values[3]),
                    Integer.parseInt(values[4]),
                    Integer.parseInt(values[5]),
                    Integer.parseInt(values[6]),
                    Boolean.parseBoolean(values[7])
            ));
        }
        return trackComponents;
    }

    private void exportTrackComponents(File file) {
        for (TrackComponent trackComponent : this.trackComponents) {
            writeToFile(file, String.format(
                    "%s,%s,%s,%s,%s,%s,%s,%s\n",
                    trackComponent.momentPlayed,
                    trackComponent.midiPath,
                    trackComponent.soundfontPath,
                    trackComponent.buttonNumber,
                    trackComponent.key,
                    trackComponent.velocity,
                    trackComponent.preset,
                    trackComponent.isLoop
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
        File fi = new File(context.getFilesDir() + "/tracks");

        if (!fi.exists())
            fi.mkdirs();

        File[] files = context.getFilesDir().listFiles();
        // files = fi.listFiles();
        List<File> tracks = new ArrayList<>();

        assert files != null;
        for (File f : files) {
            if(f.getName().contains(".kk"))
                tracks.add(f);
        }
        File[] t = new File[tracks.size()];
        for (int j = 0, i = tracks.size() - 1; i >= 0; i--, j++) {
            t[j] = tracks.get(i);
        }
        return t;
    }

    public String getTrackLength(File track) {
        if (track.length() == 0)
            return "00:00";
        List<TrackComponent> trackComponents = importTrackComponents(track);
        long length = trackComponents.get(trackComponents.size() - 1).momentPlayed;
        length /= 1000;
        return String.format("%02d:%02d", length / 60, length % 60);
    }

    public long getTrackLengthLong(File track) {
        List<TrackComponent> trackComponents = importTrackComponents(track);
        long length = trackComponents.get(trackComponents.size() - 1).momentPlayed;
        length /= 1000;
        return length;
    }

    public void deleteAllTracks() {
        File[] tracks = getTracks();
        for (File track : tracks) {
            deleteFile(track);
        }
    }

    public void deleteTrack(File file) {
        deleteFile(file);
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
        String date = ZonedDateTime.now(ZoneId.of("Europe/Paris")).toString();
        date = date.replace("T", "_");
        date = date.substring(0, date.indexOf("."));
        date = date.replaceAll(":", "-");
        return date;
    }

}
