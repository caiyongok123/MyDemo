package com.example.cy.myapplication.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.cy.common.MyApplication;

/**
 * Created by 蔡勇 on 2017/8/21.
 * 谷歌定位工具类
 */

public class GLocationUtil {
    //定位器
    public LocationManager mLocationManager;

    LocationListener mLocationListener;

    //第一次定位成功
    boolean isFirstLoc = true;



    /**
     * 开始定位
     */
    public void startLocationServer(final OnLocationChangedListener mOnLocationChangedListener) {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) MyApplication.myApplication.getSystemService(Context.LOCATION_SERVICE);
        }
        if (mLocationListener == null) {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (isFirstLoc){
                        mOnLocationChangedListener.onLocationChanged(location,true);
                        isFirstLoc = false;
                    }else {
                        mOnLocationChangedListener.onLocationChanged(location,false);
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 50, mLocationListener);
        }

    }

    /**
     * 停止定位
     */
    public void stopLocationServer() {
        if (mLocationManager != null && mLocationListener != null) {
            mLocationManager.removeUpdates(mLocationListener);
            mLocationListener = null;
        }
    }

    public interface OnLocationChangedListener{
        /**
         * 当定位位置改变
         * @param location
         * @param isFirstLocation 是否第一次定位
         */
        void onLocationChanged(Location location, boolean isFirstLocation);
    }
}
