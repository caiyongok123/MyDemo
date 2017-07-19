package com.example.cy.myapplication.activity.qrscan;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.common.HybridBinarizer;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by joson on 2017/7/19.
 */

public class QrDecoder {


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
