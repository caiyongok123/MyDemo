package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.cy.myapplication.R;

import java.util.ArrayList;

/**
 * Created by cy on 2017/10/23.
 *
 * 适用于字符串列表做选择的PopupWindow
 *
 * 需要提供子布局，（子布局必须含有 TextView,并且id为tv_item）如：
 *
 * <?xml version="1.0" encoding="utf-8"?>
 * <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
 *      android:layout_width="wrap_content"
 *      android:layout_height="wrap_content"
 *      >
 *     <TextView
 *          android:id="@+id/tv_item"
 *          android:gravity="center"
 *          android:layout_width="100dp"
 *          android:layout_height="50dp"
 *          />
 * </RelativeLayout>
 *
 *      举例：
 *      new StringsPopupWindow(context, strings, R.layout.item_text_50dp_100dp) {
 *          @Override
 *          public void onItemClick(View itemView, int position, String itemString) {
 *                  Toast.makeText(StringsPopupWindowTestActivity.this, itemString, Toast.LENGTH_LONG).show();
 *          }
 *      }.showAsDropDown(view);
 *
 */

public abstract class StringsPopupWindow extends PopupWindow {

    ArrayList<String> strings;
    Context context;
    int itemLayoutId;

    LinearLayout ll;

    public StringsPopupWindow(Context context, ArrayList<String> strings, int itemLayoutId) {
        super(LayoutInflater.from(context).inflate(R.layout.layout_items_popup_window, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        this.strings = strings;
        this.context = context;
        this.itemLayoutId = itemLayoutId;

        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        initView();
    }

    private void initView() {
        View view = getContentView();
        ll = (LinearLayout) view.findViewById(R.id.ll);

        for (int i = 0; i < strings.size(); i++) {
            final String string = strings.get(i);
            final int position = i;
            View itemView = LayoutInflater.from(context).inflate(itemLayoutId, null);
            TextView tv = (TextView) itemView.findViewById(R.id.tv_item);
            tv.setText(string);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    onItemClick(v, position, string);
                }
            });
            ll.addView(itemView);
        }
    }

    public abstract void onItemClick(View itemView, int position, String itemString);
}
