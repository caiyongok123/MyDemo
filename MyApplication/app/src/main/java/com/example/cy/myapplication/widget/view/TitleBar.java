package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cy.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 标题栏
 * 使用id ： titleBar
 */
public class TitleBar extends LinearLayout {

    /**
     * 当按钮被点击
     */
    public interface OnTitleBarClickListener{
        void onBackClick();
        void onRightClick();
    }


    @Bind(R.id.ivBack)
    public ImageView ivBack;
    @Bind(R.id.tvTitle)
    public TextView tvTitle;
    @Bind(R.id.ivRight)
    public ImageView ivRight;
    @Bind(R.id.tvRight)
    public TextView tvRight;

    public String titleText;
    public String rightText;
    public Drawable rightDrawable;

    private OnTitleBarClickListener onTitleBarClickListener;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_title_bar, this);
        ButterKnife.bind(view);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
        titleText = a.getString(R.styleable.TitleBar_titleText);
        rightText = a.getString(R.styleable.TitleBar_rightText);
        rightDrawable = a.getDrawable(R.styleable.TitleBar_rightDrawable);

        a.recycle();
        initView();

    }


    private void initView() {
        tvTitle.setText(TextUtils.isEmpty(titleText) ? "" : titleText);

        if (rightDrawable != null) {
            ivRight.setImageDrawable(rightDrawable);
        } else if (!TextUtils.isEmpty(rightText)) {
            tvRight.setText(rightText);
        }
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    @OnClick({R.id.ivBack, R.id.ivRight, R.id.tvRight})
    public void onViewClicked(View view) {
        if (onTitleBarClickListener==null){
            return;
        }
        switch (view.getId()) {
            case R.id.ivBack:
                onTitleBarClickListener.onBackClick();
                break;
            case R.id.ivRight:
                onTitleBarClickListener.onRightClick();
                break;
            case R.id.tvRight:
                onTitleBarClickListener.onRightClick();
                break;
        }
    }
}
