package com.isdust.www;

import android.app.Activity;
import android.os.Bundle;

import com.isdust.www.baseactivity.BaseCMCCandChinaUnicom;

import pw.isdust.isdust.function.Networklogin_CMCC;


/**
 * Created by Administrator on 2015/10/23.
 */
public class GoNetCMCCActivity extends BaseCMCCandChinaUnicom {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.gonet_cmcc, "CMCC");  //初始化祖基类
        sharedPreferences = mContext.getSharedPreferences("CMCCData", Activity.MODE_PRIVATE);    //读取数据的对象
        anct_cls = GoNetCMCCAcntActivity.class; // cmcc 的账户页面类
        BaseCMCCandChinaUnicomInit();   //初始化页面及数据

        obj_gonet = new Networklogin_CMCC();    //实例化操作对象
    }
}
