package de.gruppe.e.klingklang.model;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.ReturnCode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.media.MediaMetadataRetriever;


public class TrackRenderer {

    Context context;
    List<TrackComponent> trackComponents;
    List<File> toDelte;

    public TrackRenderer(Context context, List<TrackComponent> trackComponents) {
        this.trackComponents = trackComponents;
        this.context = context;
        this.toDelte = new ArrayList<>();
    }

    public File renderTrack() {

        if(trackComponents.size() == 0)
            return generateSilence(100);


        List<File> trackComponentFiles = new ArrayList<>();

        trackComponentFiles.add(generateSilence(trackComponents.get(0).momentPlayed));
        trackComponentFiles.add(getFileFromTrackComponent(trackComponents.get(0)));

        for (int i = 1; i < trackComponents.size(); i++) {
            if (getTimeDiff(trackComponents.get(i - 1), trackComponents.get(i)) > 0)
                trackComponentFiles.add(generateSilence(getTimeDiff(trackComponents.get(i - 1), trackComponents.get(i))));
            trackComponentFiles.add(getFileFromTrackComponent(trackComponents.get(i)));
        }

        File finalFile = joinTrackComponentFiles(trackComponentFiles);

        trackComponentFiles.forEach(File::delete);

        return finalFile;
    }

    private double getTimeDiff(TrackComponent t1, TrackComponent t2) {
        File f1 = getFileFromTrackComponent(t1);

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        // Set the data source to the MP3 file
        mmr.setDataSource(f1.getPath());

        int duration = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

        long diff = t2.momentPlayed + duration - t1.momentPlayed;

        if (diff <= 0)
            return 0;

        return diff;
    }

    private File getFileFromTrackComponent(TrackComponent trackComponent) {
        String name = trackComponent.buttonNumber + ".mp3";

        assert name != null;
        File file = new File(context.getFilesDir(), name);

        if (file.exists())
            return file;

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(name);
            FileOutputStream outputStream = new FileOutputStream(file);

            // Copy the contents of the input stream to the output stream
            // 30 Megabyte
            byte[] buffer = new byte[(int) 3E+7];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private File joinTrackComponentFiles(List<File> trackComponentFiles) {
        String finalName = getFileName("final.mp3");
        StringBuilder toConcat = new StringBuilder("concat:" + trackComponentFiles.get(0).getPath());

        for (int i = 1; i < trackComponentFiles.size(); i++) {
            toConcat.append("|").append(trackComponentFiles.get(i).getPath());
        }

        String exec = "-i \"" + toConcat + "\" -c copy " + finalName;

        System.out.println("Exec: " + exec);
        ffmpegExecute(exec);

        return new File(finalName);
    }

    private File generateSilence(double milliseconds) {
        double seconds = milliseconds / 1000.0;
        String fileName = getFileName("silence_" + seconds + ".mp3");

        boolean success = ffmpegExecute("-f lavfi -i anullsrc=r=44100:cl=stereo -t " + seconds + " -q:a 9 -acodec libmp3lame " + fileName);

        if (!success)
            return generateSilence(10);

        return new File(fileName);
    }


    private String getFileName(String name) {
        return String.format("%s/%s_%s", context.getFilesDir().getPath(), getDate(), name);
    }

    private boolean ffmpegExecute(String command) {
        FFmpegSession session = FFmpegKit.execute(command);

        if (ReturnCode.isSuccess(session.getReturnCode())) {

            System.out.println("FFMPEG: SUCCESS");

            return true;

        } else if (ReturnCode.isCancel(session.getReturnCode())) {

            System.out.println("FFMPEG: CANCEL");

        } else {
            System.out.println("FFMPEG: FAILURE");
            Log.d(TAG, String.format("Command failed with state %s and rc %s.%s", session.getState(), session.getReturnCode(), session.getFailStackTrace()));

        }
        return false;
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

    private String getDate() {
        String date = ZonedDateTime.now(ZoneId.of("Europe/Paris")).toString();
        date = date.replace("T", "_");
        date = date.substring(0, date.indexOf("+"));
        date = date.replaceAll(":", "-");
        return date;
    }

}
