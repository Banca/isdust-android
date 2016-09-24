package com.isdust.www;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.isdust.www.view.IsdustDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Network_Kuaitong;

/**
 * Created by Leng Hanchao on 2015/10/31.
 * Midified and Refactored by Wang Ziqiang.
 * isdust
 * Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

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
public class KuaiTongActivity extends BaseSubPageActivity_new {
    private SharedPreferences preferences_data_kuaitong,preferences_data_schoolcard;
    private SharedPreferences.Editor preferences_editor_kuaitong,preferences_editor_schoolcard;
    final int request_kuaitong=1,request_xiaoyuanka=2;
    Context mContext;
    private Network_Kuaitong obj_kuaitong;

    private TextView textuser,textuserstate,textpackage,
            textflow,textbala;
    private ImageView imgstate;
    private String smartcard_result = "";

    protected IsdustDialog customRuningDialog;  //自定义运行中提示框

    private String paynum; //充值金额
    private String xiancheng_gaimima_oldpwd,xiancheng_gaimima_newpwd1,xiancheng_gaimima_newpwd2,xiancheng_gaimima_result;  //改密


    //线程池
    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    boolean xiancheng_islogin_kuaitong=false,xiancheng_islogin_smartcard=false;
    String xiancheng_kuaitong_user, xiancheng_kuaitong_pwd;  //快通账号密码
    String xiancheng_smartcard_user, xiancheng_smartcard_pwd;  //SmartCard账号密码

    String xiancheng_login_status;
    String xiancheng_smartcard_login_status;

    String xiancheng_carddata[];
    int xiancheng_network_change;//1为开网，0为关网；
    int xiancheng_packge_id;

    android.os.Handler mHandler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what==-1){//快通初始化成功

                customRuningDialog.dismiss();
                if (xiancheng_kuaitong_user.equals("")||xiancheng_kuaitong_pwd.equals("")){
                    startActivity_kuaitong_login();
                    return;
                }
                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage("正在登录...");
                mExecutorService.execute(xiancheng_login);
                return;
            }
            if (msg.what==0){//登录失败（微信接口）
                xiancheng_islogin_kuaitong=false;
                preferences_editor_kuaitong.putString("password", "");
                preferences_editor_kuaitong.commit();
                Toast.makeText(mContext, xiancheng_login_status, Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                return;
            }
            if (msg.what==1){//登录成功（微信接口）
                xiancheng_islogin_kuaitong=true;
                textuser.setText("用户："+ xiancheng_kuaitong_user);
                customRuningDialog.dismiss();
                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage("正在获取数据...");
                mExecutorService.execute(xiancheng_getdata);


            }
            if (msg.what==2){//信息获取成功
                textuserstate.setText("当前状态:" + xiancheng_carddata[11]);//用户状态
                textpackage.setText("当前套餐:" + xiancheng_carddata[2]);//当前套餐
                if (xiancheng_carddata[0].equals("正常"))
                    imgstate.setBackgroundResource(R.drawable.kt_state_normal);
                else
                    imgstate.setBackgroundResource(R.drawable.kt_state_err);
                textflow.setText("   剩余流量：" + xiancheng_carddata[5]);
                textbala.setText("  下月余额：" + xiancheng_carddata[13]);
                customRuningDialog.dismiss();


                //smartcard登录
                if (xiancheng_smartcard_user.equals("")||xiancheng_smartcard_pwd.equals("")){//空指针崩溃
                    startActivity_smartcard_login();
                    return;

                }else{
//                    mExecutorService.execute(xiancheng_smartcard_login);
//                    customRuningDialog.show();    //打开等待框
//                    customRuningDialog.setMessage("正在登录校园卡...");
                }

            }
            if (msg.what==3){//登录成功（smartcard）
                xiancheng_islogin_smartcard=true;

                customRuningDialog.dismiss();
                Toast.makeText(KuaiTongActivity.this, "登录成功", Toast.LENGTH_SHORT).show();




            }
            if (msg.what==4){//登录失败（smartcard）
                xiancheng_islogin_smartcard=false;
                preferences_editor_schoolcard.putString("password", "");
                preferences_editor_schoolcard.commit();

                Toast.makeText(mContext, xiancheng_smartcard_login_status, Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                startActivity_smartcard_login();

//                customRuningDialog.show();    //打开等待框
//                customRuningDialog.setMessage("正在获取数据...");
//                mExecutorService.execute(xiancheng_getdata);


            }
            if (msg.what==5){//充值成功

                customRuningDialog.dismiss();
                Toast.makeText(KuaiTongActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage("正在登录...");
                mExecutorService.execute(xiancheng_getdata);

            }
            if (msg.what==6){//改密成功
                Toast.makeText(KuaiTongActivity.this, xiancheng_gaimima_result, Toast.LENGTH_SHORT).show();
                customRuningDialog.hide();
                mExecutorService.execute(xiancheng_login);

            }
            if (msg.what==7){//改密失败
                Toast.makeText(KuaiTongActivity.this, xiancheng_gaimima_result, Toast.LENGTH_SHORT).show();
                customRuningDialog.hide();
                dealChangePwd();



            }
            if (msg.what==8){//网络状态修改成功
                Toast.makeText(mContext, "网络状态变更成功", Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                mExecutorService.execute(xiancheng_getdata);


            }
            if (msg.what==9){//套餐修改成功
                Toast.makeText(mContext, "套餐变更成功", Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();
                mExecutorService.execute(xiancheng_getdata);


            }
            if (msg.what==10){//网络超时
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
                customRuningDialog.dismiss();


            }
            if (msg.what == 11){//网络超时
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "在线参数获取失败，请保证网络正常的情况下重启app", Toast.LENGTH_SHORT).show();
            }
        }
    };
    Runnable xiancheng_login=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
                xiancheng_login_status = obj_kuaitong.loginKuaitong(xiancheng_kuaitong_user, xiancheng_kuaitong_pwd);
            } catch (IOException e) {
                e.printStackTrace();
                mMessage.what=10;
                mHandler.sendMessage(mMessage);//网络超时
                return;
            }
            if (xiancheng_login_status.equals("登录成功")){

                mMessage.what=1;
                mHandler.sendMessage(mMessage);//登录成功
                return;
            }
            mMessage.what=0;
            mHandler.sendMessage(mMessage);
            return;

        }
    };
    Runnable xiancheng_getdata=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
                xiancheng_carddata = obj_kuaitong.getKuaitongInfo();
            } catch (IOException e) {
                mMessage.what=10;
                mHandler.sendMessage(mMessage);//网络超时
            }

            mMessage.what=2;
            mHandler.sendMessage(mMessage);//信息获取成功
        }
    };
    Runnable xiancheng_init=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
                obj_kuaitong = new Network_Kuaitong(mContext);
            } catch (Exception e) {
                mMessage=new Message();
                mMessage.what = 10;
                mHandler.sendMessage(mMessage);;
                return;
            }
            mMessage.what=-1;
            mHandler.sendMessage(mMessage);

            return;

        }
    };
    Runnable xiancheng_smartcard_login=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
                xiancheng_smartcard_login_status= obj_kuaitong.loginSmartCard(xiancheng_smartcard_user, xiancheng_smartcard_pwd);
            } catch (IOException e) {
                mMessage.what=10;
                mHandler.sendMessage(mMessage);//网络超时
                return;
            }
            if (xiancheng_smartcard_login_status.equals("登录成功")){
                mMessage.what=3;
                mHandler.sendMessage(mMessage);
                return;
            }
            mMessage.what=4;
            mHandler.sendMessage(mMessage);
            return;

        }
    };
    Runnable xiancheng_pay=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
                obj_kuaitong.pay(paynum);
            } catch (IOException e) {
                mMessage.what=10;
                mHandler.sendMessage(mMessage);
                return;
            }
            mMessage.what=5;
            mHandler.sendMessage(mMessage);
            return;
        }
    };
    Runnable xiancheng_gaimima=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
                xiancheng_gaimima_result= obj_kuaitong.gaimima(xiancheng_gaimima_oldpwd,xiancheng_gaimima_newpwd1,xiancheng_gaimima_newpwd2);
            } catch (IOException e) {
                mMessage.what=10;
                mHandler.sendMessage(mMessage);
                return;
            }
            if (xiancheng_gaimima_result.equals("修改密码成功")){
                mMessage.what=6;//修改成功
                mHandler.sendMessage(mMessage);
                return;
            }
            mMessage.what=7;//失败
            mHandler.sendMessage(mMessage);
            return;

        }
    };

    Runnable xiancheng_gaizhuangtai=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
                if (xiancheng_network_change==0){
                    obj_kuaitong.tingwang();
                }else {
                    obj_kuaitong.kaiwang();
                }


            }catch (Exception e){
                mMessage.what=10;
                mHandler.sendMessage(mMessage);
                return;

            }

            mMessage.what=8;//网络状态修改成功
            mHandler.sendMessage(mMessage);
            return;
        }


    };
    Runnable xiancheng_gaitaocan=new Runnable() {
        @Override
        public void run() {
            Message mMessage=new Message();
            try {
            obj_kuaitong.gaitaocan(xiancheng_packge_id+"");


            }catch (Exception e){
                mMessage.what=10;
                mHandler.sendMessage(mMessage);
                return;

            }

            mMessage.what=9;//套餐修改成功
            mHandler.sendMessage(mMessage);
            return;
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(this, "network_kuaitong");
        mContext=this;
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_kuaitong, "快通有线");
        isdustapp.kuaitong_init();
        MobclickAgent.onEvent(this, "network_kuaitong_main");

        //实例化SharedPreferences对象
        preferences_data_kuaitong = mContext.getSharedPreferences("KuaiTongData", Activity.MODE_PRIVATE);
        preferences_data_schoolcard = getSharedPreferences("CardData", Activity.MODE_PRIVATE);

        //实例化SharedPreferences.Editor对象
        preferences_editor_kuaitong = preferences_data_kuaitong.edit();
        preferences_editor_schoolcard = preferences_data_schoolcard.edit();


        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框
        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        // 使用getString方法获得value，注意第2个参数是value的默认值
        xiancheng_kuaitong_user =preferences_data_kuaitong.getString("username", "");
        xiancheng_kuaitong_pwd = preferences_data_kuaitong.getString("password", "");

        xiancheng_smartcard_user =preferences_data_schoolcard.getString("username", "");
        xiancheng_smartcard_pwd =preferences_data_schoolcard.getString("password", "");



        findView();

        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在初始化快通模块...");
        mExecutorService.execute(xiancheng_init);

//        getData();
    }

    public void onFormKuaiTongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_kuaitong_infomation:  //查看信息
                if (xiancheng_islogin_kuaitong){
                    Intent intent = new Intent(KuaiTongActivity.this,KuaiTongInfoActivity.class) ;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("KuaiTongData", xiancheng_carddata) ;
                    intent.putExtras(bundle) ;
                    startActivity(intent);
                }else{
                    startActivity_kuaitong_login();
                }
                break;
            case R.id.btn_kuaitong_su:  //切换账户
                startActivity_kuaitong_login();
                break;
            case R.id.btn_kuaitong_pay: //充值
                if (xiancheng_islogin_smartcard){
                    dealPay();
                }else {
                    startActivity_smartcard_login();

                }
                break;
            case R.id.btn_kuaitong_changepwd: //改密
                if (xiancheng_islogin_smartcard) {
                    dealChangePwd();
                }else {
                    startActivity_smartcard_login();

                }
                break;
            case R.id.btn_kuaitong_switch: //开停机
                if (xiancheng_islogin_smartcard) {
                    dealSwitch();
                }else {
                    startActivity_smartcard_login();
                }
                break;
            case R.id.btn_kuaitong_package://换套餐
                if (xiancheng_islogin_smartcard) {
                    changepackge();
                }else {
                    startActivity_smartcard_login();
                }
                break;
        }

    }

    private void dealPay() {
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView userTextView = new TextView(this);
        userTextView.setText("充值账户:"+ xiancheng_smartcard_user);
        userTextView.setTextSize(18f);
        TextView numTextView = new TextView(this);
        numTextView.setText("请输入充值金额（元）:");
        numTextView.setTextSize(18f);

        final EditText numEditText = new EditText(this);    //充值输入框
//        if (android.os.Build.VERSION.SDK_INT > 15)
//            numEditText.setInputType(InputType.TYPE_CLASS_NUMBER
//                    | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
//        else
        numEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(userTextView);
        layout.addView(numTextView);
        layout.addView(numEditText);

        new AlertDialog.Builder(this).setTitle("充值")
                .setView(layout)
                .setIcon(R.mipmap.isdust)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        paynum = numEditText.getText().toString();
                        if (paynum.equals("")) {
                            Toast.makeText(mContext, "请输入充值金额", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Float.parseFloat(paynum) >= 50) {
                            Toast.makeText(mContext, "充值金额不能大于50", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        customRuningDialog.show();    //打开等待框
                        customRuningDialog.setMessage("正在支付...");


                        mExecutorService.execute(xiancheng_pay);
                    }
                }).show();








    }   //处理充值任务

    private void changepackge(){
        final String taocan []={"5元包5G","15元包22G","30元包50G"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.isdust);
        builder.setTitle("请选择套餐");
        builder.setSingleChoiceItems(taocan, 0, null);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xiancheng_packge_id = ((AlertDialog) dialog).getListView().getCheckedItemPosition() + 5;
                mExecutorService.execute(xiancheng_gaitaocan);
//                Toast.makeText(mContext, ""+ xiancheng_packge_id, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


    private void dealSwitch() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView stateTextView = new TextView(this);
        stateTextView.setText("当前状态:" + xiancheng_carddata[0]);
        stateTextView.setTextSize(18f);
        Switch stateSwitch = new Switch(this);
        stateSwitch.setChecked(xiancheng_carddata[0].equals("正常"));
        if (xiancheng_carddata[0].equals("正常")){
            xiancheng_network_change=1;
        }else {
            xiancheng_network_change=0;
        }
        stateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {//Switch之前状态为关
                    xiancheng_network_change=1;
                } else {           //Switch之前状态为开
                    xiancheng_network_change=0;
                }
            }
        });
        layout.addView(stateTextView);
        layout.addView(stateSwitch);

        new AlertDialog.Builder(this).setTitle("开停机")
                .setView(layout)
                .setIcon(R.mipmap.isdust)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage("正在变更...");
                mExecutorService.execute(xiancheng_gaizhuangtai);

            }

        }).show();
    }   //处理充值任务

    private void dealChangePwd() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView text1 = new TextView(this);
        TextView text2 = new TextView(this);
        TextView text3 = new TextView(this);
        text1.setText("原密码：");
        text2.setText("新密码：");
        text3.setText("确认密码：");
        text1.setTextSize(18f);
        text2.setTextSize(18f);
        text3.setTextSize(18f);

        final EditText edit_orgpwd = new EditText(this);    //原密码输入框
        final EditText edit_newpwd1 = new EditText(this);    //新密码输入框
        final EditText edit_newpwd2 = new EditText(this);    //确认密码输入框
        edit_orgpwd.setInputType(InputType.TYPE_CLASS_TEXT);
        edit_newpwd1.setInputType(InputType.TYPE_CLASS_TEXT);
        edit_newpwd2.setInputType(InputType.TYPE_CLASS_TEXT);

            edit_orgpwd.setTransformationMethod( PasswordTransformationMethod.getInstance());
            edit_newpwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edit_newpwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());





        layout.addView(text1);
        layout.addView(edit_orgpwd);
        layout.addView(text2);
        layout.addView(edit_newpwd1);
        layout.addView(text3);
        layout.addView(edit_newpwd2);

        new AlertDialog.Builder(this).setTitle("密码修改")
                .setView(layout)
                .setIcon(R.mipmap.isdust)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        xiancheng_gaimima_oldpwd = edit_orgpwd.getText().toString();
                        xiancheng_gaimima_newpwd1 = edit_newpwd1.getText().toString();
                        xiancheng_gaimima_newpwd2 = edit_newpwd2.getText().toString();
                        if (xiancheng_gaimima_oldpwd==""||xiancheng_gaimima_newpwd1==""||xiancheng_gaimima_newpwd2=="" ){
                            Toast.makeText(mContext,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        customRuningDialog.show();    //打开等待框
                        customRuningDialog.setMessage("正在修改...");
                        mExecutorService.execute(xiancheng_gaimima);
                    }
                }).show();
    }   //处理改密任务

    private void startActivity_kuaitong_login() {
        Intent intent = new Intent();
        intent.setClass(this, KuaiTongAcntActivity.class);
        startActivityForResult(intent, request_kuaitong);
        System.out.println("startActivity_kuaitong_login");
    }   //以获取结果的方式打开账户页面

    private void startActivity_smartcard_login() {
        Intent intent = new Intent();
        intent.setClass(this, Card_login.class);
        startActivityForResult(intent, request_xiaoyuanka);
        System.out.println("startActivity_smartcard_login");

    }   //以获取结果的方式打开账户页面

//    private void startCardAcntActivity() {
//        Intent intent = new Intent();
//        intent.setClass(this, KuaiTongAcntActivity.class);
//        startActivityForResult(intent, KuaiTongAcntActivity.RESULT_CODE);
//    }   //以获取结果的方式打开校园卡账户页面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的

        switch (requestCode){
            case request_kuaitong:
                switch (resultCode){
                    case RESULT_OK:
                        System.out.println("request_kuaitong");

                        Bundle bundle=data.getExtras();
                        xiancheng_kuaitong_user = bundle.getString("str_user");
                        xiancheng_kuaitong_pwd = bundle.getString("str_pwd");
                        mExecutorService.execute(xiancheng_login);
                        break;
                }
                break;
            case request_xiaoyuanka:
                switch (resultCode){
                    case RESULT_OK:
                        System.out.println("request_xiaoyuanka");
                        Bundle bundle=data.getExtras();
                        xiancheng_smartcard_user = bundle.getString("username");
                        xiancheng_smartcard_pwd = bundle.getString("password");
                        customRuningDialog.show();    //打开等待框
                        customRuningDialog.setMessage("正在登录校园卡...");
                        mExecutorService.execute(xiancheng_smartcard_login);
                        break;
                }
                break;
        }

    }   //处理子页面返回的数据

    private void findView() {
        textuser = (TextView) findViewById(R.id.text_kuaitong_user);
        textuserstate = (TextView) findViewById(R.id.text_kuaitong_userstate);
        textpackage = (TextView) findViewById(R.id.text_kuaitong_package);
        textflow = (TextView) findViewById(R.id.text_kuaitong_flowmeter);
        textbala = (TextView) findViewById(R.id.text_kuaitong_balance);
        imgstate = (ImageView) findViewById(R.id.image_kuaitong_state);
    }


}
