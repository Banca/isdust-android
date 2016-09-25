package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity_new;

public class SchoolServerActivity extends BaseMainActivity_new {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.act2,"校园服务",0);
    }
    public void onFormMainClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_main_gonet:
                intent.setClass(this, GoNetActivity.class);//上网登录
                this.startActivity(intent);
                //finish();
                break;
            case R.id.btn_main_personal:
                intent.setClass(this, Library_personal_main.class);//快通查询
                this.startActivity(intent);
                break;
            case R.id.btn_main_schedule:
                intent.setClass(this, jiaowu_Schedule_main.class);//课程表
                this.startActivity(intent);
              //  finish();
                break;
            case R.id.btn_main_emptyroom:
                intent.setClass(this, Jiaowu_EmptyRoom.class);//空自习室
                this.startActivity(intent);
               // finish();
                break;
            case R.id.btn_main_guancang_library:
                intent.setClass(this, Library_guancang_main.class);//图书馆
                this.startActivity(intent);
              //  finish();
                break;
            case R.id.btn_main_card:
                intent.setClass(this, CardActivity.class);//校园卡
                this.startActivity(intent);
              //  finish();
                break;
/*			case R.id.btn_main_news:
				intent.setClass(this, NewsActivity.class);//咨询
				this.startActivity(intent);
				finish();
				break;
			case R.id.btn_main_about:
				intent.setClass(this, AboutActivity.class);//关于
				this.startActivity(intent);
				break;*/
            case R.id.btn_main_score:
                intent.setClass(this, Jiaowu_chengjichaxun_main.class);//查询成绩
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
              //  finish();
                break;
        }
    }
}
