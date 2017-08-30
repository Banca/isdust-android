package com.isdust.www;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity;

/**
 * Created by Leng Hanchao on 2015/10/16.
 * isdust
 Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class CardLossActivity extends BaseSubPageActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        INIT(R.layout.activity_cardloss, "校园卡挂失");
        //MobclickAgent.onEvent(this, "schoolcard_guashi");


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
                                EditText textpwd = (EditText) findViewById(R.id.EditText_library_login_password);
                                String strid = textid.getText().toString();
                                String strpwd = textpwd.getText().toString();
                                String result;
                                try {
                                    result = isdustapp.getUsercard().guashi(strpwd, strid);
                                    Toast.makeText(mContext, result, 1000).show();
                                    if (result.equals("挂失成功"))
                                        finish();
                                } catch (Exception e) {
                                    Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
                                    return;

                                }

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
