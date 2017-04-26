package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cy.myapplication.R;

/**
 * Created by cy on 2017/4/23.
 */

public class CustomView extends ViewGroup {
    Context context;

    public CustomView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //LayoutInflater.from(context).inflate(R.layout.activity_gif,this);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Toast.makeText(context,"自定义组件尺寸："+ changed + "***" + l + "***" + t + "***" + r + "***" + b, Toast.LENGTH_LONG).show();
        Log.e("xxxxxxxxxxxxxxxxxxxx",changed + "***" + l + "***" + t + "***" + r + "***" + b);
    }


}
