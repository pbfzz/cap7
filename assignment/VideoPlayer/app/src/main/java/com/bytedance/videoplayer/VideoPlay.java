package com.bytedance.videoplayer;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;


public class VideoPlay extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    private VideoView videoView;
    private SeekBar seekBar;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        setTitle("VideoPlayer");

        buttonPause = findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.bytedance));

        mediaController=new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        mediaController.show();

    }

    private boolean portrait;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        portrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT;
        tryFullScreen(!portrait);
    }

    private void tryFullScreen(boolean fullScreen) {
        if (VideoPlay.this instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) VideoPlay.this).getSupportActionBar();
            if (supportActionBar != null) {
                if (fullScreen) {
                    supportActionBar.hide();
                    buttonPause.setVisibility(View.GONE);
                    buttonPlay.setVisibility(View.GONE);
                } else {
                    supportActionBar.show();
                    buttonPause.setVisibility(View.VISIBLE);
                    buttonPlay.setVisibility(View.VISIBLE);
                }
            }
        }
        setFullScreen(fullScreen);
    }


    private void setFullScreen(boolean fullScreen) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        if (fullScreen) {
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}