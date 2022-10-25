package com.teame.klingklang;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private DrawerLayout mDrawerLayout;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideNavigationAndSwipeUpBar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mediaPlayer = MediaPlayer.create(this, R.raw.bass);
    }

    public void openMenu(View view) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
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

    public void playButton(View view) {
        executorService.execute(() -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(0);

            } else {
                mediaPlayer.start();
            }
        });
    }

    /**
     * Hide navigation and swipe-up bar after closing and resuming.
     */
    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationAndSwipeUpBar();
    }
}