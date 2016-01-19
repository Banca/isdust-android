package com.isdust.www.baseactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.MainActivity;
import com.isdust.www.MyApplication;
import com.isdust.www.R;
import com.isdust.www.menu.Leftmenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by wzq on 15/11/9.
 */
public class BaseMainActivity_new extends Activity {
    protected MyApplication isdustapp;	//通过app调全局变量
    protected Context mContext;
    private long exitTime = 0;
    public  Leftmenu mLeftmenu;
    SlidingMenu menu;
    Activity thisActivity;
    int mtype;
    protected void INIT(int pageid) {
        isdustapp = (MyApplication) this.getApplication();
        setContentView(pageid);
        mContext = this;
        thisActivity = this;
        mLeftmenu = new Leftmenu(thisActivity, mtype);
        menu = mLeftmenu.menu;
    }   //初始化
    protected void INIT(int pageid,String title,int type) {
        isdustapp = (MyApplication) this.getApplication();
        setContentView(pageid);
        mContext = this;
        thisActivity = this;
        mtype=type;
        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText(title);	//修改页面标题
        mLeftmenu = new Leftmenu(thisActivity, mtype);
        menu = mLeftmenu.menu;
    }   //初始化
@Override
public void onResume() {
    super.onResume();
    mLeftmenu.leftmenu_ui(mtype);

    MobclickAgent.onResume(this);
}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (mtype==0){
                if((System.currentTimeMillis()-exitTime) > 1000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
                {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }
                else
                {
                    finish();
                    System.exit(0);
                }

            }else {
                Intent intent = new Intent();
                intent.setClass(thisActivity, MainActivity.class);
                thisActivity.startActivity(intent);
                finish();

            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void onTitleBarClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_menu_btn:

                if (menu.isMenuShowing()) {
                    menu.toggle();
                } else {
                    menu.showMenu();
                }
                break;
        }
    }

}
