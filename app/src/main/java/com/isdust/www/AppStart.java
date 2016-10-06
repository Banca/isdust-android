package com.isdust.www;

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

    static boolean broadcast=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //umeng设置
        mContext=this;
        OnlineConfig.updateandload(this);
//        MobclickAgent.updateOnlineConfig(mContext);
//        UmengUpdateAgent.setUpdateOnlyWifi(false);
//        UmengUpdateAgent.update(this);
//
//        OnlineConfigAgent.getInstance().setDebugMode(true);
//        OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);

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
                checkNet();
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
    private void checkNet(){

//        String install = OnlineConfigAgent.getInstance().getConfigParams(mContext, "install");
//        if (!install.equals("true")){
//            Toast.makeText(mContext,"第一次运行该程序，请保证手机能访问网络，然后重启该应用",Toast.LENGTH_LONG).show();
//        }

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
