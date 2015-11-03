package com.formal.sdusthelper;

import android.app.Activity;
import android.os.Bundle;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;
import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;

/**
 * Created by Administrator on 2015/10/31.
 */
public class KuaiTongActivity extends BaseMainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_kuaitong, "快通有线");
    }
}
