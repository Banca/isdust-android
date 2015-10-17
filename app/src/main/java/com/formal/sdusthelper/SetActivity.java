package com.formal.sdusthelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2015/10/17.
 */
public class SetActivity extends Activity {
    private MyApplication isdustapp;	//通过app调全局变量
    private SlideMenu slideMenu;    //侧边栏
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isdustapp = (MyApplication) this.getApplication();
        setContentView(R.layout.helper_set);
        mContext = this;
    }
    public void onClick(View v) {
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
            case R.id.slide_menu_jiaowu:
                intent.setClass(this, JiaowuActivity.class);
                break;
            case R.id.slide_menu_library:
                intent.setClass(this, LibraryActivity.class);
                break;
            case R.id.slide_menu_card:
                intent.setClass(this, CardActivity.class);
                break;
            case R.id.slide_menu_life:
                intent.setClass(this, LifeActivity.class);
                break;
            case R.id.slide_menu_news:
                intent.setClass(this, NewsActivity.class);
                break;
            case R.id.slide_menu_set:
                intent.setClass(this, SetActivity.class);
                break;
        }
        //启动activity
        this.startActivity(intent);

        if (!slideMenu.isMainScreenShowing()) {
            slideMenu.closeMenu();
        }
    }
}
