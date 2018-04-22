package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.cameraview.CameraView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraViewTestActivity extends AppCompatActivity {

    @Bind(R.id.cv)
    CameraView cv;
    @Bind(R.id.bt_takePicture)
    Button btTakePicture;
    @Bind(R.id.bt_start)
    Button btStart;
    @Bind(R.id.bt_stop)
    Button btStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view_test);
        ButterKnife.bind(this);

        //cv.startPreview();
    }


    @OnClick({R.id.bt_takePicture, R.id.bt_start, R.id.bt_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_takePicture:
                cv.takePicture(Environment.getExternalStorageDirectory() + "/ppa" + System.currentTimeMillis() + ".jpg",true);
                break;
            case R.id.bt_start:
                cv.startRecorderMp4(Environment.getExternalStorageDirectory() + "/ppa" + System.currentTimeMillis() + ".MP4");
                break;
            case R.id.bt_stop:
                cv.stopRecorderMp4();
                break;
        }
    }
}
