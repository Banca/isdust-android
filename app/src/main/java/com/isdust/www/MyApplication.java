package com.isdust.www;

import android.app.Application;
import android.content.Context;

import com.isdust.www.datatype.Book;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Network_Kuaitong;
import pw.isdust.isdust.function.Networklogin_CMCC;
import pw.isdust.isdust.function.Networklogin_ChinaUnicom;
import pw.isdust.isdust.function.SchoolCard;

/**
 * Created by Administrator on 2015/10/16.
 */
public class MyApplication extends Application {
    private SchoolCard usercard;
    private Network_Kuaitong kuaitong;
    private List<Book> mBooks;
    private Context mContext;
    private Networklogin_CMCC mNetworklogin_CMCC;
    private Networklogin_ChinaUnicom mNetworklogin_ChinaUnicom;
    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    Runnable xiancheng_init=new Runnable() {
        @Override
        public void run() {

            mNetworklogin_CMCC=new Networklogin_CMCC();
            mNetworklogin_ChinaUnicom=new Networklogin_ChinaUnicom();
        }
    };
    public void onCreate() {
        super.onCreate();


        mContext=this;
        mExecutorService.execute(xiancheng_init);


    }
    public Networklogin_CMCC getNetworklogin_CMCC(){
        return mNetworklogin_CMCC;
    };
    public Networklogin_ChinaUnicom getNetworklogin_ChinaUnicom(){
        return mNetworklogin_ChinaUnicom;
    };

    public void setBooks(List<Book> Books){
        mBooks=Books;
    }
    public List<Book> getBooks(){
        return mBooks;
    }
    public SchoolCard getUsercard() {
        return usercard;
    }  //使usercard这种非序列化对象 全局可调
    public void card_init(){
        usercard = new SchoolCard(mContext);
    }
    public void kuaitong_init(){
        kuaitong=new Network_Kuaitong(mContext);
    }
}

