package com.example.cy.myapplication.activity.wallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.activity.filepath.FilePathActivity;
import com.example.cy.myapplication.util.WallpaperUtil;

public class LiveWallpapersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_wallpapers);


    }


    public void byMp4(View view){
        startActivityForResult(new Intent(this,FilePathActivity.class),1);

    }

    public void byCamera(View view){
        WallpaperUtil.setLiveWallpaper(this,this,CameraLiveWallpaperService.class,2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            String path = data.getStringExtra("path");
            if (path.endsWith(".mp4")){
                Mp4LiveWallpaperService.mp4Path = path;
                WallpaperUtil.setLiveWallpaper(this,this,Mp4LiveWallpaperService.class,3);
            }else {
                Toast.makeText(this,"请选择Mp4文件",Toast.LENGTH_LONG).show();
            }

        }
    }
}
