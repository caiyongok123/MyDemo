package com.example.cy.myapplication.activity.qrscan;

import android.animation.ObjectAnimator;
import android.graphics.BitmapFactory;
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
import com.example.cy.myapplication.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QrScanActivity extends AppCompatActivity {

    String resultString;

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
    private void onSanResult(String resultStr) {
        Log.e("打印：",resultStr+">>");
        ToastUtil.showText(resultStr);
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
                    mCamera.setPreviewCallback(null);
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
            onSanResult(resultString);
            return;
        }

        if (isFinishing()) {
            return;
        }
        try {
            mCamera.autoFocus(null);
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {

                @Override
                public void onPreviewFrame(final byte[] data1, final Camera camera) {

                    mCamera.startPreview();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String string = QrDecoder.decodeByZXing(data1,camera.getParameters().getPreviewSize().width,camera.getParameters().getPreviewSize().height,ll.getWidth());
                            //final String string = QrDecoder.decode(data1,camera.getParameters().getPreviewSize().width,camera.getParameters().getPreviewSize().height,ll.getWidth());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //if (!TextUtils.isEmpty(string)) {
                                        resultString = string;
                                    //}
                                    startScan();
                                }
                            });

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


}
