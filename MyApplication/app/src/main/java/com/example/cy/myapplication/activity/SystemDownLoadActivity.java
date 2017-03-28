package com.example.cy.myapplication.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.cy.myapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 使用系统下载器下载
 */
public class SystemDownLoadActivity extends AppCompatActivity {
    DownLoadReceiver downLoadReceiver = new DownLoadReceiver();

    DownloadManager downloadManager = null;

    long requestId = 0;//下载任务Id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_down_load);
        ButterKnife.inject(this);
        registerReceiver(downLoadReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @OnClick(R.id.bt1)
    public void onClick() {
        int state = getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {//不可用
            Toast.makeText(this, "系统下载器不可用", Toast.LENGTH_LONG).show();
        } else {
            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            Uri uri = Uri.parse("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
            DownloadManager.Request request = new DownloadManager.Request(uri);
            requestId = downloadManager.enqueue(request);
            Log.e("请求id",requestId+"");
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downLoadReceiver);
    }

    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        //install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

    class DownLoadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                Toast.makeText(context, "下载完成", Toast.LENGTH_LONG).show();
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                Log.e("请求完成id",id+"");
                if (id == requestId && id != 0){
                    Uri uri = downloadManager.getUriForDownloadedFile(requestId);
                    startInstall(context,uri);
                }
            }
        }
    }
}
