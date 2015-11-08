package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity;

/**
 * Created by Administrator on 2015/10/17.
 */
public class LifeActivity extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_life, "校园生活");
    }
    public void onFormLifeClick(View v) {
        switch (v.getId()) {
            case R.id.btn_formlife_map:
                //设置传递方向
                Intent intent = new Intent();
                intent.setClass(this,MapchooseActivity.class);

                //启动activity
                this.startActivity(intent);
                break;
        }
    }
}
