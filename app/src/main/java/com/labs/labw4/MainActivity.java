package com.labs.labw4;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String audioLink = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonVideo = findViewById(R.id.buttonVideo);
    }

    public void onClickButtonVideo(View view){
        Intent intent = new Intent(this, ActivityVideo.class);
        intent.putExtra("linkVideo", "http://www.youtubemaza.com/files/data/366/Tom%20And%20Jerry%20055%20Casanova%20Cat%20(1951).mp4");
        startActivity(intent);
    }

    public void onClickButtonAudio(View view){
        Intent audioPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        audioPickerIntent.setType("audio/*");
        startActivityForResult(audioPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            audioLink = data.getData().toString();
        }
        else{
            audioLink = null;
        }
        Intent intent = new Intent(this, ActivityAudio.class);
        if(audioLink == null)
            intent.putExtra("linkAudio", "android.resource://" + getPackageName() + "/" + R.raw.sound);
        else
            intent.putExtra("linkAudio", audioLink);
        startActivity(intent);
    }
}
