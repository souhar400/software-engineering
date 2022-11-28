package de.gruppe.e.klingklang.model;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class Recorder {
    Context context;
    private File currentFile;

    public Recorder(Context context) {
        this.context = context;
    }



    public void startRecording() {

    }

    public void stopRecording() {

    }

    /**
     * Needs to be called in the Button Listeners for it to log when a button is pressed
     */
    public void buttonPressed() {

    }

    public void getTracks() {

    }

    public void playTrack() {

    }

    public void createTrack() {
        String file = "Recording_" + getDate();
        String ret;


        writeToFile("MoinMeista", file);
        ret = readFromFile(file);
        System.out.println("erfolg" + ret);
        getTracks();

    }

    private File[] getFilesInContext() {
        File path = context.getFilesDir();
        File[] files = path.listFiles();
        System.out.println("Erfolg2"+ files.length);
        for (File f : files) {
            System.out.println(f.getName());
        }
        return files;
    }

    private void writeToFile(String data, String fileName) {
        File file = new File(context.getFilesDir(), fileName + ".kk");

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
