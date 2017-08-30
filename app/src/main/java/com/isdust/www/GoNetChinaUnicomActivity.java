package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity;
import com.isdust.www.view.IsdustDialog;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Networkjudge;

/**
 * Created by Leng Hanchao on 2015/10/31.
 * Midified and Refactored by Wang Ziqiang.
 * isdust
 Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class GoNetChinaUnicomActivity  extends BaseSubPageActivity {
    final int type_chengshiredian =1,type_cmcc=3,type_chinaunicom=2;
    ImageButton mImageButton_state;
    IsdustDialog customRuningDialog;  //自定义运行中提示框
    Networkjudge mNetworkjudge;

    //线程池构建
    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    String xiancheng_chinaunicom_user,xiancheng_chinaunicom_password;
    String xiancheng_chinaunicom_status;
    String xiancheng_toastmessage;
    int xiancheng_network_type, xiancheng_network_chinauicom_condition;

    android.os.Handler mHandler=new android.os.Handler(){
        @Override
    public void handleMessage(Message msg){
            super.handleMessage(msg);
            Message message=new Message();
            if (msg.what==0){//已连接,登录0层
                customRuningDialog.dismiss();
                mImageButton_state.setBackgroundResource(R.drawable.cmcc_0);//设置状态按钮
                return;

            }
            if (msg.what==1){//已连接,登录1层
                customRuningDialog.dismiss();
                mImageButton_state.setBackgroundResource(R.drawable.cmcc_2);//设置状态按钮
                return;
                }
           if (msg.what==2){//登录成功
               Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
               customRuningDialog.dismiss();
               message.what=1;
               mHandler.sendMessage(message);


                }
            if (msg.what==3){//登录失败，密码错误
                Toast.makeText(mContext,xiancheng_chinaunicom_status,Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                mSharedPreferences_chinaunicom.edit().putString("network_ChinaUnicom_password", "");
                mSharedPreferences_chinaunicom.edit().commit();
                Intent intent=new Intent();
                intent.setClass(mContext, NetworkPublicLoginActivity.class);
                intent.putExtra("type", type_chinaunicom);
                startActivityForResult(intent, type_chinaunicom);
                return;

            }
            if (msg.what==4){//登录失败，未知错误
                Toast.makeText(mContext,xiancheng_chinaunicom_status,Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                return;
            }
            if (msg.what==10){//网络超时
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 11){//显示信息
                customRuningDialog.dismiss();
                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage(xiancheng_toastmessage);
                return;
            }
            if (msg.what==12){//网络信号不好
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "网络信号不好，请找一个信号好的地方", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    Runnable xiancheng_autologin=new Runnable() {
        @Override
        public void run() {
            Message messgae=new Message();
            try {
                xiancheng_network_chinauicom_condition =mNetworkjudge.chinaunicom_judge();
            } catch (IOException e) {
                messgae.what=12;
                mHandler.sendMessage(messgae);
                return;
            }
            if (xiancheng_network_chinauicom_condition==1){//登录1层
                messgae.what=1;
                mHandler.sendMessage(messgae);
                return;

            }
            if (xiancheng_network_chinauicom_condition==0){//登录0层
                if (xiancheng_chinaunicom_user.equals("")||xiancheng_chinaunicom_password.equals("")){
                    Intent intent=new Intent();
                    intent.setClass(mContext, NetworkPublicLoginActivity.class);
                    intent.putExtra("type", type_chinaunicom);
                    startActivityForResult(intent, type_chinaunicom);
                    return;
                }else {
                    mExecutorService.execute(xiancheng_runnable_chinauicom_login);
                    xiancheng_toastmessage="正在登录第一层...";
                    Message mMessage=new Message();
                    mMessage.what=11;
                    mHandler.sendMessage(mMessage);
                    return;
                }


            }

        }
    };


    Runnable xiancheng_runnable_chinauicom_login =new Runnable() {//1层登录
        @Override
        public void run() {
            Message message=new Message();
            try {
//                isdustapp.getNetworklogin_ChinaUnicom().login()
                xiancheng_chinaunicom_status=isdustapp.getNetworklogin_ChinaUnicom().login(xiancheng_chinaunicom_user, xiancheng_chinaunicom_password);
            } catch (Exception e) {
                message.what=10;
                mHandler.sendMessage(message);
                return;
            }
            if (xiancheng_chinaunicom_status.equals("登录成功")){
                message.what=2;//1层登录成功
                mHandler.sendMessage(message);
                return;
            }

            if (xiancheng_chinaunicom_status.equals("密码错误")){
                message.what=3;//1层密码错误
                mHandler.sendMessage(message);
                return;
            }

            message.what=4;//1层登录失败
            mHandler.sendMessage(message);
            return;
        }
    };



    SharedPreferences mSharedPreferences_chinaunicom;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.gonet_chinaunicom, "ChinaUnicom");  //初始化祖基类
        //MobclickAgent.onEvent(this, "network_ChinaUnicom");

        mNetworkjudge=new Networkjudge(mContext);
        mImageButton_state = (ImageButton) findViewById(R.id.btn_state); //连接状态按钮
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框
        isconnect();//没连接CMCC自动退出
        mSharedPreferences_chinaunicom=getSharedPreferences("NetworkLogin", Activity.MODE_PRIVATE);

        xiancheng_chinaunicom_user=mSharedPreferences_chinaunicom.getString("network_ChinaUnicom_user", "");
        xiancheng_chinaunicom_password=mSharedPreferences_chinaunicom.getString("network_ChinaUnicom_password", "");


        xiancheng_toastmessage="正在初始化";
        Message message=new Message();
        message.what=11;
        mHandler.sendMessage(message);
        mExecutorService.execute(xiancheng_autologin);
    }
    public void isconnect(){
        xiancheng_network_type =mNetworkjudge.judgetype();
        if (xiancheng_network_type !=2){
            Toast.makeText(mContext, "请连接ChinaUnicom以后再使用本功能", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_state:
                Message message;

                try {
                    xiancheng_network_chinauicom_condition =mNetworkjudge.chinaunicom_judge();
                } catch (IOException e) {
                    message=new Message();
                    message.what=0;
                    mHandler.sendMessage(message);
                    return;
                }

                if (xiancheng_network_chinauicom_condition== 1){
                    try {
                        isdustapp.getNetworklogin_ChinaUnicom().logout();
                    } catch (IOException e) {
                         message=new Message();
                        message.what=10;
                        mHandler.sendMessage(message);
                        return;
                    }
                }

                message=new Message();
                message.what=0;
                mHandler.sendMessage(message);
                mSharedPreferences_chinaunicom.edit().putString("network_ChinaUnicom_user", "");
                mSharedPreferences_chinaunicom.edit().putString("network_ChinaUnicom_password", "");
                mSharedPreferences_chinaunicom.edit().commit();
                Intent intent=new Intent();
                intent.setClass(mContext, NetworkPublicLoginActivity.class);
                intent.putExtra("type", type_chinaunicom);
                startActivityForResult(intent, type_chinaunicom);
                //点击状态按钮设置用户名密码
//                Intent intent=new Intent();
//                intent.setClass(this, anct_cls);
//                if (anct_cls.equals(NetworkPublicLoginActivity.class))
//                    startActivityForResult(intent, NetworkPublicLoginActivity.RESULT_CODE);
//                else if (anct_cls.equals(GoNetChinaUnicomAcntActivity.class))
//                    startActivityForResult(intent, GoNetChinaUnicomAcntActivity.RESULT_CODE);
                break;
            case R.id.btn_quicklogin:  //一键登录
                try {
                    xiancheng_network_chinauicom_condition =mNetworkjudge.chinaunicom_judge();
                } catch (IOException e) {
                    message=new Message();
                    message.what=12;
                    mHandler.sendMessage(message);
                    return;
                }
                if (xiancheng_network_chinauicom_condition==1){
                    Toast.makeText(this, "您已登录，请不要重复登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                xiancheng_toastmessage="正在初始化";
                message=new Message();
                message.what=11;
                mHandler.sendMessage(message);


                mExecutorService.execute(xiancheng_autologin);
                break;
            case R.id.btn_quicklogout:

                    try {
                        isdustapp.getNetworklogin_ChinaUnicom().logout();
                    } catch (IOException e) {
                        message=new Message();
                        message.what=10;
                        mHandler.sendMessage(message);
                        return;
                    }
                    Toast.makeText(this, "下线成功", Toast.LENGTH_SHORT).show();
                    message=new Message();
                    message.what=0;
                    mHandler.sendMessage(message);
                break;




        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的

        switch (requestCode){
            case type_chinaunicom:
                switch (resultCode){
                    case RESULT_OK:
                        Bundle bundle = data.getExtras();
                        xiancheng_chinaunicom_user=bundle.getString("ChinaUnicom_user");
                        xiancheng_chinaunicom_password=bundle.getString("ChinaUnicom_password");


                        mExecutorService.execute(xiancheng_runnable_chinauicom_login);
                        customRuningDialog.show();    //打开等待框
                        customRuningDialog.setMessage("正在登录...");

                        break;
                    case RESULT_CANCELED:

                        break;
                }
                break;
        }


    }   //处理子页面返回的数据

}
