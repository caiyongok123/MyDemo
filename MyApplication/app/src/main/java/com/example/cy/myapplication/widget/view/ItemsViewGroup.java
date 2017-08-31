package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 蔡勇 on 2017-8-30.
 */

public class ItemsViewGroup extends ViewGroup implements View.OnClickListener {

    public interface OnItemClickListener {
        void onItemClick(TextView view, int position);
    }

    Context context;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setMarginHDp(int marginHDp) {
        this.marginHDp = marginHDp;
        marginHPx = (int) (context.getResources().getDisplayMetrics().density * marginHDp + 0.5f);
    }

    public void setMarginWDp(int marginWDp) {
        this.marginWDp = marginWDp;
        marginWPx = (int) (context.getResources().getDisplayMetrics().density * marginWDp + 0.5f);
    }


    private OnItemClickListener onItemClickListener;
    private int marginHDp = 0;
    private int marginWDp = 0;

    private int marginHPx;
    private int marginWPx;

    public ItemsViewGroup(Context context) {
        super(context, null);
    }

    public ItemsViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        final int count = getChildCount();
        int row = 0;
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int lengthX = 0; // right position of child relative to parent
        int lengthY = 0;
        for (int i = 0; i < count; i++) {
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            lengthX += width + marginWPx;
            lengthY = row * (height + marginHPx) + marginHPx + height;
            if (lengthX > sizeWidth) {
                lengthX = width + marginWPx;
                row++;
                lengthY = row * (height + marginHPx) + marginHPx + height;
            }

        }
        setMeasuredDimension(widthMeasureSpec, (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) ? MeasureSpec.getSize(heightMeasureSpec) : lengthY);
        ///super.onMeasure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int row = 0;
        int lengthX = l; // right position of child relative to parent
        int lengthY = 0;
        for (int i = 0; i < count; i++) {
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            lengthX += width + marginWPx;
            lengthY = (row + 1) * (height + marginHPx);
            if (lengthX > r) {
                lengthX = width + marginWPx + l;
                row++;
                lengthY = (row + 1) * (height + marginHPx);
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);

        }

    }

    public void setData(List<String> titles, int itemTextViewLayoutId) {
        removeAllViews();
        for (int i = 0; i < titles.size(); i++) {
            TextView tv = (TextView) LayoutInflater.from(context).inflate(itemTextViewLayoutId, null);
            tv.setText(titles.get(i));
            tv.setId(i);
            tv.setOnClickListener(this);
            addView(tv);
        }
    }


    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick((TextView) v, v.getId());
    }
}
