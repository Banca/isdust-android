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
            case R.id.picturemap:
                intent.putExtra("judge","picturemap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
            case R.id.ATM_map:
                intent.putExtra("judge","ATMmap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
            case R.id.print:
                intent.putExtra("judge","printmap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
            case R.id.hospital:
                intent.putExtra("judge","hospitalmap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
            case R.id.express_map:
                intent.putExtra("judge","expressmap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
            case R.id.KTV_map:
                intent.putExtra("judge","KTVmap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
            case R.id.barbershop_map:
                intent.putExtra("judge","barbershopmap");
                intent.setClass(this, SchoolMapActivity.class);
                break;
        }
        //启动activity
        this.startActivity(intent);
    }
}
