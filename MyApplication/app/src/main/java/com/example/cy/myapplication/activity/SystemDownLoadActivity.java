package com.example.cy.myapplication.activity;

import android.app.DownloadManager;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.cy.myapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 使用系统下载器下载
 */
public class SystemDownLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_down_load);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.bt1)
    public void onClick() {
        int state = getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {//不可用
            Toast.makeText(this, "系统下载器不可用", Toast.LENGTH_LONG).show();
        } else {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1,null,this);


            //直接用浏览器下载
            //Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://down.pre.im/0c/c3/0cc395374fac90f206f820620dc13f1b.apk?OSSAccessKeyId=QoA0RoJkVznFZAxs&Expires=1490954337&Signature=Cf0%2BKCzNvbMuYbIPPbim5AEXqGk%3D"));
            //startActivity(i);
        }

    }
}
