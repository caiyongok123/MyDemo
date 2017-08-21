package com.example.cy.myapplication.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import com.example.cy.myapplication.MyApplication;
import com.example.cy.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
                try {
                    mCamera.setPreviewDisplay(null);
                    mCamera.stopPreview();
                } catch (Exception e) {
                }
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
                        mCamera.startPreview();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    final String log = openUrl(data);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MyApplication.myApplication,log,Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                /*File f = new File(Environment.getExternalStorageDirectory().getPath() + "/111111.jpg");
                                try {
                                    FileOutputStream os = new FileOutputStream(f);
                                    os.write(data);
                                    os.close();
                                    ExifInterface exifInterface = new ExifInterface(Environment.getExternalStorageDirectory().getPath() + "/111111.jpg");
                                    exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_90 + "");
                                    exifInterface.saveAttributes();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCamera.startPreview();
                                    }
                                });*/
                            }
                        }).start();
                    }
                });
    }


    class ImgUploader {
    }


    public static String openUrl(byte[] data) throws Exception {
        URL urls = new URL("http://52.33.105.73:5000/predict/upload");
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        String rs = null;
        try {
            connection = (HttpURLConnection) urls.openConnection();
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----111111111111111111111111");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(20000);
            connection.setRequestMethod("POST");

            outputStream = connection.getOutputStream();
            outputStream.write(data);
            try {
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    rs = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                }
            } catch (Exception e) {
                rs = null;
            }



            return rs;
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
            }
            outputStream = null;

            if (connection != null)
                connection.disconnect();
            connection = null;
        }

    }

}
