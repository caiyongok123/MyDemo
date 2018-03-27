package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.example.cy.myapplication.R;

/**
 * Created by dell on 2018/3/27.
 */

public class NetImageView extends AppCompatImageView {

    Context context;
    String url;
    int placeholderResourceId;
    int errorResourceId;

    public NetImageView(Context context) {
        super(context,null);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NetImageView);
        url = a.getString(R.styleable.NetImageView_url);
        placeholderResourceId=a.getResourceId(R.styleable.NetImageView_placeholderResourceId,0);
        errorResourceId=a.getResourceId(R.styleable.NetImageView_errorResourceId,0);
        a.recycle();

        if (!TextUtils.isEmpty(url)){
            loadUrl(url);
        }
    }


    public NetImageView setPlaceholderResourceId(@DrawableRes int id){
        placeholderResourceId = id;
        return this;
    }

    public NetImageView setErrorResourceId(@DrawableRes int id){
        errorResourceId = id;
        return this;
    }





    public void loadUrl(String url){
        this.url = url;

        DrawableTypeRequest<String> dtr = Glide.with(context).load(url);
        if (placeholderResourceId>0){
            dtr.placeholder(placeholderResourceId);
        }
        if (errorResourceId>0){
            dtr.error(errorResourceId);
        }
        dtr.into(this);
    }


}
