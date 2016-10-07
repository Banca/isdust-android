package com.isdust.www;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.isdust.www.baseactivity.BaseMainActivity;
import com.umeng.analytics.MobclickAgent;

import pw.isdust.isdust.OnlineConfig;



public class AppStart extends BaseMainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openOrCreateDatabase("jiaowu_schedule_new.db", Context.MODE_MULTI_PROCESS, null);//创建课程表临时解决方案
        //umeng设置
        mContext=this;
        OnlineConfig.updateandload(this);


        // SystemTool.gc(this); //针对性能好的手机使用，加快应用相应速度

        final View view = View.inflate(this, R.layout.welcome, null);
        setContentView(view);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(800);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
        finish();
    }
}
