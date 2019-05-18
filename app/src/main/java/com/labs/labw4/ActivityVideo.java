package com.labs.labw4;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class ActivityVideo extends AppCompatActivity {

    VideoView videoPlayer;
    Uri videoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoUri = Uri.parse(getIntent().getStringExtra("linkVideo"));
        videoPlayer = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        videoPlayer.setVideoURI(videoUri);
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);
    }
}
