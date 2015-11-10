package com.isdust.www.baseactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.isdust.www.NetworkPublicLoginActivity;
import com.isdust.www.GoNetChinaUnicomAcntActivity;
import com.isdust.www.R;
import com.isdust.www.view.IsdustDialog;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Networkjudge;
import pw.isdust.isdust.function.baseclass.BaseNetworklogin;

/**
 * Created by Administrator on 2015/10/31.
 */
public class BaseCMCCandChinaUnicom extends BaseSubPageActivity_new {
    protected BaseNetworklogin obj_gonet;   //上网登录实例对象
    protected Class<?> anct_cls;    //账号页面类
    //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
    protected SharedPreferences sharedPreferences;
    private Thread threadLogin; //用于一键登录的线程
    private Thread threadJudgeNet; //用于判断网络的线程
    private Networkjudge obj_netstate;  //用于网络判断的对象
    protected IsdustDialog customRuningDialog;  //自定义运行中提示框
    protected ImageButton imgbtn_state;
    private String str_user1,str_user2,
            str_pwd1,str_pwd2;

    //构建线程池
    String xiancheng_result;
    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    final android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){//未连接
                imgbtn_state.setBackgroundResource(R.drawable.offline);//设置状态按钮
                customRuningDialog.hide();
            }
            if (msg.what == 1){//已连接,未登录
                imgbtn_state.setBackgroundResource(R.drawable.online);//设置状态按钮
                Toast.makeText(BaseCMCCandChinaUnicom.this, "已连接CMCC，请点击一键登录", Toast.LENGTH_SHORT).show();

                customRuningDialog.hide();
            }
            if (msg.what == 2){//登录成功
                imgbtn_state.setBackgroundResource(R.drawable.online);
                customRuningDialog.hide();
                Toast.makeText(BaseCMCCandChinaUnicom.this, "CMCC登录成功", Toast.LENGTH_SHORT).show();
                //Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();

            }
            if (msg.what == 3){//登录失败
                customRuningDialog.hide();
                Toast.makeText(BaseCMCCandChinaUnicom.this, xiancheng_result, Toast.LENGTH_SHORT).show();

            }

            if (msg.what == 10){//网络超时
                Toast.makeText(BaseCMCCandChinaUnicom.this, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
            }


        }
    };
    Runnable xiancheng_login=new Runnable() {
        @Override
        public void run() {

            try {
                xiancheng_result = obj_gonet.login(str_user1,str_pwd1,str_user2,str_pwd2);
            } catch (IOException e) {//登录超时
                Message message = new Message();
                message.what = 10;
                handler.sendMessage(message);
                return;
            }
            if(xiancheng_result.equals("登录成功")){//登录成功
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            return;
        }else {//登录失败
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        }


                                                      };
    Runnable xiancheng_JudgeNet= new Runnable() {
        @Override
        public void run() {
            //boolean isonline = obj_netstate.isOnline();
            int isonline=obj_netstate.judgetype();
            if (isonline==1) {
                if (obj_netstate.cmcc_judge()==2){//登录成功
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                    return;
                }

                Message message = new Message();
                message.what = 1;//已连接,未登录
                handler.sendMessage(message);



            }
            else {

                Message message = new Message();
                message.what = 0;//未连接
                handler.sendMessage(message);
            }
        }
    };

    protected void BaseCMCCandChinaUnicomInit() {
        imgbtn_state = (ImageButton) findViewById(R.id.btn_state); //连接状态按钮
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框

        // 使用getString方法获得value，注意第2个参数是value的默认值
        str_user1 =sharedPreferences.getString("username_first", "");
        str_pwd1 =sharedPreferences.getString("password_first", "");

        str_user2 =sharedPreferences.getString("username_sec", "");
        str_pwd2 =sharedPreferences.getString("password_sec", "");
        if (!judgeEmptyData()) {
            obj_netstate = new Networkjudge(this);
            customRuningDialog.show();    //打开等待框
            customRuningDialog.setMessage("正在检测网络状态...");
            mExecutorService.execute(xiancheng_JudgeNet);
        }   //如果没有空数据，就判断网络状态
    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_state:   //点击状态按钮设置用户名密码
                Intent intent=new Intent();
                intent.setClass(this, anct_cls);
                if (anct_cls.equals(NetworkPublicLoginActivity.class))
                    startActivityForResult(intent, NetworkPublicLoginActivity.RESULT_CODE);
                else if (anct_cls.equals(GoNetChinaUnicomAcntActivity.class))
                    startActivityForResult(intent, GoNetChinaUnicomAcntActivity.RESULT_CODE);
                break;
            case R.id.btn_quicklogin:  //一键登录

                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage("正在登录...");
                mExecutorService.execute(xiancheng_login);  //初始化进程
                //threadLogin.start();    //打开登录进程
                break;
            case R.id.btn_quicklogout:
                if (obj_gonet.logout())
                    Toast.makeText(this, "下线成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "下线失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_changepwd:
                if (obj_gonet.changepwd(""))
                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "密码修改失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                if (obj_gonet.query())
                    Toast.makeText(this, "请注意查收短信", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean judgeEmptyData() {
        if (anct_cls.equals(NetworkPublicLoginActivity.class)) {
            if (str_user1.isEmpty() || str_pwd1.isEmpty() || str_user2.isEmpty() || str_pwd2.isEmpty()) {
                Intent intent=new Intent();
                intent.setClass(BaseCMCCandChinaUnicom.this, anct_cls);
                startActivityForResult(intent, 1);  //获取返回值方式启动
                return true;
            }
            else
                return false;
        }   //当前页面为CMCC
        else {
            if (str_user1.isEmpty() || str_pwd1.isEmpty()) {
                Intent intent=new Intent();
                intent.setClass(BaseCMCCandChinaUnicom.this, anct_cls);
                startActivityForResult(intent, 1);  //获取返回值方式启动
                return true;
            }
            else
                return false;
        }   //当前页面为ChinaUnicom

    }//有数据为空，需要编辑

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的
        if (resultCode== NetworkPublicLoginActivity.RESULT_CODE ||
                resultCode==GoNetChinaUnicomAcntActivity.RESULT_CODE) //cmcc&unicom
        {
            Bundle bundle=data.getExtras();
            str_user1 = bundle.getString("str_user1");
            str_pwd1 = bundle.getString("str_pwd1");
            str_user2 = bundle.getString("str_user2");
            str_pwd2 = bundle.getString("str_pwd2");
        }
    }   //处理子页面返回的数据




}
