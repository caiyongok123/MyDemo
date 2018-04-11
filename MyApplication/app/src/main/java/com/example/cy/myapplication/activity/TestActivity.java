package com.example.cy.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cy.common.base.BaseActivity;
import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.TitleBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity {


    @Bind(R.id.titleBar)
    TitleBar titleBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    FileInputStream fis;

    ArrayList<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        String path = Environment.getExternalStorageDirectory() + "/ppa.txt";//ppa_utf-8.txt//ppa
        File file = new File(path);

        String code = "US-ASCII";

        try {
            fis = new FileInputStream(file);
            int pre = (fis.read() << 8) + fis.read();
            switch (pre) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                case 0x5c75:
                    code = "ASCII";
                    break;
                default:
                    code = "GBK";
                    break;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis = new FileInputStream(file);
            StringBuffer sb = new StringBuffer("");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, code));
            String temp = null;
            int i = 1;
            while ((temp = (br.readLine())) != null) {
                if (temp.equals("")) {
                    continue;
                }
                sb.append(temp);
                sb.append("\n");
                if (i == 50) {
                    strings.add("\n"+sb.toString());
                    sb.setLength(0);
                    i = 1;
                } else {
                    i++;
                }

            }
            strings.add("\n"+sb.toString());


            BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_text) {
                @Override
                protected void convert(BaseViewHolder baseViewHolder, String s) {
                    baseViewHolder.setText(R.id.tv_item, s);
                }
            };
            recyclerView.setAdapter(adapter);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(recyclerView.getLayoutManager() != null) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        //获取可视的第一个view
                        View topView = layoutManager.getChildAt(0);
                        if(topView != null) {
                            //获取与该view的顶部的偏移量
                            int lastOffset = topView.getTop();
                            //得到该View的数组位置
                            int lastPosition = layoutManager.getPosition(topView);

                            SharedPreferences sp = getSharedPreferences("note",MODE_PRIVATE);
                            sp.edit().putInt("lastOffset",lastOffset).commit();
                            sp.edit().putInt("lastPosition",lastPosition).commit();

                        }
                    }
                }
            });


            adapter.addData(strings);
            Log.e("xxxxxxxx", strings.size() + "");

            SharedPreferences sp = getSharedPreferences("note",MODE_PRIVATE);
            //得到该View的数组位置
            int lastPosition = sp.getInt("lastOffset",0);
            int lastOffset = sp.getInt("lastOffset",0);
            if(recyclerView.getLayoutManager() != null && lastPosition >= 0) {
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
