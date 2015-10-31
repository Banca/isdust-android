package com.formal.sdusthelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.formal.sdusthelper.baseactivity.BaseCMCCandChinaUnicom;
import com.formal.sdusthelper.baseactivity.BaseSubListPageActivity;
import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;
import com.formal.sdusthelper.view.IsdustDialog;

import pw.isdust.isdust.function.Networkjudge;
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
