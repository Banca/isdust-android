package com.isdust.www;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;

;

/**
 * Created by Administrator on 2015/10/17.
 */
@SuppressLint("ClickableViewAccessibility")

public class AboutActivity extends BaseSubPageActivity_new {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        INIT(R.layout.helper_about, "关于我们");
    }


}
