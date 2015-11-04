package com.formal.sdusthelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;
import com.formal.sdusthelper.datatype.Book;
import com.formal.sdusthelper.view.IsdustDialog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Library;

/**
 * Created by Administrator on 2015/10/17.
 */
public class LibraryActivity extends BaseMainActivity {
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
            mBooks=mLibrary.findBookByISBN(mxiancheng_isbn);
            Message message = new Message();
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
            mBooks=mLibrary.findBookByName(mxiancheng_bookname);
            Message message = new Message();

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

            intent.setClass(mContext,Library_result.class);
            startActivity(intent);
            return;
         }
        }
    };


    //Application isdust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
        mLibrary=new Library();
        INIT(R.layout.helper_library, "图书馆");
        mEditText=(EditText)findViewById(R.id.guancang_edittext);
        mImageView_library=(ImageView)findViewById(R.id.guancang_scan);
        mImageView_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(mContext, Library_scan.class);
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
