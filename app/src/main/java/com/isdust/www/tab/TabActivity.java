package com.isdust.www.tab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        my_tabhost = getTabHost();
       // jumpTo(MainActivity.class);

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
    }

    private void jumpTo(Class<MainActivity> cls) {



    }
}
