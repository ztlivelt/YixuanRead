package com.guider.yixuanread.base;

import android.app.Application;

import com.guider.yixuanread.widget.PageFactory;

import org.litepal.LitePal;

/**
 * Created by zt on 2018/5/7.
 */

public class MyAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
        Config.createConfig(this);
        PageFactory.createPageFactory(this);
    }
    private void initDb(){
        LitePal.initialize(this);
    }
}
