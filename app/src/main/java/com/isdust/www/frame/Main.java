package com.isdust.www.frame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isdust.www.Module.KeCheng;
import com.isdust.www.MyApplication;
import com.isdust.www.R;
import com.isdust.www.RecycleView.RecycleViewAdapter;
import com.isdust.www.Spinner.spinner;

import pw.isdust.isdust.OnlineConfig;

/**
 * Created by zor on 2016/9/29.
 */

@SuppressLint("ValidFragment")
public class Main extends Fragment {
    private TextView info;
    private RecyclerView rcv;
    protected MyApplication isdustapp;
    private spinner mSpinner;
    private SQLiteDatabase db;
    private KeCheng kecheng;
    private TextView kc;
    private String kechengInfo;
    public static RecycleViewAdapter adapter;
    private GridLayoutManager manager;
    private View v;
    private Activity mContext;
    private String brodcast;
    private TextView title;
    public Main(Activity activity){
        this.mContext=activity;
        isdustapp= (MyApplication) mContext.getApplication();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act1,null);
        initView();
        initData();
        manager = new GridLayoutManager(mContext, 6);
        adapter = new RecycleViewAdapter(mContext,isdustapp.getList());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        kc.setText(kechengInfo);
        info.setText(brodcast);
        title.setText("首页");
        return v;
    }
    void initView(){
        info = (TextView)v.findViewById(R.id.notification);
        kc = (TextView)v.findViewById(R.id.kecheng);
        title = (TextView)v.findViewById(R.id.title_bar_name);
        rcv = (RecyclerView)v.findViewById(R.id.module);
        mSpinner = new spinner(mContext,v);
        mSpinner.init();
    }
    void initData(){
        //开启数据库获取数据
        db = mContext.openOrCreateDatabase("jiaowu_schedule.db", Context.MODE_PRIVATE, null);
        kecheng=new KeCheng(db,mContext);
        kechengInfo=kecheng.getKecheng();
        brodcast= OnlineConfig.getConfigParams( "system_broadcast");
        db.close();
    }

    @Override
    public void onStart() {
        super.onStart();
        mSpinner.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSpinner.onStop();
    }
}
