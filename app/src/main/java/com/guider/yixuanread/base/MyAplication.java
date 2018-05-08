package com.guider.yixuanread.base;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by zt on 2018/5/7.
 */

public class MyAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
    }
    private void initDb(){
        LitePal.initialize(this);
    }
}
