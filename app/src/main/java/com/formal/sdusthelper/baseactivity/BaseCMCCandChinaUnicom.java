package com.formal.sdusthelper.baseactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.formal.sdusthelper.GoNetCMCCAcntActivity;
import com.formal.sdusthelper.GoNetChinaUnicomAcntActivity;
import com.formal.sdusthelper.R;
import com.formal.sdusthelper.view.IsdustDialog;

import pw.isdust.isdust.function.Networkjudge;
import pw.isdust.isdust.function.baseclass.BaseNetworklogin;

/**
 * Created by Administrator on 2015/10/31.
 */
public class BaseCMCCandChinaUnicom extends BaseSubPageActivity {
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
            initTreadJudgeNet();
            threadJudgeNet.start();
        }   //如果没有空数据，就判断网络状态
    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_state:   //点击状态按钮设置用户名密码
                Intent intent=new Intent();
                intent.setClass(this, anct_cls);
                if (anct_cls.equals(GoNetCMCCAcntActivity.class))
                    startActivityForResult(intent, GoNetCMCCAcntActivity.RESULT_CODE);
                else if (anct_cls.equals(GoNetChinaUnicomAcntActivity.class))
                    startActivityForResult(intent, GoNetChinaUnicomAcntActivity.RESULT_CODE);
                break;
            case R.id.btn_quicklogin:  //一键登录
                initThreadLogin();  //初始化进程
                threadLogin.start();    //打开登录进程
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
        if (str_user1.isEmpty() || str_pwd1.isEmpty() || str_user2.isEmpty() || str_pwd2.isEmpty()) {
            Intent intent=new Intent();
            intent.setClass(BaseCMCCandChinaUnicom.this, anct_cls);
            startActivityForResult(intent, 1);  //获取返回值方式启动
            return true;
        }
        else
            return false;
    }//有数据为空，需要编辑

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的
        if (resultCode==GoNetCMCCAcntActivity.RESULT_CODE ||
                resultCode==GoNetChinaUnicomAcntActivity.RESULT_CODE) //cmcc&unicom
        {
            Bundle bundle=data.getExtras();
            str_user1 = bundle.getString("str_user1");
            str_pwd1 = bundle.getString("str_pwd1");
            str_user2 = bundle.getString("str_user2");
            str_pwd2 = bundle.getString("str_pwd2");
        }
    }   //处理子页面返回的数据

    private void initThreadLogin() {
        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在登录...");
        threadLogin = new Thread(new Runnable() {
            @Override
            public void run() {
                final String result;
                result = obj_gonet.login(str_user1,str_pwd1,str_user2,str_pwd2);
                BaseCMCCandChinaUnicom.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("登录成功"))
                            imgbtn_state.setBackgroundResource(R.drawable.online);
                        customRuningDialog.hide();
                        Toast.makeText(BaseCMCCandChinaUnicom.this, result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });     //初始化登录线程
    }   //快捷登录CMCC

    private void initTreadJudgeNet() {
        obj_netstate = new Networkjudge(this);
        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在检测网络状态...");
        threadJudgeNet = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isonline = obj_netstate.isOnline();
                if (isonline) {
                    BaseCMCCandChinaUnicom.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgbtn_state.setBackgroundResource(R.drawable.online);//设置状态按钮
                            customRuningDialog.hide();
                        }
                    });
                }   //已连接
                else {
                    BaseCMCCandChinaUnicom.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgbtn_state.setBackgroundResource(R.drawable.offline);//设置状态按钮
                            customRuningDialog.hide();
                        }
                    });
                }   //未连接
            }
        });
    }   //判断当前网络及界面切换
}
