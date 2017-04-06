package com.example.cy.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.cy.myapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //自定义TabLayout
                startActivity(new Intent(this, TabLayoutTestActivity.class));
                break;
            case R.id.button1:
                //协调布局的简单使用
                startActivity(new Intent(this, CoordinatorLayoutTestActivity.class));
                break;
            case R.id.button2:
                //用Fresco和Glide播放Gif图
                startActivity(new Intent(this, GifActivity.class));
                break;
            case R.id.button3:
                //Fragment转场动画
                startActivity(new Intent(this, FragmentAnimActivity.class));
                break;
            case R.id.button4:
                //AES加密解密
                startActivity(new Intent(this, AesActivity.class));
                break;
            case R.id.button5:
                //设置电源管理，使后台线程不自动休眠
                startActivity(new Intent(this, ThreadActivity.class));
                break;
            case R.id.button6:
                //展示系统相册所有图片
                startActivity(new Intent(this, ImagesActivity.class));
                break;
            case R.id.button7:
                //系统下载器下载安装apk
                startActivity(new Intent(this, SystemDownLoadActivity.class));
                break;
            case R.id.button8:
                //手机旋转监听
                startActivity(new Intent(this, SensorActivity.class));
                break;
        }
    }

}
