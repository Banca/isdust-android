package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity_new;

/**
 * Created by Administrator on 2015/10/17.
 */
public class SetActivity extends BaseMainActivity_new {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_set, "设置",8);
    }

    public void onFormSetClick(View v) {
        switch (v.getId()) {
            case R.id.set_btn_account:
                //设置传递方向
                Intent intent = new Intent();

                intent.setClass(this,SetAccountActivity.class);

                //启动activity
                this.startActivity(intent);
                break;
        }
    }
}
