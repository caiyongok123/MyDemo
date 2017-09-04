package com.example.cy.myapplication.activity.sound;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.MyAudioRecorder;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 使用AudioRecord录音
 * 需要录音权限：android.permission.RECORD_AUDIO
 */
public class SoundCompoundActivity extends AppCompatActivity {


    MyAudioRecorder myAudioRecorder;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ppa111.wav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_compound);

        myAudioRecorder = new MyAudioRecorder(new MyAudioRecorder.SoundLevelListener() {
            @Override
            public void getSoundLevel(int l) {
                TextView tv = (TextView) findViewById(R.id.tv);
                tv.setText("音量等级:" +l);
            }
        });
    }

    public void start(View view) {
        myAudioRecorder.start(path);
    }

    public void stop(View view) {
        myAudioRecorder.stop();
    }

    public void play(View view) {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
