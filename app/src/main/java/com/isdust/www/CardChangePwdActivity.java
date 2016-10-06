package com.isdust.www;

import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leng Hanchao on 2015/10/16.
 * Midified and Refactored by Wang Ziqiang
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
public class CardChangePwdActivity extends BaseSubPageActivity {
    String xiancheng_oldpassword,xiancheng_newpassword1,xiancheng_newpassword2,xiancheng_shengfenzheng,xiancheng_result;

    ExecutorService mExecutorService= Executors.newCachedThreadPool();
    android.os.Handler mHandler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what==1){//修改成功
                CardChangePwdActivity.this.setResult(RESULT_OK);
                finish();

            }
            if (msg.what==2){//密码修改失败
                Toast.makeText(mContext, xiancheng_result, Toast.LENGTH_SHORT).show();

            }
            if (msg.what == 10){//网络超时
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
            }

        }
    };
    Runnable xiancheng_changepasswod=new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            try {

                xiancheng_result = isdustapp.getUsercard().changepassword(xiancheng_oldpassword,xiancheng_newpassword1,xiancheng_shengfenzheng);
            } catch (IOException e) {
                e.printStackTrace();
                message.what=10;
                mHandler.sendMessage(message);
                return;
            }
            if (xiancheng_result.equals("修改密码成功")){
                message.what=1;
                mHandler.sendMessage(message);
                return;
            }
            message.what=2;
            mHandler.sendMessage(message);


        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_cardchangepwd, "密码修改");
        mContext=this;
        MobclickAgent.onEvent(this, "schoolcard_changePwd");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FormCard_button_changeok:
                EditText textid = (EditText) findViewById(R.id.FormCard_editText_IDcard);
                EditText textoldpwd = (EditText) findViewById(R.id.FormCard_editText_oldpwd);
                EditText textnewpwd = (EditText) findViewById(R.id.FormCard_editText_newpwd);
                EditText textrenewpwd = (EditText) findViewById(R.id.FormCard_editText_renewpwd);
                xiancheng_shengfenzheng = textid.getText().toString();
                xiancheng_oldpassword = textoldpwd.getText().toString();
                xiancheng_newpassword1= textnewpwd.getText().toString();
                xiancheng_newpassword2 = textrenewpwd.getText().toString();
                if (xiancheng_newpassword1.length()!=6){
                    Toast.makeText(this, "密码需要设置为6位纯数字", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (xiancheng_newpassword1.equals(xiancheng_newpassword2)) {
                    mExecutorService.execute(xiancheng_changepasswod);

                }
                else
                    Toast.makeText(this, "新密码前后不一致", Toast.LENGTH_SHORT).show();
                break;
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
