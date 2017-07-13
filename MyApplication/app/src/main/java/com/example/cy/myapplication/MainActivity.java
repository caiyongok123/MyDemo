package com.example.cy.myapplication;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cy.myapplication.activity.AesActivity;
import com.example.cy.myapplication.activity.AnimalUpAndDownActivity;
import com.example.cy.myapplication.activity.BarActivity;
import com.example.cy.myapplication.activity.CalendarActivity;
import com.example.cy.myapplication.activity.CameraPreviewActivity;
import com.example.cy.myapplication.activity.CoordinatorLayoutTestActivity;
import com.example.cy.myapplication.activity.DialogFragmentActivity;
import com.example.cy.myapplication.activity.FragmentAnimActivity;
import com.example.cy.myapplication.activity.GifActivity;
import com.example.cy.myapplication.activity.GreenDaoActivity;
import com.example.cy.myapplication.activity.ImagesActivity;
import com.example.cy.myapplication.activity.JiecaoActivity;
import com.example.cy.myapplication.activity.mpandroidchart.MPAndroidChartActivity;
import com.example.cy.myapplication.activity.MvvmActivity;
import com.example.cy.myapplication.activity.MyApplication;
import com.example.cy.myapplication.activity.NetImagesActivity;
import com.example.cy.myapplication.activity.SensorActivity;
import com.example.cy.myapplication.activity.SystemDownLoadActivity;
import com.example.cy.myapplication.activity.TabLayoutTestActivity;
import com.example.cy.myapplication.activity.ThreadActivity;
import com.example.cy.myapplication.activity.VitamioBundleActivity;
import com.example.cy.myapplication.databinding.ActivityMainBinding;
import com.example.cy.myapplication.databinding.ItemText50dpBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static class ViewMode {
        public MyAdapter adapter = new MyAdapter();

        @BindingAdapter("setAdapter")
        public static void setAdapter(ListView lv, ViewMode mode) {


            ArrayList<String> urls = new ArrayList();
            urls.addAll(Arrays.asList(MyApplication.myApplication.getResources().getStringArray(R.array.imgUrls)));

            Collections.addAll(mode.adapter.dataList,
                    new ItemMode("自定义TabLayout", TabLayoutTestActivity.class),
                    new ItemMode("协调布局的简单使用", CoordinatorLayoutTestActivity.class),
                    new ItemMode("用Fresco和Glide播放Gif图", GifActivity.class),
                    new ItemMode("Fragment转场动画", FragmentAnimActivity.class),
                    new ItemMode("AES加密解密", AesActivity.class),
                    new ItemMode("设置电源管理，使后台线程不自动休眠", ThreadActivity.class),
                    new ItemMode("展示系统相册所有图片", ImagesActivity.class),
                    new ItemMode("系统下载器下载安装apk", SystemDownLoadActivity.class),
                    new ItemMode("手机旋转监听", SensorActivity.class),
                    new ItemMode("日历", CalendarActivity.class),
                    new ItemMode("ViewPager显示一组网络图片", new Intent(MyApplication.myApplication, NetImagesActivity.class)
                            .putExtra("urls", urls)),
                    new ItemMode("AR", new Intent().setClassName(MyApplication.myApplication.getPackageName(), "com.leon.vuforia.ImageTargets")),
                    new ItemMode("GreenDao数据库操作", GreenDaoActivity.class),
                    new ItemMode("mvvm模式", MvvmActivity.class),
                    new ItemMode("VitamioBundle框架播放视频", VitamioBundleActivity.class),
                    new ItemMode("节操框架播放视频", JiecaoActivity.class),
                    new ItemMode("图片上下回弹跳的动画", AnimalUpAndDownActivity.class),
                    new ItemMode("MPAndroidChart图表", MPAndroidChartActivity.class),
                    new ItemMode("DialogFragment", DialogFragmentActivity.class),
                    new ItemMode("标题栏导航栏背景色设置", BarActivity.class),
                    new ItemMode("像头预览，拍照发送图片到服务器进行识别", CameraPreviewActivity.class)
            );



            lv.setAdapter(mode.adapter);
        }
    }

    public static class ItemMode extends BaseObservable {

        public String title;
        public Intent intent;


        @BindingAdapter("setOnClick")
        public static void setOnClick(final TextView tv, final ItemMode item) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv.getContext().startActivity(item.intent);
                }
            });
        }

        public ItemMode(String title, Intent intent) {
            this.title = title;
            this.intent = intent;
        }

        public ItemMode(String title, Class mClass) {
            this.title = title;
            this.intent = new Intent(MyApplication.myApplication, mClass);
        }
    }

    static class MyAdapter extends BaseAdapter {

        public ArrayList<ItemMode> dataList = new ArrayList<>();

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemText50dpBinding ib = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_50dp, parent, false);
            ib.setItemMode(dataList.get(position));
            return ib.getRoot();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding amb = DataBindingUtil.setContentView(this, R.layout.activity_main);
        amb.setViewMode(new ViewMode());


    }

}
