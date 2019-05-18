package com.labs.labw4;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityAudio extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mediaPlayer;
    private Uri audioUri;
    private Timer seekBarProgressUpdateTimer;
    private SeekBarProgressUpdateTimerTask seekBarProgressUpdateTimerTask;
    private SeekBar seekBar;
    private boolean isWasStoppedBySeekBar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        audioUri = Uri.parse(getIntent().getStringExtra("linkAudio"));
        mediaPlayer = MediaPlayer.create(this, audioUri);
        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartPlay();
            }
        });
        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PausePlay();
            }
        });
        findViewById(R.id.buttonStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopPlay();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                StopPlay();
            }
        });
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBarProgressUpdateTimer = new Timer();
        seekBarProgressUpdateTimerTask = new SeekBarProgressUpdateTimerTask();
        seekBarProgressUpdateTimer.schedule(seekBarProgressUpdateTimerTask, 200, 200);
        StartPlay();

    }

    private void StartPlay(){
        mediaPlayer.start();
    }

    private void PausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    private void StopPlay(){
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
        }
        catch (Exception exception){}
        seekBar.setProgress(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StopPlay();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
/*        if(b) {
            StartPlay();
            mediaPlayer.seekTo(seekBar.getProgress() * mediaPlayer.getDuration() / 100);
        }*/
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(mediaPlayer.isPlaying()) {
            PausePlay();
            isWasStoppedBySeekBar = true;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(isWasStoppedBySeekBar)
            mediaPlayer.start();
        mediaPlayer.seekTo(seekBar.getProgress()*mediaPlayer.getDuration()/100);
        isWasStoppedBySeekBar = true;
    }

    class SeekBarProgressUpdateTimerTask extends TimerTask{

        @Override
        public void run() {
            if(mediaPlayer.isPlaying())
                seekBar.setProgress(mediaPlayer.getCurrentPosition()*100/mediaPlayer.getDuration());
        }
    }
}
