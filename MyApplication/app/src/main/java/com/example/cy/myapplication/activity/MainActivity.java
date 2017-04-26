package com.example.cy.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.cy.myapplication.R;
import com.leon.vuforia.ImageTargets;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11,
            R.id.button12
    })
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
            case R.id.button9:
                //日历
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.button10:
                //ViewPager显示一组网络图片
                ArrayList<String> urls = new ArrayList();
                urls.add("http://image.tianjimedia.com/uploadImages/2014/287/00/WU13N728772L.jpg");
                urls.add("http://bcs.91.com/rbpiczy/Wallpaper/2014/11/12/043999654e824d859aa7ce3e6dee0eed-1.jpg");
                urls.add("http://img5.duitang.com/uploads/item/201503/07/20150307192348_uCUNB.jpeg");
                urls.add("http://img5.duitang.com/uploads/item/201508/21/20150821110613_xsSM5.jpeg");
                urls.add("http://imgstore.cdn.sogou.com/app/a/100540002/822878.jpg");
                urls.add("http://bizhi.zhuoku.com/2013/04/14/zhuoku/zhuoku109.jpg");
                startActivity(
                        new Intent(this, NetImagesActivity.class)
                        .putExtra("urls",urls)
                );
                break;
            case R.id.button11:
                //AR
                startActivity(new Intent().setClassName(this.getPackageName(),"com.leon.vuforia.ImageTargets"));
                 break;
            case R.id.button12://GreenDao数据库操作
                startActivity(new Intent(MainActivity.this,GreenDaoActivity.class));
                break;
        }
    }

}
