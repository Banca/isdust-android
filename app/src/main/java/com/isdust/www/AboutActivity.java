package com.isdust.www;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;

;

/**
 * Created by Administrator on 2015/10/17.
 */
@SuppressLint("ClickableViewAccessibility")

public class AboutActivity extends BaseSubPageActivity_new {
    protected MyApplication isdustapp;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

        }catch (Exception e){
            System.out.println(e);
        }
        INIT(R.layout.helper_about, "关于我们");
    }
    protected void INIT(int pageid,String title) {
        isdustapp = (MyApplication) this.getApplication();
        setContentView(pageid);
        mContext = this;
        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText(title);	//修改页面标题
    }   //初始化

}
