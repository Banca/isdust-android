package com.formal.sdusthelper.baseactivity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.formal.sdusthelper.MyApplication;
import com.formal.sdusthelper.R;
import com.formal.sdusthelper.SlideMenu;

/**
 * 子页面父类
 * Created by Administrator on 2015/10/20.
 */
public class BaseSubPageActivity extends Activity{
    protected MyApplication isdustapp;	//通过app调全局变量
    protected Context mContext;

    protected void INIT(int pageid,String title) {
        isdustapp = (MyApplication) this.getApplication();
        setContentView(pageid);
        mContext = this;
        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText(title);	//修改页面标题
    }   //初始化

    public void onTitleBarClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back_btn:
                finish();
                break;
        }
    }
}
