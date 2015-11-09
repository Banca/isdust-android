package com.isdust.www.baseactivity;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.MyApplication;
import com.isdust.www.R;
import com.isdust.www.SlideMenu;
import com.isdust.www.menu.Leftmenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by wzq on 15/11/9.
 */
public class BaseMainActivity_new extends Activity {
    protected MyApplication isdustapp;	//通过app调全局变量
    protected SlideMenu slideMenu;    //侧边栏
    protected Context mContext;
    private long exitTime = 0;
    Leftmenu mLeftmenu;
    SlidingMenu menu;
    Activity thisActivity;

    protected void INIT(int pageid,String title,int type) {
        isdustapp = (MyApplication) this.getApplication();
        setContentView(pageid);
        mContext = this;
        thisActivity = this;

        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText(title);	//修改页面标题
        mLeftmenu = new Leftmenu(thisActivity, type);
        menu = mLeftmenu.menu;
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
    @Override
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
