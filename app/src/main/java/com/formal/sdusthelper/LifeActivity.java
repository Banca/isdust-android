package com.formal.sdusthelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;

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
            case R.id.FormLife_btn_map:
                //设置传递方向
                Intent intent = new Intent();

                intent.setClass(this,SchoolMapActivity.class);

                //启动activity
                this.startActivity(intent);
                break;
        }
    }
}
