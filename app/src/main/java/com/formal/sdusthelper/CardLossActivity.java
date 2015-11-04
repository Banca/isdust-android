package com.formal.sdusthelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2015/10/16.
 */
public class CardLossActivity extends BaseSubPageActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_cardloss, "校园卡挂失");
        MobclickAgent.onEvent(this, "schoolcard_guashi");

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FormCard_button_ok:   //确认按钮
                new AlertDialog.Builder(this).setTitle("确认挂失吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                EditText textid = (EditText) findViewById(R.id.FormCard_editText_IDcard);
                                EditText textpwd = (EditText) findViewById(R.id.FormCard_editText_pwd);
                                String strid = textid.getText().toString();
                                String strpwd = textpwd.getText().toString();
                                String result;
                                result = isdustapp.getUsercard().guashi(strpwd, strid);
                                Toast.makeText(mContext, result, 1000).show();
                                if (result.equals("挂失成功"))
                                    finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();
                break;
        }
    }
}
