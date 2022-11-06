package de.gruppe.e.klingklang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

    public class RecordButton extends RelativeLayout {

        private MainActivity mainActivity;
        private ImageView mImgPlay;
        private ImageView msoundStart;
        private ImageView mSoundStop;
        private ImageView imageView;
        private int mLeftButtonX = 0;
        private int mRightButtonX = 0;


        private TextView mTvRecordTime;
        private long mStartTime;
        private String mAudioFile = null;
        private boolean mIsCancel = false;


        private ObtainDecibelThread mThread;

        public RecordButton(Context context) {
            super(context);
            init();
        }

        public RecordButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public RecordButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {

            mImgPlay.setImageResource(R.drawable.img);
            mImgPlay.setImageResource(R.drawable.img_1);

            mTvRecordTime = (TextView) findViewById(androidx.core.R.id.time);
            initPlayButtonEvent();
        }

        private void initPlayButtonEvent() {
            msoundStart.setOnClickListener(v -> playRecord());

            mSoundStop.setOnClickListener(v -> stopRecording());

        }
        private void scaleView(View view, float scaleXY) {
            if (android.os.Build.VERSION.SDK_INT > 10) {
                view.setScaleX(scaleXY);
                view.setScaleY(scaleXY);
            }
        }
        private void changeRecordStatus(String recordStatus) {
            switch (recordStatus) {
                case "start":
                    initRecorder();
                    msoundStart.setImageResource(R.drawable.img_1);
                    break;
                case "stop":
                    mSoundStop.setImageResource(R.drawable.img);
                    break;
            }
        }

        private void initRecorder() {
            mStartTime = System.currentTimeMillis();
            startRecording();
        }
        private void startRecording() {
            mainActivity.playSound(mAudioFile, 5);
            //TODO  audio.recordAudio()....
            mThread = new ObtainDecibelThread();
            mThread.start();

        }
        private void stopRecording() {
            if (mThread != null) {
                mThread.exit();
                mThread = null;
            }
            //  if (TODO audio != null ) {
            // TODO audio.stopRecord();}
        }
        public void playRecord() {
            // TODO   audioToPlay.startPlay();
        }
        public void startPlay() {
            // TODO audioToPlay.startPlay();
        }
        private static class ObtainDecibelThread extends Thread {
            private volatile boolean running = true;

            public void exit() {
                running = false;
            }

            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }


        }
    }


