package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity_new;

/**
 * Created by wzq on 15/11/23.
 */
public class LifeActivity extends BaseMainActivity_new {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_life, "生活服务",8);
    }
    public void onFormJiaowuClick(View v) {
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.life_button_dianfei:
                intent.setClass(this,Jiaowu_EmptyRoom.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.life_button_map:
                intent.setClass(this,Jiaowu_Schedule_main.class);
                //启动activity
                this.startActivity(intent);
                break;



        }
    }
}