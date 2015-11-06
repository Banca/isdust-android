package com.isdust.www;

import android.os.Bundle;

import com.isdust.www.baseactivity.BaseMainActivity;

/**
 * Created by Administrator on 2015/10/17.
 */
public class AboutActivity extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_about, "关于我们");
    }

}
