package com.example.cy.myapplication.activity;

import android.app.Application;

import com.example.cy.myapplication.entity.DaoMaster;
import com.example.cy.myapplication.entity.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by cy on 2017/3/18.
 */

public class MyApplication extends Application{
    public static MyApplication myApplication;

    DaoSession daoSession;
    DaoMaster.DevOpenHelper devOpenHelper;
    DaoMaster daoMaster;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        Fresco.initialize(this);
        devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "user.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }
}
