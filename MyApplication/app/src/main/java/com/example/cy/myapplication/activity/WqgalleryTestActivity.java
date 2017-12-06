package com.example.cy.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cy.common.base.BaseActivity;
import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.CommonUtil;
import com.google.gson.Gson;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WqgalleryTestActivity extends BaseActivity {

    int REQUEST_CODE_CHOOSE = 2;


    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.bt_choose)
    Button btChoose;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.bt_choose_zhihu)
    Button btChooseZhihu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wqgallery_test);
        ButterKnife.bind(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            //在data中返回 选择的图片列表
            ArrayList<String> paths = data.getStringArrayListExtra("data");
            tv.setText(new Gson().toJson(paths));
        }
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> list = Matisse.obtainResult(data);
            tv.setText(CommonUtil.getPathByUri(list.get(0))+" \n\n "+new File(CommonUtil.getPathByUri(list.get(0))).length());
            iv.setImageDrawable(new BitmapDrawable(CommonUtil.getPathByUri(list.get(0))));

        }
    }

    @OnClick({R.id.bt_choose_zhihu, R.id.bt_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_choose_zhihu:
                HashSet<MimeType> set = new HashSet();
                set.add(MimeType.GIF);
                Matisse.from(mActivity)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(1)
                        //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.bt_choose:
                //图片剪裁的一些设置
                UCrop.Options options = new UCrop.Options();
                //图片生成格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //图片压缩比
                options.setCompressionQuality(100);

                new  PickConfig.Builder(mActivity)
                        .maxPickSize(9)//最多选择几张
                        .isneedcamera(true)//是否需要第一项是相机
                        .spanCount(3)//一行显示几张照片
                        .actionBarcolor(Color.parseColor("#bbbbbb"))//设置toolbar的颜色
                        .statusBarcolor(Color.parseColor("#bbbbbb")) //设置状态栏的颜色(5.0以上)
                        .isneedcrop(true)//受否需要剪裁
                        .setUropOptions(options) //设置剪裁参数
                        .isSqureCrop(true) //是否是正方形格式剪裁
                        .pickMode(PickConfig.MODE_MULTIP_PICK)//单选还是多选
                        .build();
                break;
        }
    }
}
