package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity_new;

/**
 * Created by wzq on 15/12/2.
 */
public class Library_index extends BaseMainActivity_new {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_library, "图书馆",4);


    }
    public void onFormJiaowuClick(View v) {
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.library_button_guancang:
                intent.setClass(this,Library_guancang_main.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.library_button_personel:
                intent.setClass(this,jiaowu_Schedule_main.class);
                //启动activity
                this.startActivity(intent);
                break;



        }
    }
}
