package com.example.cy.myapplication.activity;

import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.example.cy.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 摄像头预览，拍照发送图片到服务器进行识别
 */
public class CameraPreviewActivity extends AppCompatActivity {


    @Bind(R.id.sv)
    SurfaceView sv;
    @Bind(R.id.bt_confirm)
    Button btConfirm;

    SurfaceHolder surfaceHolder;

    Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
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
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

    @OnClick(R.id.bt_confirm)
    public void onViewClicked() {
        mCamera.takePicture(
                null,//快门回调
                null,//原图回调
                new Camera.PictureCallback() {//jepg回调
                    @Override
                    public void onPictureTaken(final byte[] data, Camera camera) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                File f = new File(Environment.getExternalStorageDirectory().getPath() + "/111111.jpg");
                                try {
                                    FileOutputStream os = new FileOutputStream(f);
                                    os.write(data);
                                    os.close();
                                    ExifInterface exifInterface = new ExifInterface(Environment.getExternalStorageDirectory().getPath() + "/111111.jpg");
                                    exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_90+"");
                                    exifInterface.saveAttributes();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCamera.startPreview();
                                    }
                                });
                            }
                        }).start();
                    }
                });
    }

    //快门按下的时候onShutter()被回调
    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            //发出提示用户的声音

        }
    };

}
