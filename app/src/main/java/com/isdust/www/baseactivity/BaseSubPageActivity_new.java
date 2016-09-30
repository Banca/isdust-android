package com.isdust.www.baseactivity;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.isdust.www.MyApplication;
import com.isdust.www.R;

import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by wzq on 15/11/9.
 */
public class BaseSubPageActivity_new extends SwipeBackActivity {
    protected MyApplication isdustapp;	//通过app调全局变量
    protected Context mContext;

    protected void INIT(int pageid,String title) {
        isdustapp = (MyApplication) this.getApplication();
        setContentView(pageid);
        mContext = this;
        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText(title);	//修改页面标题
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }   //初始化

    public void onTitleBarClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back_btn:
                Utils.convertActivityToTranslucent(this);
                getSwipeBackLayout().scrollToFinishActivity();
                break;
        }
}}
