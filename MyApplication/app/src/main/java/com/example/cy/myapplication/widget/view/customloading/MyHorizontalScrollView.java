package com.example.cy.myapplication.widget.view.customloading;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by 蔡勇 on 2017-9-4.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {

    OnScrollListener onScrollListener;

    public interface OnScrollListener{
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }


    public void setOnScrollListener(OnScrollListener l) {
        this.onScrollListener = l;
    }

    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener!=null){
            onScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }
}
