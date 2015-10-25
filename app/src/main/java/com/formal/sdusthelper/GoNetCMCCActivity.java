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
import android.widget.Toast;

import pw.isdust.isdust.function.Networkjudge;
import pw.isdust.isdust.function.Networklogin_CMCC;


/**
 * Created by Administrator on 2015/10/23.
 */
public class GoNetCMCCActivity {
    private final static int REQUEST_CODE=1;
    private Context mContext;

    private Button btn_state,btn_login,
            btn_logout,btn_changepwd,btn_query;
    private String str_user1,str_user2,
            str_pwd1,str_pwd2;

    private Networklogin_CMCC obj_cmcc;
    private Networkjudge obj_netstate;
    public GoNetCMCCActivity(Context parent) {
        mContext = parent;
        obj_cmcc = new Networklogin_CMCC(); //实例化cmcc
        //obj_netstate = new Networkjudge(mContext);
    }

    public View Init() {
        View cmcc = LayoutInflater.from(mContext).inflate(
                R.layout.gonet_cmcc, null);

        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferencesCMCC= mContext.getSharedPreferences("CMCCData", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        str_user1 =sharedPreferencesCMCC.getString("username_first", "");
        str_pwd1 =sharedPreferencesCMCC.getString("password_first", "");

        str_user2 =sharedPreferencesCMCC.getString("username_sec", "");
        str_pwd2 =sharedPreferencesCMCC.getString("password_sec", "");

//        int state = obj_netstate.cmcc_judge();  //判断当前CMCC连接不同状态
//        if (state == 0) {
//
//        }
//        else if (state == 1) {
//
//        }
//        else if (state == 2) {
//
//        }
        return cmcc;
    }   //初始化 更新按钮状态

    public void GoFirstNet() {
        String result;
        result = obj_cmcc.login(str_user1, str_pwd1);  //登陆一层
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        if (result.equals("登录成功")) {
            obj_cmcc.cmcc_init();   //为登陆二层做准备
        }
    }   //登录一层

    public void GoSecNet() {
        String result;
        result = obj_cmcc.cmcc_login(str_user2, str_pwd2);  //登陆二层
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }   //登录二层

    public void changePwd() {
        obj_cmcc.cmcc_changepwd("将汉字替换为密码");
    }   //修改密码

    public boolean haveEmptyData() {
        return (str_user1.isEmpty() || str_pwd1.isEmpty() || str_user2.isEmpty() || str_pwd2.isEmpty());
    }//有数据为空，需要编辑

    public void setData(Bundle bundle) {
        str_user1 = bundle.getString("str_user1");
        str_pwd1 = bundle.getString("str_pwd1");
        str_user2 = bundle.getString("str_user2");
        str_pwd2 = bundle.getString("str_pwd2");
    }   //设置用户名密码数据
}
