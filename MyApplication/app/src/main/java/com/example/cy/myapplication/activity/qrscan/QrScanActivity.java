package com.example.cy.myapplication.activity.qrscan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.activity.MyApplication;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.common.HybridBinarizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QrScanActivity extends AppCompatActivity {

    String resultString;
    Bitmap resultBitmap;

    @Bind(R.id.sv)
    SurfaceView sv;

    SurfaceHolder surfaceHolder;

    Camera mCamera;

    private void onSanResult() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        ButterKnife.bind(this);

        init();

    }


    void init() {

        surfaceHolder = sv.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mCamera = Camera.open();

                try {
                    mCamera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCamera.setDisplayOrientation(90);

                mCamera.startPreview();
                startScan();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                try {
                    mCamera.setPreviewDisplay(null);
                    mCamera.stopPreview();
                    mCamera.release();
                } catch (Exception e) {
                }
            }
        });

    }

    private void startScan() {

        if (!TextUtils.isEmpty(resultString)){
            onSanResult();
            return;
        }

        if (isFinishing()) {
            return;
        }
        try {
            mCamera.autoFocus(null);
            mCamera.takePicture(
                    null,//快门回调
                    null,//原图回调
                    new Camera.PictureCallback() {//jepg回调
                        @Override
                        public void onPictureTaken(final byte[] data, Camera camera) {
                            mCamera.startPreview();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        final String string = QrDecoder.decode(bitmap);
                                        final String string2 = QrDecoder.decode(BitmapRotater.adjustPhotoRotation(bitmap,90));

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (!TextUtils.isEmpty(string)) {
                                                    resultString =string;
                                                    resultBitmap = bitmap;
                                                }else if (!TextUtils.isEmpty(string2)){
                                                    resultString =string2;
                                                    resultBitmap = bitmap;
                                                }
                                                startScan();
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
        }catch (Exception e){}

    }





}
