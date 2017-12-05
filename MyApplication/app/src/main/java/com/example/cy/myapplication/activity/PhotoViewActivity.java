package com.example.cy.myapplication.activity;

import android.os.Bundle;

import com.example.cy.common.base.BaseActivity;
import com.example.cy.myapplication.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoViewActivity extends BaseActivity {

    @Bind(R.id.pv)
    PhotoView pv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);

        pv.setImageResource(R.drawable.bg);
    }
}
