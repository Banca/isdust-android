package com.formal.sdusthelper;

import android.app.Application;

import pw.isdust.isdust.function.Xiaoyuanka;

/**
 * Created by Administrator on 2015/10/16.
 */
public class MyApplication extends Application {
    private Xiaoyuanka usercard;

    public void onCreate() {
        super.onCreate();
        usercard = new Xiaoyuanka(this);
    }

    public Xiaoyuanka getUsercard() {
        return usercard;
    }  //使usercard这种非序列化对象 全局可调
}

