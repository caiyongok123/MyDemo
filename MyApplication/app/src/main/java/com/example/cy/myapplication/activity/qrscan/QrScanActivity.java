package com.example.cy.myapplication.activity.qrscan;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cy.myapplication.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QrScanActivity extends AppCompatActivity {

    String resultString;
    Bitmap resultBitmap;

    @Bind(R.id.sv)
    SurfaceView sv;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.ll)
    LinearLayout ll;

    SurfaceHolder surfaceHolder;

    Camera mCamera;


    /**
     * 扫描成功了，这里就不会继续扫了，结果已经拿到了
     */
    private void onSanResult(String resultStr, Bitmap resultmap) {
        Toast.makeText(this, resultStr, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        ButterKnife.bind(this);

        init();

        startAnim();

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


    /**
     * 开始扫描
     */
    private void startScan() {

        if (!TextUtils.isEmpty(resultString)) {
            onSanResult(resultString, resultBitmap);
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
                                        final Bitmap cutBitmap = getCutBitmap(bitmap);
                                        final Bitmap cutBitmap2 = BitmapRotater.adjustPhotoRotation(cutBitmap, 90);

                                        final String string = QrDecoder.decode(cutBitmap);
                                        final String string2 = QrDecoder.decode(cutBitmap2);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (!TextUtils.isEmpty(string)) {
                                                    resultString = string;
                                                    resultBitmap = bitmap;
                                                } else if (!TextUtils.isEmpty(string2)) {
                                                    resultString = string2;
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
        } catch (Exception e) {
        }

    }


    /**
     * 播放扫描动画
     */
    private void startAnim() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(iv, "translationY", dp2px(200 - 2 * 5));
        oa.setDuration(1500);
        oa.setRepeatCount(-1);
        oa.start();
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }


    /**
     * 获取根据扫描框位置和尺寸进行裁剪后的有效扫描区的bitmap
     *
     * @param bitmap
     * @return
     */
    Bitmap getCutBitmap(Bitmap bitmap) {

        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int size = w * ll.getWidth() / getWindowManager().getDefaultDisplay().getWidth();


        int retX = (w - size) / 2;
        int retY = (h - size) / 2;

        return Bitmap.createBitmap(bitmap, retX, retY, size, size, null,
                false);


    }

}
