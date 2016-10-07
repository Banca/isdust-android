package com.isdust.www;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.isdust.www.Module.BaseModule;
import com.isdust.www.Module.Catagory;
import com.isdust.www.Utils.SerializableList;
import com.isdust.www.datatype.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.Network_Kuaitong;
import pw.isdust.isdust.function.Networklogin_CMCC;
import pw.isdust.isdust.function.Networklogin_ChinaUnicom;
import pw.isdust.isdust.function.SchoolCard;

/**
 * Created by Leng Hanchao on 2015/10/16.
 * Midified and Refactored by Wang Ziqiang.
 * isdust
 * Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class MyApplication extends Application {
    private SchoolCard usercard;
    private Network_Kuaitong kuaitong;
    private List<BaseModule> list = new ArrayList<>();
    private List<Catagory> catagorys = new SerializableList<>();
    private List<Book> mBooks;
    private Context mContext;
    private Networklogin_CMCC mNetworklogin_CMCC;
    private Networklogin_ChinaUnicom mNetworklogin_ChinaUnicom;
    ExecutorService mExecutorService = Executors.newCachedThreadPool();
    Runnable xiancheng_init = new Runnable() {
        @Override
        public void run() {

            mNetworklogin_CMCC = new Networklogin_CMCC();
            mNetworklogin_ChinaUnicom = new Networklogin_ChinaUnicom();
        }
    };

    public void onCreate() {
        super.onCreate();
        FeedbackAPI.initAnnoy(this, "23471189");



        mContext = this;
        mExecutorService.execute(xiancheng_init);

    }

    public Networklogin_CMCC getNetworklogin_CMCC() {
        return mNetworklogin_CMCC;
    }


    public Networklogin_ChinaUnicom getNetworklogin_ChinaUnicom() {
        return mNetworklogin_ChinaUnicom;
    }


    public void setBooks(List<Book> Books) {
        mBooks = Books;
    }

    public List<Book> getBooks() {
        return mBooks;
    }

    public SchoolCard getUsercard() {
        return usercard;
    }  //使usercard这种非序列化对象 全局可调

    public void card_init() {

            usercard = new SchoolCard(mContext);
    }

    public void kuaitong_init() {

            kuaitong = new Network_Kuaitong(mContext);
    }

    public List<BaseModule> getList() {
        return list;
    }

    public void setList(List<BaseModule> list) {
        this.list = list;
    }

    public List<Catagory> getCatagorys() {
        return catagorys;
    }

    public void setCatagorys(List<Catagory> catagorys) {
        this.catagorys = catagorys;
    }
}

