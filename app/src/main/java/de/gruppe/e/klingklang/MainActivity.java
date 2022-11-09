package de.gruppe.e.klingklang;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private VolumeData volumeData = new VolumeData();
    private SoundMenu smenu = new SoundMenu(volumeData);
    private SoundMenu smenu = new SoundMenu(volumeData);
    private MainMenu mainMenu = new MainMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationAndSwipeUpBar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        playSound("sndfnt.sf2", 2);

        Log.d(LOG_TAG, "App successfully created!");
    }
    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationAndSwipeUpBar();
    }

    public void openMenu(View view) {
        smenu.show(getSupportFragmentManager(), "");
    }

    public void playSound(String fileName, int soundLength) {
        try {
            String tempSoundfontPath = copyAssetToTmpFile(fileName);
            playFluidSynthSound(tempSoundfontPath, soundLength);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to play synthesizer sound");
            throw new RuntimeException(e);
        }
    }

    private void hideNavigationAndSwipeUpBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * Turns a assets file that is a series of bytes in the compressed APK into a playable temporary
     * file.
     *
     * @param fileName Name of the .sf2 file in /assets
     * @return Path of the temporary file
     * @throws IOException IOException when file does not exist or is not openable
     */
    private String copyAssetToTmpFile(String fileName) throws IOException {
        try (InputStream is = getAssets().open(fileName)) {
            String tempFileName = "tmp_" + fileName;
            try (FileOutputStream fos = openFileOutput(tempFileName, Context.MODE_PRIVATE)) {
                int bytes_read;
                byte[] buffer = new byte[4096];
                while ((bytes_read = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytes_read);
                }
            }
            return getFilesDir() + "/" + tempFileName;
        }
    }

    /**
     * Native method that calls methods from the FluidSynth library.
     *
     * @param soundfontPath Path of the .sf2 soundfont file to be played (in /assets folder)
     * @param soundLength   Length of the .sf2 file in seconds
     */
    private native void playFluidSynthSound(String soundfontPath, int soundLength);

    private native void playFluidSynthSoundVolume(String soundfontPath, int soundLength, float volume);

    public void playVolumeSound(View view) {
        try {
            String tempSoundfontPath = copyAssetToTmpFile("sndfnt.sf2");
            playFluidSynthSoundVolume(tempSoundfontPath, 2, volumeData.getVolume());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to play synthesizer sound");
            throw new RuntimeException(e);
        }
    }
}
