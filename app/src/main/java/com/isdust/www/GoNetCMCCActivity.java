package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.isdust.www.view.IsdustDialog;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Networkjudge;


/**
 * Created by Administrator on 2015/10/23.
 */
public class GoNetCMCCActivity extends BaseSubPageActivity_new {
    final int type_chengshiredian =1,type_cmcc=3;
    ImageButton mImageButton_state;
    IsdustDialog customRuningDialog;  //自定义运行中提示框
    Networkjudge mNetworkjudge;

    SharedPreferences mSharedPreferences_cmcc;



    //线程池构建
    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    String xiancheng_chengshiredian_user,xiancheng_chengshiredian_password;
    String xiancheng_cmcc_user,xiancheng_cmcc_password;
    String xiancheng_chengshiredian_status,xiancheng_cmcc_status;
    String xiancheng_toastmessage;
    int xiancheng_network_type, xiancheng_network_cmcc_condition;
    final android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Message message=new Message();
            if (msg.what == 0){//已连接,登录0层
                mImageButton_state.setBackgroundResource(R.drawable.cmcc_0);//设置状态按钮
                //customRuningDialog.hide();
                return;
            }
            if (msg.what == 1){//已连接,登录1层

                mImageButton_state.setBackgroundResource(R.drawable.cmcc_1);//设置状态按钮
                //Toast.makeText(BaseCMCCandChinaUnicom.this, "已连接CMCC，请点击一键登录", Toast.LENGTH_SHORT).show();
                return;
                //customRuningDialog.hide();
            }
            if (msg.what == 2){//已连接，登录2层
                mImageButton_state.setBackgroundResource(R.drawable.cmcc_2);//设置状态按钮
                return;
                // imgbtn_state.setBackgroundResource(R.drawable.online);
                //customRuningDialog.hide();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, "CMCC登录成功", Toast.LENGTH_SHORT).show();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();

            }
            if (msg.what == 3){//1层登录成功
                //customRuningDialog.hide();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "1层登录成功", Toast.LENGTH_SHORT).show();
                message.what=1;
                handler.sendMessage(message);
                mExecutorService.execute(xiancheng_autologin);
                return;


            }
            if (msg.what == 4){//2层登录成功
                Toast.makeText(mContext, "2层登录成功", Toast.LENGTH_SHORT).show();
                //customRuningDialog.hide();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                message.what=2;
                handler.sendMessage(message);
                return;
            }
            if (msg.what == 5){//1层登录失败
                //customRuningDialog.hide();
                customRuningDialog.dismiss();
                customRuningDialog.dismiss();
                Toast.makeText(mContext, xiancheng_chengshiredian_status, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(mContext, NetworkPublicLoginActivity.class);
                intent.putExtra("type", type_chengshiredian);
                startActivityForResult(intent, type_chengshiredian);
                return;

            }
            if (msg.what == 6){//2层登录失败（密码错误）
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "2层登录密码错误", Toast.LENGTH_SHORT).show();

                mSharedPreferences_cmcc.edit().putString("network_cmcc_cmcc_password", "");
                mSharedPreferences_cmcc.edit().commit();
                Intent intent=new Intent();
                intent.setClass(mContext, NetworkCMCCLoginActivity.class);
                startActivityForResult(intent, type_cmcc);
                return;

                //customRuningDialog.hide();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();

            }
            if (msg.what == 7){//2层登录失败（未知）
                customRuningDialog.dismiss();
                mSharedPreferences_cmcc.edit().putString("network_cmcc_cmcc_password", "");
                mSharedPreferences_cmcc.edit().commit();
                Toast.makeText(mContext, xiancheng_cmcc_status, Toast.LENGTH_SHORT).show();
                return;

                //customRuningDialog.hide();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();

            }
            if (msg.what == 8){//cmcc_init

                if (xiancheng_cmcc_user.equals("")||xiancheng_cmcc_password.equals("")){
                    Intent intent=new Intent();
                    intent.setClass(mContext, NetworkCMCCLoginActivity.class);
                    startActivityForResult(intent, type_cmcc);
                    return;

                }
                mExecutorService.execute(xiancheng_runnable_cmcc_login);


                //customRuningDialog.hide();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();

            }
            if (msg.what == 9){//动态验证码过期
                mSharedPreferences_cmcc.edit().putString("network_cmcc_cmcc_password", "");
                mSharedPreferences_cmcc.edit().commit();
                Toast.makeText(mContext, xiancheng_cmcc_status, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent();
                intent.setClass(mContext, NetworkCMCCLoginActivity.class);
                startActivityForResult(intent, type_cmcc);
                return;
                //mImageButton_state.setBackgroundResource(R.drawable.online);//设置状态按钮
                //Toast.makeText(BaseCMCCandChinaUnicom.this, "已连接CMCC，请点击一键登录", Toast.LENGTH_SHORT).show();

                //customRuningDialog.hide();
            }

            if (msg.what == 10){//网络超时
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
            xiancheng_network_cmcc_condition =mNetworkjudge.cmcc_judge();

            if (xiancheng_network_cmcc_condition==2){//登录了2层
                Message mMessage=new Message();
                mMessage.what=2;
                handler.sendMessage(mMessage);
                return;
            }
            if (xiancheng_network_cmcc_condition==1){//登录1层
                if (xiancheng_cmcc_user.equals("")||xiancheng_cmcc_password.equals("")){
                    Intent intent=new Intent();
                    intent.setClass(mContext, NetworkCMCCLoginActivity.class);
                    startActivityForResult(intent, type_chengshiredian);
                    return;
                }
                mExecutorService.execute(xiancheng_runnable_cmcc_login);
//                customRuningDialog.show();    //打开等待框
                xiancheng_toastmessage="正在登录第二层...";
                Message mMessage=new Message();
                mMessage.what=11;
                handler.sendMessage(mMessage);
                return;

            }
            if (xiancheng_network_cmcc_condition==0){//登录0层
                if (xiancheng_chengshiredian_user.equals("")||xiancheng_chengshiredian_password.equals("")){
                    Intent intent=new Intent();
                    intent.setClass(mContext, NetworkPublicLoginActivity.class);
                    intent.putExtra("type", type_chengshiredian);
                    startActivityForResult(intent, type_chengshiredian);
                    return;
                }else {
                    mExecutorService.execute(xiancheng_runnable_chengshiredian_login);
//                    customRuningDialog.show();    //打开等待框
                       xiancheng_toastmessage="正在登录第一层...";
                    Message mMessage=new Message();
                    mMessage.what=11;
                    handler.sendMessage(mMessage);
                    return;
                }


            }

        }
    };
    Runnable xiancheng_runnable_chengshiredian_login =new Runnable() {//1层登录
        @Override
        public void run() {
            Message message=new Message();
            try {
                xiancheng_chengshiredian_status=isdustapp.getNetworklogin_CMCC().login(xiancheng_chengshiredian_user, xiancheng_chengshiredian_password);
            } catch (Exception e) {
                message.what=10;
                handler.sendMessage(message);
                return;
            }
            if (xiancheng_chengshiredian_status.equals("登录成功")){
                message.what=3;//1层登录成功
                handler.sendMessage(message);
                return;
            }
            message.what=5;//1层登录失败
            handler.sendMessage(message);
            return;
        }
    };

    Runnable xiancheng_runnable_cmcc_login =new Runnable() {//2层登录
        @Override
        public void run() {

            Message message=new Message();
            try {
                isdustapp.getNetworklogin_CMCC().cmcc_init();
                xiancheng_cmcc_status=isdustapp.getNetworklogin_CMCC().cmcc_login(xiancheng_cmcc_user, xiancheng_cmcc_password);
            } catch (IOException e) {
                message.what=10;
                handler.sendMessage(message);
                return;
            }
            if (xiancheng_cmcc_status.equals("登录成功")){
                message.what=4;//2层登录成功
                handler.sendMessage(message);
                return;
            }
            if (xiancheng_cmcc_status.equals("CMCC用户名或密码错误")){
                message.what=6; //2层登录失败（密码错误）
                handler.sendMessage(message);
                return;

            }
            if (xiancheng_cmcc_status.contains("动态密码有效期已过期")){

                message.what=9; //2层登录失败（密码错误）
                handler.sendMessage(message);
                return;
            }
            if (mNetworkjudge.cmcc_judge()==2){
                message.what=4;//2层登录成功
                handler.sendMessage(message);
                return;
            }

            message.what=7;//2层登录失败
            handler.sendMessage(message);
            return;
        }
    };






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.gonet_cmcc, "CMCC上网登录");  //初始化祖基类
        mNetworkjudge=new Networkjudge(mContext);
        mImageButton_state = (ImageButton) findViewById(R.id.btn_state); //连接状态按钮
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框
        isconnect();//没连接CMCC自动退出
        mSharedPreferences_cmcc=getSharedPreferences("NetworkLogin", Activity.MODE_PRIVATE);

        xiancheng_chengshiredian_user=mSharedPreferences_cmcc.getString("network_cmcc_chengshiredian_user", "");
        xiancheng_chengshiredian_password=mSharedPreferences_cmcc.getString("network_cmcc_chengshiredian_password", "");
        xiancheng_cmcc_user=mSharedPreferences_cmcc.getString("network_cmcc_cmcc_user","");
        xiancheng_cmcc_password=mSharedPreferences_cmcc.getString("network_cmcc_cmcc_password", "");


        xiancheng_toastmessage="正在初始化";
        Message message=new Message();
        message.what=11;
        handler.sendMessage(message);

        //autologin();
        mExecutorService.execute(xiancheng_autologin);






//        sharedPreferences = mContext.getSharedPreferences("CMCCData", Activity.MODE_PRIVATE);    //读取数据的对象
//        anct_cls = GoNetCMCCAcntActivity.class; // cmcc 的账户页面类
//        BaseCMCCandChinaUnicomInit();   //初始化页面及数据
//
//        obj_gonet = new Networklogin_CMCC();    //实例化操作对象
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的

        switch (requestCode){
            case type_chengshiredian:
                switch (resultCode){
                    case RESULT_OK:
                        Bundle bundle = data.getExtras();
                        xiancheng_chengshiredian_user=bundle.getString("cmcc_chengshiredian_user");
                        xiancheng_chengshiredian_password=bundle.getString("cmcc_chengshiredian_password");


                        mExecutorService.execute(xiancheng_runnable_chengshiredian_login);
                        customRuningDialog.show();    //打开等待框
                        customRuningDialog.setMessage("正在登录第一层...");

                        break;
                    case RESULT_CANCELED:

                        break;
                }
                break;
            case type_cmcc:
                switch (resultCode){
                    case RESULT_OK:
                        Bundle bundle = data.getExtras();
                        xiancheng_cmcc_user=bundle.getString("cmcc_cmcc_user");
                        xiancheng_cmcc_password=bundle.getString("cmcc_cmcc_password");
                        mExecutorService.execute(xiancheng_runnable_cmcc_login);
                        customRuningDialog.show();    //打开等待框
                        customRuningDialog.setMessage("正在登录第二层...");
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }


    }   //处理子页面返回的数据
    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_state:
                Message message;

                xiancheng_network_cmcc_condition =mNetworkjudge.cmcc_judge();

                if (xiancheng_network_cmcc_condition==2){
                    isdustapp.getNetworklogin_CMCC().logout_cmcc();
                    isdustapp.getNetworklogin_CMCC().logout_chengshiredian();


                }
                if (xiancheng_network_cmcc_condition== 1){
                    isdustapp.getNetworklogin_CMCC().logout_chengshiredian();

                }
                message=new Message();
                message.what=0;
                handler.sendMessage(message);
                isdustapp.getNetworklogin_CMCC().logout_cmcc();
                mSharedPreferences_cmcc.edit().putString("network_cmcc_cmcc_password", "");
                mSharedPreferences_cmcc.edit().putString("network_cmcc_chengshiredian_password", "");
                mSharedPreferences_cmcc.edit().commit();
                Intent intent=new Intent();
                intent.setClass(mContext, NetworkCMCCLoginActivity.class);
                startActivityForResult(intent, type_chengshiredian);
              //点击状态按钮设置用户名密码
//                Intent intent=new Intent();
//                intent.setClass(this, anct_cls);
//                if (anct_cls.equals(NetworkPublicLoginActivity.class))
//                    startActivityForResult(intent, NetworkPublicLoginActivity.RESULT_CODE);
//                else if (anct_cls.equals(GoNetChinaUnicomAcntActivity.class))
//                    startActivityForResult(intent, GoNetChinaUnicomAcntActivity.RESULT_CODE);
                break;
            case R.id.btn_quicklogin:  //一键登录
                xiancheng_network_cmcc_condition =mNetworkjudge.cmcc_judge();
                if (xiancheng_network_cmcc_condition==2){
                    Toast.makeText(this, "您已登录，请不要重复登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                xiancheng_toastmessage="正在初始化";
                message=new Message();
                message.what=11;
                handler.sendMessage(message);


                mExecutorService.execute(xiancheng_autologin);
                break;
            case R.id.btn_quicklogout:
                if (isdustapp.getNetworklogin_CMCC().logout_cmcc()){
                    isdustapp.getNetworklogin_CMCC().logout_chengshiredian();
                    Toast.makeText(this, "下线成功", Toast.LENGTH_SHORT).show();
                    message=new Message();
                    message.what=0;
                    handler.sendMessage(message);
                    return;}
                else{
                    Toast.makeText(this, "下线失败", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.btn_changepwd:
                if (isdustapp.getNetworklogin_CMCC().changepwd(""))
                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "密码修改失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                if (isdustapp.getNetworklogin_CMCC().query())
                    Toast.makeText(this, "请注意查收短信", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void isconnect(){
        xiancheng_network_type =mNetworkjudge.judgetype();
        if (xiancheng_network_type !=1){
            Toast.makeText(mContext,"请连接CMCC以后再使用本功能",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
