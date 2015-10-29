package com.formal.sdusthelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;

/**
 * Created by Administrator on 2015/10/17.
 */
public class LibraryActivity extends BaseMainActivity {
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_library, "图书馆");
        Button mButton_library=(Button)findViewById(R.id.button_library_scan);
        mButton_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent();
                mIntent.setClass(mContext,Library_scan.class);
                mContext.startActivity(mIntent);
            }
        });
    }

}
