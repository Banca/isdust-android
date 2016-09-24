package com.isdust.www.tab;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.isdust.www.AboutActivity;
import com.isdust.www.MainActivity;
import com.isdust.www.NewsActivity;
import com.isdust.www.R;
import com.isdust.www.SchoolServerActivity;

public class TabActivity extends android.app.TabActivity {

    private TabHost my_tabhost;
    private String[] tabMenu = {"首页", "校园资讯","关于"};
    private Intent intent1, intent2,intent3;
    private TabHost.TabSpec tabSpec1, tabSpec2,tabSpec3;
    TabWidget mTabWidget = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        my_tabhost = getTabHost();
        //获取导航按钮控件
        mTabWidget = my_tabhost.getTabWidget();


        intent2 = new Intent(this, SchoolServerActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent3 = new Intent(this, AboutActivity.class);
        intent1 = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabSpec1 = my_tabhost.newTabSpec("main").setIndicator(tabMenu[0], null).
                setContent(intent1);
        tabSpec2 = my_tabhost.newTabSpec("info").setIndicator(tabMenu[1], null).
                setContent(intent2);
        tabSpec3 = my_tabhost.newTabSpec("about").setIndicator(tabMenu[2], null).
                setContent(intent3);
        my_tabhost.addTab(tabSpec1);
        my_tabhost.addTab(tabSpec2);
        my_tabhost.addTab(tabSpec3);
        my_tabhost.setCurrentTab(0);
        init();
    }
    private void init(){
        //逐个按钮添加特效
        for(int i=0;i<mTabWidget.getChildCount();i++){
            //换字体颜色
            TextView tv = (TextView)
                    mTabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.rgb(255, 255, 255));
            //设置背景图
            mTabWidget.getChildAt(i).setBackgroundResource(
                    R.drawable.card);
        }
    }



}
