package com.example.cy.myapplication.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cy.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GetApkTestActivity extends AppCompatActivity {

    @Bind(R.id.lv)
    ListView lv;
    BaseAdapter adapter;

    List<PackageInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_apk_test);
        ButterKnife.bind(this);


        getData();


        initView();


    }

    private void getData() {
        //获取安装了的应用信息
        list.addAll(getPackageManager().getInstalledPackages(0));
    }

    private void initView() {
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
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
                PackageInfo packageInfo = list.get(position);
                ApplicationInfo applicationInfo = null;
                try {
                    applicationInfo = getPackageManager().getApplicationInfo(packageInfo.packageName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


                View view = LayoutInflater.from(GetApkTestActivity.this).inflate(R.layout.item_app_info, null);
                TextView tv = (TextView) view.findViewById(R.id.tv_item);
                tv.setText(
                        "应用名：" + getPackageManager().getApplicationLabel(applicationInfo)
                        + "\n\n包名：\n" + packageInfo.packageName
                        + "\n\n版本号：\n" + packageInfo.versionCode
                        + "\n\n版本名：\n" + packageInfo.versionName
                        + "\n\n提取APK路径：\n" + applicationInfo.dataDir
                        + "\n\n提取APK大小：\n" + new File(applicationInfo.sourceDir).length()/1020+"kB"
                );

                ImageView iv = (ImageView) view.findViewById(R.id.iv_item);
                iv.setImageDrawable(getPackageManager().getApplicationIcon(applicationInfo));


                return view;
            }
        };
        lv.setAdapter(adapter);
    }
}
