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

import com.formal.sdusthelper.baseactivity.BaseSubListPageActivity;
import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;
import com.formal.sdusthelper.view.IsdustDialog;

import pw.isdust.isdust.function.Networkjudge;
import pw.isdust.isdust.function.Networklogin_CMCC;


/**
 * Created by Administrator on 2015/10/23.
 */
public class GoNetCMCCActivity {
    private final static int REQUEST_CODE=1;
    private Context mContext;

    private String str_user1,str_user2,
            str_pwd1,str_pwd2;

    private Networklogin_CMCC obj_cmcc;

    public GoNetCMCCActivity(Context parent) {
        mContext = parent;
        obj_cmcc = new Networklogin_CMCC(); //实例化cmcc
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

        return cmcc;
    }   //初始化 更新按钮状态


    public boolean GoFirstNet() {
        String result;
        result = obj_cmcc.login(str_user1, str_pwd1);  //登陆一层
        if (result.equals("登录成功")) {
            obj_cmcc.cmcc_init();   //为登陆二层做准备
            return true;
        }
        else
            return false;
    }   //登录一层

    public boolean GoSecNet() {
        String result;
        result = obj_cmcc.cmcc_login(str_user2, str_pwd2);  //登陆二层
        return result.equals("登录成功");
    }   //登录二层

    public void changePwd(String pwd) {
        obj_cmcc.cmcc_changepwd(pwd);

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
