package com.isdust.www;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.isdust.www.datatype.Book;
import com.isdust.www.view.IsdustDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Library;

/**
 * Created by Wang Ziqiang on 2015/10/17.
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
public class Library_guancang_main extends BaseSubPageActivity_new {
    Context mContext;
    Library mLibrary;
    static EditText mEditText;
    List<Book> mBooks;
    ImageView mImageView_library;
    ImageView mImageView_search;

    protected IsdustDialog customRuningDialog;  //自定义运行中提示框


    //线程池搭建
    String mxiancheng_isbn;
    String mxiancheng_bookname;
    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    Runnable mRunnable_findbookbyisbn=new Runnable() {
        @Override
        public void run() {

            Message message = new Message();
            try {
                mBooks=mLibrary.findBookByISBN(mxiancheng_isbn);
            } catch (Exception e) {
                message.what = 10;
                mHandler.sendMessage(message);;
                return;            }

            if (mBooks.size()==0){
                message.what = 0;
                mHandler.sendMessage(message);;
                return;
            }
            message.what = 1;
            mHandler.sendMessage(message);;

        }
    };
    Runnable mRunnable_findbookbyname=new Runnable() {
        @Override
        public void run() {
            Message message = new Message();

            try {
                mBooks=mLibrary.findBookByName(mxiancheng_bookname);
            } catch (Exception e) {
                message.what = 10;
                mHandler.sendMessage(message);;
                return;
            }

            if (mBooks.size()==0){
                message.what = 0;
                mHandler.sendMessage(message);;
                return;
            }
            message.what = 1;
            mHandler.sendMessage(message);;



        }
    };

    final android.os.Handler mHandler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        if (msg.what==0){
            customRuningDialog.hide();
            Toast.makeText(mContext, "没有找到该书", Toast.LENGTH_SHORT).show();
            return;
        }else if (msg.what==1){
            customRuningDialog.hide();
            isdustapp.setBooks(mBooks);
            Intent intent = new Intent();

            intent.setClass(mContext,Library_guancang_result.class);
            startActivity(intent);
            return;
         }else if (msg.what == 10){
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
            }
        }
    };


    //Application isdust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_library_guancang_main, "图书馆");
        MobclickAgent.onEvent(this, "library_guancang");

        mContext=this;
        try {
            mLibrary=new Library(this);
        } catch (Exception e) {
            Toast.makeText(mContext, "在线参数获取失败，请保证网络正常的情况下重启app", Toast.LENGTH_SHORT).show();

        }
        mEditText=(EditText)findViewById(R.id.guancang_edittext);
        mImageView_library=(ImageView)findViewById(R.id.guancang_scan);
        mImageView_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(mContext, Library_guancang_scan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
            }
        });
        mImageView_search=(ImageView)findViewById(R.id.guancang_search);
        mImageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mxiancheng_bookname =mEditText.getText().toString();
                if (mxiancheng_bookname.equals("")){
                    Toast.makeText(mContext, "书本名字不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mExecutorService.execute(mRunnable_findbookbyname);
                customRuningDialog.show();    //打开等待框
                customRuningDialog.setMessage("正在查找图书...");


            }
        });

        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    mxiancheng_isbn = bundle.getString("result");

                    mExecutorService.execute(mRunnable_findbookbyisbn);
                    customRuningDialog.show();    //打开等待框
                    customRuningDialog.setMessage("正在查找图书...");
//                    mBooks.get(0).downloadpicture();

                }
        }
    }


}
