package com.example.cy.common.net;

import android.content.pm.ApplicationInfo;

import com.example.cy.common.MyApplication;
import com.example.cy.myapplication.BuildConfig;

/**
 * Created by dell on 2017/11/20.
 * 网络访问
 */

public class Business {

    static String getBaseUrl() {
        if (BuildConfig.DEBUG) {//测试包
            return "";
        } else {//正式签名打包
            return "";
        }
    }

    public final static String BASE_URL = getBaseUrl();

    public final static String XXX_URL = BASE_URL + "/xxx/xxx";



}
