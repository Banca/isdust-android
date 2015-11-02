package com.formal.sdusthelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;

/**
 * Created by Administrator on 2015/10/24.
 */
public class SetAccountActivity extends BaseSubPageActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_set_account, "账号管理");
    }

    public void onFormSetAcntClick(View v) {
        switch (v.getId()) {
            case R.id.set_btn_cmcc:
                //设置传递方向
                Intent intent = new Intent();

                intent.setClass(this,GoNetCMCCAcntActivity.class);

                //启动activity
                this.startActivity(intent);
                break;

        }
    }
}
