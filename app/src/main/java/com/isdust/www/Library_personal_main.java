package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.isdust.www.view.IsdustDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Library;

/**
 * Created by Wang Ziqiang on 15/11/4.
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
public class Library_personal_main extends BaseSubPageActivity_new {
    ListView mListView;
    Button mButton_renew;
    Button mButton_logout;
    TextView mTextView_usercard;
    TextView mTextView_username;
    TextView mTextView_userstate;
    SharedPreferences preferences_data;
    SharedPreferences.Editor preferences_editor;


    IsdustDialog customRuningDialog;
    Library mlibrary;


    //线程池
    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    String [] [] mxiancheng_borrowdetail;
    String mxiancheng_username;
    String mxiancheng_password;
    String mxiancheng_renew_detail;
    private boolean xiancheng_bollean;
    int mxianchengchi_login_status=0;//0为已登录，1为登录
    //列表下拉框
    private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();	//列表框的数据
    SimpleAdapter adapter;	//列表的适配器

    Runnable mRunnable_login=new Runnable() {
        @Override
        public void run() {
           try {
               mlibrary=new Library(mContext);
           }catch (Exception e){
               System.out.println(e);

           }

            Message msg=new Message();
            String status;
            try {
                status=mlibrary.login(mxiancheng_username,mxiancheng_password);
            } catch (IOException e) {
                msg.what=10;
                mHandler.sendMessage(msg);
                e.printStackTrace();
                return;
            }
            if(status.equals( "登录成功")){
                msg.what=0;
                mHandler.sendMessage(msg);
                return;
            }else{
                msg.what=1;
                mHandler.sendMessage(msg);
                return;

            }
        }
    };
    Runnable mRunnable_detail=new Runnable() {
        @Override
        public void run() {
            //mlibrary=new Library(mContext);
            Message msg=new Message();
            try {
                mxiancheng_borrowdetail=mlibrary.get_borrwingdetail();

            } catch (IOException e) {

                msg.what=10;
                mHandler.sendMessage(msg);
                e.printStackTrace();
                return;
            }
            if (mxiancheng_borrowdetail.length==0){
                msg.what=2;
                mHandler.sendMessage(msg);
                return;
            }
            msg.what=3;
            mHandler.sendMessage(msg);
            return;
        }
    };
    Runnable mRunnable_renew=new Runnable() {
        @Override
        public void run() {
            //mlibrary=new Library(mContext);
            Message msg=new Message();
            try {
                mxiancheng_renew_detail=mlibrary.renew_all();

            } catch (IOException e) {

                msg.what=10;
                mHandler.sendMessage(msg);
                e.printStackTrace();
                return;
            }
            msg.what=5;
            mHandler.sendMessage(msg);
            return;
        }
    };
    final android.os.Handler mHandler=new Handler(){
        @Override
        public void  handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what==0){//登录成功
                mxianchengchi_login_status=1;
                mTextView_usercard.setText("学号:"+mlibrary.getStuID());
                mTextView_username.setText("姓名:"+mlibrary.getStuName());
                mTextView_userstate.setText("状态:"+mlibrary.getState());

                customRuningDialog.dismiss();
                Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                mExecutorService.execute(mRunnable_detail);

            }
            if (msg.what==1){//登录失败
                mxianchengchi_login_status=0;

                customRuningDialog.dismiss();
                preferences_editor.putBoolean("keeppwd", false);
                preferences_editor.putString("password", "");
                preferences_editor.commit();
                //Toast.makeText(mContext,"test",Toast.LENGTH_SHORT).show();

                Toast.makeText(mContext, "登陆失败，密码错误", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(mContext, jiaowu_Schedule_login.class);
                startActivityForResult(intent, 1);

            }
            if (msg.what==2){//没有借书
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "您没有在图书馆借书", Toast.LENGTH_SHORT).show();

            }
            if (msg.what==3){//借书列表拉取成功
                Map<String, Object> map;

                for (int i=0;i<mxiancheng_borrowdetail.length;i++) {
                    map = new HashMap<String, Object>();
                    if(mxiancheng_borrowdetail[i][1].length()>28){
                        mxiancheng_borrowdetail[i][1]=mxiancheng_borrowdetail[i][1].substring(0,28)+"...";
                    }
                    map.put("title",mxiancheng_borrowdetail[i][1]);
                    map.put("ima",R.drawable.item_borrows);
                    map.put("bookid",mxiancheng_borrowdetail[i][0]);
                    map.put("borrowdate",mxiancheng_borrowdetail[i][2]);
                    map.put("returndate",mxiancheng_borrowdetail[i][3]);
                    //map.put("bala","￥" + xiancheng_ph[i].getBala().replace("-",""));
                    listdata.add(map);}
                if(xiancheng_bollean==false){
                    xiancheng_bollean=true;
                    customRuningDialog.dismiss();

                    adapter = new SimpleAdapter(mContext, listdata,
                            R.layout.activity_library_personal_main_item , new String[] { "title", "ima", "bookid", "borrowdate", "returndate"},
                            new int[] { R.id.TextView_library_title, R.id.iv_gridview_item,
                                    R.id.TextView_library_id,	R.id.textView_library_borrowdate,R.id.textView_library_returndate});

                    mListView.setAdapter(adapter);	//捆绑适配器}



                }
                adapter.notifyDataSetChanged();	//列表刷新

                //mPullToRefreshView.onFooterRefreshComplete();

            }
            if (msg.what==4){//注销成功


            }
            if (msg.what==5){//续借
                customRuningDialog.dismiss();
                Toast.makeText(mContext,mxiancheng_renew_detail, Toast.LENGTH_LONG).show();

            }
            if (msg.what==10){//超时
                    customRuningDialog.dismiss();
                    Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();

            }
        }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_library_personal_main, "图书馆个人中心");
        MobclickAgent.onEvent(this, "library_personal");

        mTextView_usercard=(TextView)findViewById(R.id.text_library_usercard);
         mTextView_username=(TextView)findViewById(R.id.text_library_username);
         mTextView_userstate=(TextView)findViewById(R.id.text_library_userstate);
        init();
        login();


        //setContentView(R.layout.activity_library_guancang_main);


    }
    private void login(){
        xiancheng_bollean=false;
        String user_save=preferences_data.getString("username", "");
        String password_save=preferences_data.getString("password", "");
        if (user_save.equals("") || password_save.equals("")){

            Intent intent = new Intent();
            intent.setClass(mContext, Library_personal_login.class);
            startActivityForResult(intent, 1);
            return;
        }else{
            mxiancheng_username=user_save;
            mxiancheng_password=password_save;}

        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在登录");
        mExecutorService.execute(mRunnable_login);


    }
    private void init(){
        mButton_renew=(Button)findViewById(R.id.button_library_renewall);
        mButton_logout=(Button)findViewById(R.id.button_library_logout);
        mListView=(ListView)findViewById(R.id.listView_mybook);
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);
        preferences_data = mContext.getSharedPreferences("LibraryData", Activity.MODE_PRIVATE);
        preferences_editor = preferences_data.edit();
        mButton_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mxianchengchi_login_status == 1) {
                    mExecutorService.execute(mRunnable_renew);
                    customRuningDialog.show();    //打开等待框
                    customRuningDialog.setMessage("正在续借");
                }


            }
        });
        mButton_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mxianchengchi_login_status == 1) {
                    mxianchengchi_login_status = 0;
                    mxiancheng_password = "";
                    preferences_editor.putString("password", "");
                    preferences_editor.commit();
                    login();

                }

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    mxiancheng_username=bundle.getString("username");
                    mxiancheng_password=bundle.getString("password");
                    customRuningDialog.show();    //打开等待框
                    customRuningDialog.setMessage("正在登录");
                    mExecutorService.execute(mRunnable_login);
                    break;
                }
                if (resultCode == RESULT_CANCELED) {
                    finish();
                    break;

                }
                break;
        }
    }
    }
