package com.isdust.www;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.isdust.www.Module.CardModule;
import com.isdust.www.Module.Catagory;
import com.isdust.www.Module.KuaiTongModule;
import com.isdust.www.Module.WlanModule;
import com.isdust.www.Module.jiaowu_ClassroomModule;
import com.isdust.www.Module.jiaowu_MarkModule;
import com.isdust.www.Module.jiaowu_ScheduleModule;
import com.isdust.www.Module.library_PersonalModule;
import com.isdust.www.Module.library_SearchModule;
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
public class MyApplication extends Application {
    private SchoolCard usercard;
    private Network_Kuaitong kuaitong;
    public List<Catagory> list = new ArrayList<>();
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
        initSchoolServer();


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
        try {
            usercard = new SchoolCard(mContext);
        } catch (Exception e) {
            Toast.makeText(mContext,"在线参数获取失败，请保证网络正常的情况下重启app",Toast.LENGTH_SHORT);
            return;
        }
    }
    public void kuaitong_init(){
        try {
            kuaitong=new Network_Kuaitong(mContext);
        } catch (Exception e) {
            Toast.makeText(mContext, "在线参数获取失败，请保证网络正常的情况下重启app", Toast.LENGTH_SHORT);
            return;
        }
    }
    private void initSchoolServer(){
        Catagory card = new Catagory(R.string.schoolcard_catgory);
        card.addItem(CardModule.getInstance());

        Catagory jiaowu = new Catagory(R.string.Jiaowu_catgory);
        jiaowu.addItem(jiaowu_MarkModule.getInstance());
        jiaowu.addItem(jiaowu_ClassroomModule.getInstance());
        jiaowu.addItem(jiaowu_ScheduleModule.getInstance());

        Catagory library = new Catagory(R.string.library_catagory);
        library.addItem(library_SearchModule.getInstance());
        library.addItem(library_PersonalModule.getInstance());

        Catagory net = new Catagory(R.string.net_catgory);
        net.addItem(KuaiTongModule.getInstance());
        net.addItem(WlanModule.getInstance());


        list.add(card);
        list.add(jiaowu);
        list.add(library);
        list.add(net);
    }
}

