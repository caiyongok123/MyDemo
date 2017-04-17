package com.example.cy.myapplication.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by joson on 2017/4/6.
 * 手机旋转监听
 */
public abstract class RotateSensorListener {


    Context context;
    SensorManager sensorManager;
    SensorEventListener sensorEventListener;


    public RotateSensorListener(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void register() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener = new SensorEventListener() {
                                           @Override
                                           public void onSensorChanged(SensorEvent event) {
                                               try {
                                                   onSensorEvent(event.values);
                                               } catch (Exception e) {
                                                   e.printStackTrace();
                                               }
                                           }

                                           @Override
                                           public void onAccuracyChanged(Sensor sensor, int accuracy) {

                                           }
                                       },
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST
        );
    }

    public void unRegister(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    int qianfanTag = 0;//(对应第二个返回值)  前空翻标志，旋转0度:0，旋转90度:1，旋转180度:2，旋转270度:3，旋转0度:0;
    int qianfanNum = 0;//前翻圈数统计

    int houfanTag = 0;
    int houfanNum = 0;

    void onSensorEvent(float[] eventValues) {
        qianfan(eventValues[1]);
        houfan(eventValues[1]);
    }

    private void qianfan(float value) {
        if (Math.abs(value) < 45) {
            if (qianfanTag == 3) {
                qianfanNum++;
                onQianfan(qianfanNum);
            }
            qianfanTag = 0;

        } else if (value < 135 && value > 45) {
            if (qianfanTag == 0) {
                qianfanTag = 1;
            }else if (qianfanTag!=1){
                qianfanTag = 0;
            }

        } else if (Math.abs(value) > 135) {
            if (qianfanTag == 1) {
                qianfanTag = 2;
            }else  if (qianfanTag!=2){
                qianfanTag = 0;
            }
        } else if (value <= -45 && value >= -135) {
            if (qianfanTag == 2) {
                qianfanTag = 3;
            }else  if (qianfanTag!=3){
                qianfanTag = 0;
            }
        }


    }

    private void houfan(float value) {
        if (Math.abs(value) <= 45) {
            if (houfanTag == 3) {
                houfanNum++;
                onHoufan(houfanNum);
            }
            houfanTag = 0;

        } else if (value <= -45 && value >= -135) {
            if (houfanTag == 0) {
                houfanTag = 1;
            }else if (houfanTag!=1){
                houfanTag = 0;
            }

        } else if (Math.abs(value) >= 135) {
            if (houfanTag == 1) {
                houfanTag = 2;
            }else  if (houfanTag!=2){
                houfanTag = 0;
            }
        } else if (value >= 45 && value <= 135) {
            if (houfanTag == 2) {
                houfanTag = 3;
            }else  if (houfanTag!=3){
                houfanTag = 0;
            }
        }


    }

    public abstract void onQianfan(int num);
    public abstract void onHoufan(int num);

}
