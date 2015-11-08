package com.isdust.www.baseactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.AboutActivity;
import com.isdust.www.CardActivity;
import com.isdust.www.GoNetActivity;
import com.isdust.www.JiaowuActivity;
import com.isdust.www.KuaiTongActivity;
import com.isdust.www.LibraryActivity;
import com.isdust.www.MainActivity;
import com.isdust.www.MyApplication;
import com.isdust.www.NewsActivity;
import com.isdust.www.R;
import com.isdust.www.SetActivity;
import com.isdust.www.SlideMenu;

/**
 * 主界面的父类
 * Created by Administrator on 2015/10/18.
 */
public class BaseMainActivity extends Activity{
    protected MyApplication isdustapp;	//通过app调全局变量
    protected SlideMenu slideMenu;    //侧边栏
    protected Context mContext;
    private long exitTime = 0;

    protected void INIT(int pageid,String title) {
        isdustapp = (MyApplication) this.getApplication();
        setContentView(pageid);
        mContext = this;
        slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText(title);	//修改页面标题
    }   //初始化

    public void onTitleBarClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_menu_btn:
                if (slideMenu.isMainScreenShowing()) {
                    slideMenu.openMenu();
                } else {
                    slideMenu.closeMenu();
                }
                break;
        }
    }
    public void onMenuClick(View v) {
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.slide_menu_home:
                intent.setClass(this, MainActivity.class);
                break;
            case R.id.slide_menu_cmcc:
                intent.setClass(this, GoNetActivity.class);
                break;
            case R.id.slide_menu_kuaitong:
                intent.setClass(this, KuaiTongActivity.class);
                break;
            case R.id.slide_menu_jiaowu:
                intent.setClass(this, JiaowuActivity.class);
                break;
            case R.id.slide_menu_library:
                intent.setClass(this, LibraryActivity.class);
                break;
            case R.id.slide_menu_card:
                intent.setClass(this, CardActivity.class);
                break;
            case R.id.slide_menu_news:
                intent.setClass(this, NewsActivity.class);
                break;
            case R.id.slide_menu_set:
                intent.setClass(this, SetActivity.class);
                break;
            case R.id.slide_menu_about:
                intent.setClass(this, AboutActivity.class);
                break;
        }
        //启动activity
        this.startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

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
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
