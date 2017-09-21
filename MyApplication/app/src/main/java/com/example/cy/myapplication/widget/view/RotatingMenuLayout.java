package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.cy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蔡勇 on 2017-9-20.
 * 旋转菜单布局
 */

public class RotatingMenuLayout extends ViewGroup{

    Context context;

    int itemLayoutId;
    List<Object> itemList;
    List<View> itemViewList;

    int resWidth = 0;
    int resHeight = 0;

    int itemSize;

    int angle = 0; //偏移角度

    public RotatingMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setPadding(0,0,0,0);

    }

    public void setItemList(List<Object> list){
        removeAllViews();
        itemList=list;
        itemViewList = new ArrayList<>();
        if (itemList!=null){
            for (int i = 0; i < itemList.size(); i++) {
                if (itemLayoutId == 0){
                    itemLayoutId = R.layout.item_rotating_menu;
                }
                itemViewList.add(LayoutInflater.from(context).inflate(itemLayoutId,null));
                addView(itemViewList.get(i));
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 对于长宽，如果xml中设置的是具体值，那就长宽统一为短的那个，如果不是具体值，就充满父布局（保持正方形）
         */


        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY
                || heightMode != MeasureSpec.EXACTLY) {
            // 主要设置为背景图的高度
            resWidth = getSuggestedMinimumWidth();
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

            resHeight = getSuggestedMinimumHeight();
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
        } else {
            // 如果都设置为精确值，则直接取小值；
            resWidth = resHeight = Math.min(width, height);
        }

        setMeasuredDimension(resWidth, resHeight);
        try {
            View view = getChildAt(0);
            itemSize = view.getMeasuredHeight();
        }catch (Exception e){}


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (itemList!=null && !itemList.isEmpty()){
            for (int i = 0; i < itemList.size(); i++) {
                View view = getChildAt(i);
                int[] xy = getXY(i);
                if (xy!=null){
                    view.layout(xy[0],xy[1],xy[0]+itemSize,xy[1]+itemSize);
                }

            }
        }
    }


    /**
     * 获得默认该layout的尺寸(父布局的长宽取其短)
     *
     * @return
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }


    int[] getXY(int index){
        if (itemList!=null && !itemList.isEmpty()){
            int x = resWidth/2;
            int y = resHeight/2;
            int radius = (int) Math.sqrt(x*x-(itemSize /2)*(itemSize /2))- itemSize /2;
            x = (int) Math.sin((360f/itemList.size()*index+angle)/180*Math.PI)-itemSize/2;
            y = (int) Math.cos((360f/itemList.size()*index+angle)/180*Math.PI)-itemSize/2;
            return new int[]{x,y};
        }else {
            return null;
        }
    }
}
