package com.example.cy.myapplication.activity.qrscan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

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


    public static String decodeByZXing(byte[] data,int w,int h,int size){

        ByteArrayOutputStream baos;
        Bitmap bitmap;

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        YuvImage yuvimage = new YuvImage(
                data,
                ImageFormat.NV21,
                w,
                h,
                null);
        baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect((w-size)/2, (h-size)/2, (w+size)/2, (h+size)/2), 100, baos);// 80--JPG图片的质量[0-100],100最高
        byte[] rawImage = baos.toByteArray();
        //将rawImage转换成bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);

        MultiFormatReader multiFormatReader = new MultiFormatReader();

        // 解码的参数
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
        // 可以解析的编码类型
        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();

            // 这里设置可扫描的类型，我这里选择了都支持
            decodeFormats.addAll(MyDecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(MyDecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(MyDecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        // 设置继续的字符编码格式为UTF8
        // hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
        // 设置解析配置参数
        multiFormatReader.setHints(hints);

        // 开始对图像资源解码
        Result rawResult = null;
        try {
            rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(bitmap))));
            try {
                bitmap.recycle();
                bitmap=null;
            }catch (Exception e){}
            return rawResult.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        /*try {
            Bitmap bp = BitmapFactory.decodeByteArray(data,w,h);
            bp = Bitmap.createBitmap(bp,(w-size)/2,(h-size)/2,size,size);


            LuminanceSource source = new BitmapLuance(bp);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            return result.getText();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("zxing二维码解析错误",e.getMessage());
            return null;
        }*/

    }
}
