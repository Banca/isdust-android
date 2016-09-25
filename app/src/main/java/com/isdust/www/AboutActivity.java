package com.isdust.www;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.UserInfo;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

;import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wang Ziqiang on 2015/10/17.
 *  isdust
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
@SuppressLint("ClickableViewAccessibility")

public class AboutActivity extends BaseMainActivity_new {

    Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onEvent(this, "About");


        INIT(R.layout.helper_about, "关于我们",0);
        Button mButton_feedback=(Button)findViewById(R.id.button_feedback);
        Button mButton_update=(Button)findViewById(R.id.button_update);
        mButton_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackAgent agent = new FeedbackAgent(thisActivity);
                UserInfo info = agent.getUserInfo();
                if (info == null)
                    info = new UserInfo();
                Map<String, String> contact = info.getContact();
                if (contact == null)
                    contact = new HashMap<>();
                SharedPreferences sp = thisActivity.getSharedPreferences("data", 0);
                String num = sp.getString("num", "");
                contact.put("num", num);
                info.setContact(contact);
                agent.setUserInfo(info);
                agent.startFeedbackActivity();
            }
        });
        mButton_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int arg0, UpdateResponse arg1) {
                        // TODO Auto-generated method stub
                        switch (arg0) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(mContext, arg1);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(mContext, getString(R.string.umeng_isNewest), Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(mContext, getString(R.string.umeng_notWifi), Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(mContext, getString(R.string.umeng_timeout), Toast.LENGTH_SHORT)
                                        .show();
                                break;
                        }
                    }
                });

                UmengUpdateAgent.forceUpdate(mContext);
            }
        });
    }


}
