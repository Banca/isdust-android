package com.isdust.www;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.isdust.www.datatype.Book;
import com.isdust.www.view.IsdustDialog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Library;

/**
 * Created by Administrator on 2015/10/17.
 */
public class LibraryActivity extends BaseMainActivity_new {
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
        INIT(R.layout.helper_library, "图书馆",4);
        mContext=this;
        mLibrary=new Library(this);
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
