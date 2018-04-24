package com.example.cy.myapplication.widget.view.cameraview;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.ExifInterface;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cy on 2018/4/23.
 */

public class CameraView extends SurfaceView {

    int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    Camera mCamera;
    SurfaceHolder surfaceHolder;


    public CameraView(Context context) {
        super(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mCamera = Camera.open(cameraId);
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


        //设置触摸对焦
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    onFocus(new Point((int) (motionEvent.getX()), (int) (motionEvent.getY())));
                }
                return false;
            }
        });
    }


    public void switchCamera() {

        if (Camera.getNumberOfCameras() < 2) {
            return;
        }

        if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            //后置摄像头
            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {  // 前置摄像头
            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }

        mCamera.stopPreview();//停掉原来摄像头的预览
        mCamera.release();//释放资源
        mCamera = null;//取消原来摄像头
        mCamera = Camera.open(cameraId);//打开当前选中的摄像头
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> SupportedPreviewSizes = parameters.getSupportedPreviewSizes();// 获取支持预览照片的尺寸
            Camera.Size previewSize = SupportedPreviewSizes.get(0);// 从List取出Size
            parameters.setPreviewSize(previewSize.width, previewSize.height);//
            //设置预览照片的大小
            List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();// 获取支持保存图片的尺寸
            Camera.Size pictureSize = supportedPictureSizes.get(0);// 从List取出Size
            parameters.setPictureSize(pictureSize.width, pictureSize.height);//
            //设置照片的大小
            mCamera.setParameters(parameters);

            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }


    /**
     * 手动聚焦
     *
     * @param point 触屏坐标
     */
    protected boolean onFocus(Point point) {
        if (mCamera == null) {
            return false;
        }

        Camera.Parameters parameters = null;
        try {
            parameters = mCamera.getParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //不支持设置自定义聚焦，则使用自动聚焦，返回

        if (Build.VERSION.SDK_INT >= 14) {

            if (parameters.getMaxNumFocusAreas() <= 0) {
                return focus();
            }

            Log.i("CameraView", "onCameraFocus:" + point.x + "," + point.y);

            //定点对焦
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            int left = point.x - 300;
            int top = point.y - 300;
            int right = point.x + 300;
            int bottom = point.y + 300;
            left = left < -1000 ? -1000 : left;
            top = top < -1000 ? -1000 : top;
            right = right > 1000 ? 1000 : right;
            bottom = bottom > 1000 ? 1000 : bottom;
            areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
            parameters.setFocusAreas(areas);
            try {
                List<Camera.Size> SupportedPreviewSizes = parameters.getSupportedPreviewSizes();// 获取支持预览照片的尺寸
                Camera.Size previewSize = SupportedPreviewSizes.get(0);// 从List取出Size
                parameters.setPreviewSize(previewSize.width, previewSize.height);//
                //设置预览照片的大小
                List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();// 获取支持保存图片的尺寸
                Camera.Size pictureSize = supportedPictureSizes.get(0);// 从List取出Size
                parameters.setPictureSize(pictureSize.width, pictureSize.height);//
                //设置照片的大小
                mCamera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        return focus();
    }

    private boolean focus() {
        try {
            mCamera.autoFocus(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCamera.startPreview();
        }
    };

    void save(final String filePath, final byte[] data) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File f = new File(filePath);
                if (f.exists()) {
                    f.delete();
                }
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream os = new FileOutputStream(f);
                    os.write(data);
                    os.close();
                    ExifInterface exifInterface = new ExifInterface(filePath);
                    if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {//后置摄像头的拍照要旋转90度
                        exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_90 + "");
                    } else {
                        exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_270 + "");
                    }
                    exifInterface.saveAttributes();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                han.sendEmptyMessage(0);
            }
        }).start();

    }


    /**
     * 拍照
     *
     * @param jpgFilePath 照片保持路径
     * @param sound       是否需要系统快门声
     */
    public void takePicture(final String jpgFilePath, boolean sound) {
        mCamera.takePicture(
                sound ? new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {
                    }
                } : null,
                null,//这里是原图回调，但是返回的数据老是空的，没什么卵用，所以干脆不用它
                new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {//jepg回调
                        if (!TextUtils.isEmpty(jpgFilePath) && bytes != null && bytes.length > 0) {
                            save(jpgFilePath, bytes);
                        }
                    }
                }
        );
    }


    MediaRecorder mMediaRecorder = new MediaRecorder();//录制视频器
    boolean isRecording = false;//是否正在录制视频
    boolean isOnPause = false;
    long recordTime = 0;//录制时长(毫秒数)
    long startRecordTime;//开始录制的时间

    /**
     * 开始录制mp4
     */
    public void startRecorderMp4(final String mp4FilePath) {
        File f = new File(mp4FilePath);
        if (f.exists()) {
            f.delete();
        }
        if (mCamera != null) {
            stopRecorderMp4();
        }
        mCamera.unlock();
        setConfigRecord();
        mMediaRecorder.setOutputFile(mp4FilePath);
        try {
            //开始录制
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            startRecordTime = System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRecording = true;

    }

    /**
     * 暂停录制mp4
     */
    public void pauseRecorderMp4(final String mp4FilePath) {

    }

    /**
     * 恢复录制mp4
     */
    public void resumeRecorderMp4(final String mp4FilePath) {

    }

    /**
     * 停止录制mp4
     *
     * @return 录制时长(秒数)
     */
    public int stopRecorderMp4() {
        try {
            mMediaRecorder.stop();
            int time = (int) (recordTime / 1000 + (System.currentTimeMillis() - startRecordTime) / 1000);
            recordTime = 0;
            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 配置MediaRecorder()
     */
    private void setConfigRecord() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mediaRecorder, int i, int i1) {

            }
        });

        //使用SurfaceView预览
        mMediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());

        //1.设置采集声音
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置采集图像
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //2.设置视频，音频的输出格式 mp4
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        //3.设置音频的编码格式
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //设置图像的编码格式
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //设置立体声
//        mediaRecorder.setAudioChannels(2);
        //设置最大录像时间 单位：毫秒
//        mediaRecorder.setMaxDuration(60 * 1000);
        //设置最大录制的大小 单位，字节
//        mediaRecorder.setMaxFileSize(1024 * 1024);
        //音频一秒钟包含多少数据位
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        mMediaRecorder.setAudioEncodingBitRate(44100);
        if (mProfile.videoBitRate > 2 * 1024 * 1024)
            mMediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
        else
            mMediaRecorder.setVideoEncodingBitRate(1024 * 1024);
        mMediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);

        //设置选择角度，顺时针方向，因为默认是逆向90度的，这样图像就是正常显示了,这里设置的是观看保存后的视频的角度
        if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            mMediaRecorder.setOrientationHint(90);
        }else {
            mMediaRecorder.setOrientationHint(270);
        }

        //设置录像的分辨率
        //mMediaRecorder.setVideoSize(352, 288);
        mMediaRecorder.setVideoSize(640, 480);
    }

}
