package com.example.cy.myapplication.util;

import android.widget.Toast;

import com.example.cy.common.MyApplication;

/**
 * Created by dell on 2017/11/22.
 */

public class ToastUtil {
    private static Toast mToast = null;

    public synchronized static void showText(int stringId){
        String string = null;
        try {
            string = MyApplication.myApplication.getString(stringId);
        }catch (Exception e){
            string =""+stringId;
        }
        showText(string);
    }
    public synchronized static void showText(String string) {
        synchronized(ToastUtil.class){
            if (mToast==null){
                mToast = new Toast(MyApplication.myApplication);
            }
        }
        mToast.cancel();
        mToast.makeText(MyApplication.myApplication, ""+string,Toast.LENGTH_LONG).show();
    }
}
