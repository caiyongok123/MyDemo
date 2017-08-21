package com.example.cy.myapplication.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.cy.myapplication.MyApplication;
import com.example.cy.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 蔡勇 on 2017/8/21.
 * 谷歌地图工具类(包括定位功能)
 */

public class GMapUtil implements OnMapReadyCallback {

    public MapFragment mMapFragment;
    public GoogleMap mGoogleMap;

    //定位器
    GLocationUtil mGLocationUtil;

    //在UI界面中要重写方法的各种回调
    GMapUtilListener mGMapUtilListener;

    //第一次移动地图镜头
    boolean firstMove = true;


    public GMapUtil(MapFragment ftMap, GMapUtilListener listener) {
        mMapFragment = ftMap;
        mGMapUtilListener = listener;
        ftMap.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGLocationUtil = new GLocationUtil();
        mGMapUtilListener.initMapSetting(googleMap);
        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (firstMove) {
                    firstMove = false;
                } else {
                    mGMapUtilListener.onCameraMoved(mGoogleMap.getCameraPosition());
                }
            }
        });
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mGMapUtilListener != null) {
                    mGMapUtilListener.onMapClick(latLng);
                }
            }
        });
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return mGMapUtilListener.onMarkerClick(marker);
            }
        });


    }

    /**
     * 开始定位
     */
    public void startLocationServer() {
        mGLocationUtil.startLocationServer(new GLocationUtil.OnLocationChangedListener() {
            @Override
            public void onLocationChanged(Location location, boolean isFirstLocation) {
                mGMapUtilListener.onLocationChanged(location, isFirstLocation);
            }
        });
    }

    /**
     * 停止定位
     */
    public void stopLocationServer() {
        mGLocationUtil.stopLocationServer();
    }


    /**
     * 移动镜头到指定位置
     *
     * @param latLng
     */
    public void moveCamera(LatLng latLng) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    /**
     * 移动镜头到指定位置（指定缩放级别）
     *
     * @param latLng
     * @param zoom
     */
    public void moveCamera(LatLng latLng, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    /**
     * 移动镜头到指定位置
     *
     * @param location
     */
    public void moveCamera(Location location) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    /**
     * 移动镜头到指定位置（指定缩放级别）
     *
     * @param location
     * @param zoom
     */
    public void moveCamera(Location location, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom));
    }

    /**
     * 地图的各种回调
     */
    public interface GMapUtilListener {

        /**
         * 谷歌地图加载完毕后，进行UI等设置
         *
         * @param googleMap
         */
        void initMapSetting(GoogleMap googleMap);

        /**
         * 当地图被点击
         *
         * @param latLng
         */
        void onMapClick(LatLng latLng);

        /**
         * 当地图镜头完成移动
         *
         * @param cameraPosition
         */
        void onCameraMoved(CameraPosition cameraPosition);


        /**
         * 当定位位置改变
         *
         * @param location
         * @param isFirstLocation 是否第一次定位
         */
        void onLocationChanged(Location location, boolean isFirstLocation);

        /**
         * Marker点被点击 （market可以设置Tag,相当于高德地图的market设置Object）
         *
         * @param marker
         * @return
         */
        boolean onMarkerClick(Marker marker);
    }


    /**
     * 添加带数字地标
     */
    public static Marker addMarker(GoogleMap map, LatLng latLng, int num, BitmapDescriptor bpDr) {
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(bpDr)
                .position(latLng)
                .draggable(true);

        //在地图上添加地标
        return map.addMarker(markerOptions);
    }


    /**
     * 添加带数字地标
     */
    public static Marker addMarker(GoogleMap map, LatLng latLng, int num, int layoutId) {
        Bitmap bitmap = getNumberBitMap(num, layoutId);
        BitmapDescriptor bpDr = BitmapDescriptorFactory.fromBitmap(bitmap);

        MarkerOptions markerOptions = new MarkerOptions()
                .icon(bpDr)
                .position(latLng)
                .draggable(true);

        //在地图上添加地标
        return map.addMarker(markerOptions);
    }

    public static BitmapDescriptor getBitmapDescriptor(int num, int layoutId) {
        Bitmap bitmap = getNumberBitMap(num, layoutId);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    /**
     * 查询谷歌地址
     * @param restListener
     * @param latLng
     * @param tag
     */
    public void getGoogleAddress(StringCallback restListener, LatLng latLng,Object tag) {
        ApplicationInfo info = MyApplication.myApplication.getApplicationInfo();
        String googleMapKey = info.metaData.getString("com.google.android.geo.API_KEY");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latLng.latitude + "," + latLng.longitude + "&key=" + googleMapKey;
        OkGo.getInstance().cancelTag(tag);
        OkGo.get(url).tag(tag).execute(restListener);
    }


    public String getGoogleAppKey(){
        ApplicationInfo info = MyApplication.myApplication.getApplicationInfo();
        return info.metaData.getString("com.google.android.geo.API_KEY");
    }


    /**
     * 带数字的宝藏图标BitMap
     *
     * @param num
     * @return
     */
    private static Bitmap getNumberBitMap(int num, int layoutId) {
        View view = LayoutInflater.from(MyApplication.myApplication).inflate(layoutId, null);
        if (num > 0)
            ((TextView) view.findViewById(R.id.tv_num)).setText(num + "");
        return convertViewToBitmap(view);
    }

    //view 转bitmap
    private static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
