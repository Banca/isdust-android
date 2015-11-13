package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageButton;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.isdust.www.view.IsdustDialog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Networkjudge;

/**
 * Created by Administrator on 2015/10/31.
 */
public class GoNetChinaUnicomActivity  extends BaseSubPageActivity_new {
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
                mImageButton_state.setBackgroundResource(R.drawable.cmcc_0);//设置状态按钮
                return;

            }
            if (msg.what==1){//已连接,登录1层
                mImageButton_state.setBackgroundResource(R.drawable.cmcc_2);//设置状态按钮
                return;
                }
           if (msg.what==2){//登录成功
               Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
               message.what=1;
               mHandler.sendMessage(message);

                }
            if (msg.what==3){//登录失败，密码错误

            }
            if (msg.what==4){//登录失败，未知错误

            }
            if (msg.what==10){//网络超时
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
                return;
            }
            if (msg.what == 11){//显示信息
                customRuningDialog.dismiss();
                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage(xiancheng_toastmessage);
                return;
            }
        }
    };

    Runnable xiancheng_autologin=new Runnable() {
        @Override
        public void run() {
            Message messgae=new Message();
            xiancheng_network_chinauicom_condition =mNetworkjudge.chinaunicom_judge();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的

        switch (requestCode){
            case type_chinaunicom:
                switch (resultCode){
                    case RESULT_OK:
                        Bundle bundle = data.getExtras();
                        xiancheng_chinaunicom_user=bundle.getString("network_ChinaUnicom_user");
                        xiancheng_chinaunicom_password=bundle.getString("network_ChinaUnicom_password");


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
