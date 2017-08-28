package com.example.cy.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.cy.myapplication.MyApplication;

/**
 * Created by cy on 2017/8/28.
 */

public class PermissionManager {

    public abstract class Listener {
        abstract void onGranted();

        void onCancel() {
            Toast.makeText(MyApplication.myApplication, "您取消了授权", Toast.LENGTH_LONG).show();
        }

    }

    public static void equestPermission(Activity activities;String[]permissions, Listener listener) {
        boolean granted = true;
        for (String p:permissions){
            if (ContextCompat.checkSelfPermission(MyApplication.myApplication, p) != PackageManager.PERMISSION_GRANTED){
                granted = false;
            }
        }
        if (granted){
            listener.onGranted();
        }else {
            ActivityCompat.requestPermissions(MyApplication.myApplication,permissions, 952792);
        }

    }
}
