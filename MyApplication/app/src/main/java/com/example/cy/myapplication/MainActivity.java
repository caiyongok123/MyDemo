package com.example.cy.myapplication;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cy.common.MyApplication;
import com.example.cy.common.base.BaseActivity;
import com.example.cy.myapplication.activity.AesActivity;
import com.example.cy.myapplication.activity.AnimalUpAndDownActivity;
import com.example.cy.myapplication.activity.BarActivity;
import com.example.cy.myapplication.activity.BluetoothTestActivity;
import com.example.cy.myapplication.activity.CalendarActivity;
import com.example.cy.myapplication.activity.CameraPreviewActivity;
import com.example.cy.myapplication.activity.CameraViewTestActivity;
import com.example.cy.myapplication.activity.CoordinatorLayoutTestActivity;
import com.example.cy.myapplication.activity.CustomLoadingActivity;
import com.example.cy.myapplication.activity.DialogFragmentActivity;
import com.example.cy.myapplication.activity.DrawerLayoutActivity;
import com.example.cy.myapplication.activity.FragmentAnimActivity;
import com.example.cy.myapplication.activity.GetApkTestActivity;
import com.example.cy.myapplication.activity.GifActivity;
import com.example.cy.myapplication.activity.GoogleMapActivity;
import com.example.cy.myapplication.activity.GreenDaoActivity;
import com.example.cy.myapplication.activity.ImagesActivity;
import com.example.cy.myapplication.activity.ItemsViewGroupTestActivity;
import com.example.cy.myapplication.activity.JPushTestActivity;
import com.example.cy.myapplication.activity.JiecaoActivity;
import com.example.cy.myapplication.activity.PhotoViewActivity;
import com.example.cy.myapplication.activity.QrCreateActivity;
import com.example.cy.myapplication.activity.RotatingMenuActivity;
import com.example.cy.myapplication.activity.ScrollTogetherActivity;
import com.example.cy.myapplication.activity.SeekBarActivity;
import com.example.cy.myapplication.activity.StringsPopupWindowTestActivity;
import com.example.cy.myapplication.activity.XiaoshuoActivity;
import com.example.cy.myapplication.activity.WqgalleryTestActivity;
import com.example.cy.myapplication.activity.filepath.FilePathActivity;
import com.example.cy.myapplication.activity.sound.SoundCompoundActivity;
import com.example.cy.myapplication.activity.wallpapers.LiveWallpapersActivity;
import com.example.cy.myapplication.activity.RsaActivity;
import com.example.cy.myapplication.activity.mpandroidchart.MPAndroidChartActivity;
import com.example.cy.myapplication.activity.MvvmActivity;
import com.example.cy.myapplication.activity.NetImagesActivity;
import com.example.cy.myapplication.activity.SensorActivity;
import com.example.cy.myapplication.activity.SystemDownLoadActivity;
import com.example.cy.myapplication.activity.TabLayoutTestActivity;
import com.example.cy.myapplication.activity.ThreadActivity;
import com.example.cy.myapplication.activity.VitamioBundleActivity;
import com.example.cy.myapplication.activity.qrscan.QrScanActivity;
import com.example.cy.myapplication.databinding.ActivityMainBinding;
import com.example.cy.myapplication.databinding.ItemText50dpBinding;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends BaseActivity {

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public static class ViewMode {
        public MyAdapter adapter = new MyAdapter();

        @BindingAdapter("setAdapter")
        public static void setAdapter(ListView lv, ViewMode mode) {


            ArrayList<String> urls = new ArrayList();
            urls.addAll(Arrays.asList(MyApplication.myApplication.getResources().getStringArray(R.array.imgUrls)));

            Collections.addAll(mode.adapter.dataList,
                    new ItemMode("CameraView测试", CameraViewTestActivity.class),
                    new ItemMode("长文件txt小说的显示，文件编码格式的识别", XiaoshuoActivity.class),
                    new ItemMode("StringsPopupWindow测试", StringsPopupWindowTestActivity.class),
                    new ItemMode("自定义TabLayout", TabLayoutTestActivity.class),
                    new ItemMode("自适应Items布局测试", ItemsViewGroupTestActivity.class),
                    new ItemMode("协调布局的简单使用", CoordinatorLayoutTestActivity.class),
                    new ItemMode("用Fresco和Glide播放Gif图", GifActivity.class),
                    new ItemMode("Fragment转场动画", FragmentAnimActivity.class),
                    new ItemMode("AES加密解密", AesActivity.class),
                    new ItemMode("RSA加密解密", RsaActivity.class),
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
                    new ItemMode("像头预览，拍照发送图片到服务器进行识别", CameraPreviewActivity.class),
                    new ItemMode("自定义动画等待框", CustomLoadingActivity.class),
                    new ItemMode("二维码扫描", QrScanActivity.class),
                    new ItemMode("二维码生成", QrCreateActivity.class),
                    new ItemMode("动态壁纸", LiveWallpapersActivity.class),
                    new ItemMode("获取文件地址", FilePathActivity.class),
                    new ItemMode("官方侧滑v4.widget.DrawerLayout", DrawerLayoutActivity.class),
                    new ItemMode("录音功能测试", SoundCompoundActivity.class),
                    new ItemMode("谷歌地图测试", GoogleMapActivity.class),
                    new ItemMode("联合滑动测试", ScrollTogetherActivity.class),
                    new ItemMode("拖动进度条", SeekBarActivity.class),
                    new ItemMode("旋转菜单", RotatingMenuActivity.class),
                    new ItemMode("获取手机中已经安装的应用的apk安装包", GetApkTestActivity.class),
                    new ItemMode("github开源库PhotoView图片查看（继承自ImageView，支持缩放，以及各种手势的监听）", PhotoViewActivity.class),
                    new ItemMode("知乎框架  和  微信样式相册选择器（wqgallery开源库）", WqgalleryTestActivity.class),
                    new ItemMode("激光推送测试", JPushTestActivity.class),
                    new ItemMode("蓝牙测试", BluetoothTestActivity.class)
                    // TODO: 2017/7/27 1.qq侧滑
            );


            lv.setAdapter(mode.adapter);

            MobclickAgent.reportError(MyApplication.myApplication, "123123123123123123123123123123123123123");



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



        /*View view = LayoutInflater.from(MyApplication.myApplication).inflate(R.layout.xuanfu,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.myApplication,"点个妹子点",Toast.LENGTH_LONG).show();
            }
        });
        WindowManager windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams =new WindowManager.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        layoutParams.type =TYPE_PHONE;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width=200;
        layoutParams.height=200;
        layoutParams.gravity= Gravity.RIGHT | Gravity.TOP;
        windowManager.addView(view,layoutParams);*/
    }

}


