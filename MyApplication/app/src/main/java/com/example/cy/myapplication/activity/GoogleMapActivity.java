package com.example.cy.myapplication.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.entity.GoogleSearchInfo;
import com.example.cy.myapplication.util.GMapUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class GoogleMapActivity extends AppCompatActivity {

    MapFragment ftMap;
    GMapUtil gMapUtil;
    GMapUtil.GMapUtilListener gMapUtilListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        ButterKnife.bind(this);
        ftMap = (MapFragment) getFragmentManager().findFragmentById(R.id.ft_map);
        initGMapUtilListener();
        gMapUtil = new GMapUtil(ftMap, gMapUtilListener);

    }

    private void initGMapUtilListener() {
        gMapUtilListener = new GMapUtil.GMapUtilListener() {
            @Override
            public void initMapSetting(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                gMapUtil.startLocationServer();
            }

            @Override
            public void onMapClick(LatLng latLng) {

            }

            @Override
            public void onCameraMoved(CameraPosition cameraPosition) {

            }

            @Override
            public void onLocationChanged(Location location, boolean isFirstLocation) {
                if (isFirstLocation){
                    gMapUtil.moveCamera(location);
                }
                gMapUtil.getGoogleAddress(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        GoogleSearchInfo info = new Gson().fromJson(s,GoogleSearchInfo.class);
                        if (info.isSuccess()){

                        }
                        Log.e("地址：",s+"??");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("地址：","地址查询失败");
                    }
                },new LatLng(location.getLatitude(),location.getLongitude()),GoogleMapActivity.this);
            }

            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gMapUtil.stopLocationServer();
    }
}
