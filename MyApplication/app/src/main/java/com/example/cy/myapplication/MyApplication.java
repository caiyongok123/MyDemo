package com.example.cy.myapplication;

import android.app.Application;

import com.example.cy.myapplication.entity.DaoMaster;
import com.example.cy.myapplication.entity.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by cy on 2017/3/18.
 */

public class MyApplication extends Application{
    public static MyApplication myApplication;

    public DaoSession daoSession;
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

        OkGo.init(this);
        OkGo.getInstance().setConnectTimeout(10 * 1000)               //全局的连接超时时间
                .setReadTimeOut(60 * 1000)                  //全局的读取超时时间
                .setWriteTimeOut(60 * 1000)
                .setCookieStore(new PersistentCookieStore());//MemoryCookieStore()
    }
}
