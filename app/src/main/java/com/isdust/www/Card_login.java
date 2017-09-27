package com.isdust.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.isdust.www.baseactivity.BaseSubPageActivity;

import java.io.IOException;

/**
 *
 * Created by Leng Hanchao on 15/11/7.
 * Midified and Refactored by Wang Ziqiang
 *   isdust
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
public class Card_login extends BaseSubPageActivity {
    Context mContext;
    SharedPreferences.Editor preferences_editor;
    SharedPreferences preferences_data;
    CheckBox mCheckBox_savepwd;
    EditText mEditText_user,mEditText_password,mEditText_yzm;
    ImageView imageView_yzm;
    Button mButton_login;
    protected void onCreate(Bundle savedInstanceState) {

        mContext=this;
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_card_login, "校园卡登录");
        mContext=this;
        //实例化SharedPreferences对象
        preferences_data = mContext.getSharedPreferences("CardData", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        preferences_editor = preferences_data.edit();
        getview();
        getLocalData();


        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, password,yzm;
                user = mEditText_user.getText().toString();
                password = mEditText_password.getText().toString();
                yzm = mEditText_yzm.getText().toString();
                preferences_editor.putString("username", user);
                if (mCheckBox_savepwd.isChecked()) {    //记住密码
                    preferences_editor.putBoolean("keeppwd", true);
                    preferences_editor.putString("password", password);
                } else {    //不记住密码
                    preferences_editor.putBoolean("keeppwd", false);
                    preferences_editor.putString("password", "");
                }
                //提交当前数据
                preferences_editor.commit();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("username", user);
                bundle.putString("password", password);
                bundle.putString("yzm", yzm);
                intent.putExtras(bundle);
                Card_login.this.setResult(RESULT_OK, intent);

                finish();

            }
        });


    }
    private void getLocalData() {
        // 使用getString方法获得value，注意第2个参数是value的默认值

        String name =preferences_data.getString("username", "");
        String pwd =preferences_data.getString("password", "");


        Boolean keeppwd;
        mEditText_user.setText(name);
        mEditText_password.setText(pwd);
        keeppwd = preferences_data.getBoolean("keeppwd", true);
        mCheckBox_savepwd.setChecked(keeppwd);


    }   //读取本地数据
    private void getview(){
        mEditText_user=(EditText)findViewById(R.id.edittext_schoolcard_user);
        mEditText_password=(EditText)findViewById(R.id.edittext_schoolcard_pwd);
        mEditText_yzm=(EditText)findViewById(R.id.edittext_schoolcard_yzm);
        mCheckBox_savepwd=(CheckBox)findViewById(R.id.check_schoolcard_savepwd);
        mButton_login=(Button)findViewById(R.id.btn_schoolcard_ok);
        imageView_yzm=(ImageView)findViewById(R.id.imageview_yzm);
        try {
            Bitmap bitmap = isdustapp.getMhttp().get_image("http://192.168.100.126/getCheckpic.action?rand=2080.7104059143767");
            imageView_yzm.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onTitleBarClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back_btn:
                this.setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

}
