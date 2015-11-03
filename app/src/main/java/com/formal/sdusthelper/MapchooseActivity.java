package com.formal.sdusthelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by lenovo on 2015/11/1.
 */
public class MapchooseActivity extends BaseSubPageActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(this, "Map");

        super.onCreate(savedInstanceState);
        INIT(R.layout.schoolmapchoose, "地图指南");
    }

    public void onMapClick(View v) {
        //设置传递方向
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.baidumap:
                intent.putExtra("judge","baidumap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
            case R.id.ATM_map:
                intent.putExtra("judge","ATMmap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
        }
        //启动activity
        this.startActivity(intent);
    }
}
