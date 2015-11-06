package com.isdust.www;

import android.app.Application;

import com.isdust.www.datatype.Book;

import java.util.List;

import pw.isdust.isdust.function.SchoolCard;

/**
 * Created by Administrator on 2015/10/16.
 */
public class MyApplication extends Application {
    private SchoolCard usercard;
    private List<Book> mBooks;
    public void onCreate() {
        super.onCreate();
        usercard = new SchoolCard(this);

    }
    public void setBooks(List<Book> Books){
        mBooks=Books;
    }
    public List<Book> getBooks(){
        return mBooks;
    }
    public SchoolCard getUsercard() {
        return usercard;
    }  //使usercard这种非序列化对象 全局可调
}

