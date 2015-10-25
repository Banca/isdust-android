package com.formal.sdusthelper;

import android.app.Application;

import pw.isdust.isdust.function.SchoolCard;

/**
 * Created by Administrator on 2015/10/16.
 */
public class MyApplication extends Application {
    private SchoolCard usercard;
    public void onCreate() {
        super.onCreate();
        usercard = new SchoolCard(this);
    }

    public SchoolCard getUsercard() {
        return usercard;
    }  //使usercard这种非序列化对象 全局可调
}

