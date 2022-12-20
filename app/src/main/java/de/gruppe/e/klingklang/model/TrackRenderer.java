package de.gruppe.e.klingklang.model;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.ReturnCode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TrackRenderer {

    List<TrackComponent> trackComponents;
    Map<Integer, String> buttonNumberToFile;

    public TrackRenderer(List<TrackComponent> trackComponents) {
        this.trackComponents = trackComponents;
        this.buttonNumberToFile = mapButtonNumberToFile();
    }

    private Map<Integer, String> mapButtonNumberToFile() {
        Map<Integer, String> buttonNumberToFile = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            buttonNumberToFile.put(i, String.format("%d.wav", i));
        }
        return buttonNumberToFile;
    }


    public File renderTrack(File track) {
        if(track.length() == 0)
            return generateSilence(1);



        return new File("");
    }

    private File generateSilence(int seconds) {

        String fileName = String.format("%s_silence_%d.wav", getDate(), seconds);
        FFmpegSession session = FFmpegKit.execute(String.format("ffmpeg -f lavfi -i \"aevalsrc=0:d=%d\" -c:a pcm_s16le %s", seconds, fileName));

        ffmpegRet(session);

        return new File(fileName);
    }

    private void ffmpegRet(FFmpegSession session) {
        if (ReturnCode.isSuccess(session.getReturnCode())) {

            System.out.println("FFMPEG: SUCCESS");

        } else if (ReturnCode.isCancel(session.getReturnCode())) {

            System.out.println("FFMPGE CANCEL");

        } else {
            System.out.println("FFMPGE FAILURE");
            Log.d(TAG, String.format("Command failed with state %s and rc %s.%s", session.getState(), session.getReturnCode(), session.getFailStackTrace()));

        }
    }

    private String getDate() {
        String date = ZonedDateTime.now(ZoneId.of("Europe/Paris")).toString();
        date = date.replace("T", "_");
        date = date.substring(0, date.indexOf("."));
        date = date.replaceAll(":", "-");
        return date;
    }

}
