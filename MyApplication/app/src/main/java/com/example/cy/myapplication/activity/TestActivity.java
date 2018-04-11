package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
            adapter.addData(strings);

            Log.e("xxxxxxxx", strings.size() + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号  
        String regEx = "[『』]"; // 清除掉特殊字符  
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
