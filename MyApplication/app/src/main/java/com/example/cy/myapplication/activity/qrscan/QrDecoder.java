package com.example.cy.myapplication.activity.qrscan;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.common.HybridBinarizer;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by joson on 2017/7/19.
 */

public class QrDecoder {


    /**
     * 用Zbar扫描，不知道为什么，扫描条形码很蛋疼，很难扫，识别率超低
     * @param bm
     * @return
     */
    public static String decode(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        Hashtable<DecodeHintType, Object> hints = new Hashtable();
        initHints(hints, null, "UTF8");
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);

        LuminanceSource source = new BitmapLuance(bm);

        BinaryBitmap bit = new BinaryBitmap(new HybridBinarizer(source));

        try {
            return multiFormatReader.decodeWithState(bit).getText();

        } catch (ReaderException re) {
            // continue
        } finally {
            multiFormatReader.reset();
        }
        return null;
    }


    /**
     * 用Zbar扫描
     *
     * @param bm
     * @return
     */
    /*public static String decode(Bitmap bm) {
        ImageScanner mScanner = new ImageScanner();
        mScanner.setConfig(0, Config.X_DENSITY, 3);
        mScanner.setConfig(0, Config.Y_DENSITY, 3);
        Image mResult = new Image(bm.getWidth(), bm.getHeight(), "Y800");// 第三个参数不知道是干嘛的
        // 设置Image的数据资源
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        mResult.setData(baos.toByteArray());

        int mResultCode = mScanner.scanImage(mResult);
        // 如果代码不为0，表示扫描成功
        if (mResultCode != 0) {
            return mScanner.getResults().iterator().next().getData();
        }
        return null;
    }*/


    public static void initHints(Hashtable<DecodeHintType, Object> hints,
                                 Vector<BarcodeFormat> decodeFormats, String CODE_STYLE) {
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();
            decodeFormats.addAll(MyDecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(MyDecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(MyDecodeFormatManager.DATA_MATRIX_FORMATS);
        }

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
    }
}
