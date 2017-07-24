package com.example.cy.myapplication.activity.qrscan;

import android.util.Log;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;

import java.util.Iterator;

/**
 * Created by joson on 2017/7/19.
 */

public class QrDecoder {

    /**
     * 用Zbar扫描
     */

    public static String decode(byte[] data,int w,int h,int size) {
        ImageScanner mScanner = new ImageScanner();
        mScanner.setConfig(0, Config.X_DENSITY, 3);
        mScanner.setConfig(0, Config.Y_DENSITY, 3);

        Image mResult = new Image(w, h, "Y800");// 第三个参数不知道是干嘛的



        mResult.setCrop((w-size)/2,(h-size)/2,w-(w-size)/2,h-(h-size)/2);
        // 设置Image的数据资源
        mResult.setData(data);

        int mResultCode = mScanner.scanImage(mResult);
        // 如果代码不为0，表示扫描成功
        if (mResultCode != 0) {
            Iterator<Symbol> ss = mScanner.getResults().iterator();
            while (ss.hasNext()){
                Symbol sl  = ss.next();
                Log.e("扫描结果:",sl.getType() +"-----------------"+sl.getData());
            }
            return mScanner.getResults().iterator().next().getData();
        }
        return null;
    }
}
