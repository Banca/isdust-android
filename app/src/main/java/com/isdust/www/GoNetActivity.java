package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity;

/**
 * Created by Administrator on 2015/10/17.
 */
public class GoNetActivity extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_gonet, "上网登录");
    }
    public void onFormGoNetClick(View v) {
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.FormGoNet_button_cmcc:
                intent.setClass(this,GoNetCMCCActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormGoNet_button_chinaunicom:
                intent.setClass(this,GoNetChinaUnicomActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
        }
    }
}
