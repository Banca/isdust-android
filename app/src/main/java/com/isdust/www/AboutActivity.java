package com.isdust.www;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.isdust.www.frame.About;
import com.isdust.www.frame.Main;
import com.isdust.www.frame.SchoolServer;
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


    public static class TabActivity extends FragmentActivity {

        private RadioGroup navGroup;
        private long exitTime = 0;
        String tabs[] = {"Tab1", "Tab2", "Tab3"};
        TextView[] textViews=new TextView[3];


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab);
            initView();
        }

        private void initView() {
            navGroup = (RadioGroup) findViewById(R.id.frames);
            textViews[0]=(TextView)findViewById(R.id.fram1);
            textViews[1]=(TextView)findViewById(R.id.fram2);
            textViews[2]=(TextView)findViewById(R.id.fram3);
            navGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.fram1:
                            switchFragmentSupport(R.id.content, tabs[0]);
                            break;
                        case R.id.fram2:
                            switchFragmentSupport(R.id.content, tabs[1]);
                            break;
                        case R.id.fram3:
                            switchFragmentSupport(R.id.content, tabs[2]);
                            break;
                    }
                }
            });
            RadioButton btn = (RadioButton) navGroup.getChildAt(0);
            btn.toggle();
        }

        private void switchFragmentSupport(int containId, String tag) {
            //获取FramegrameManager管理器
            FragmentManager manager = getFragmentManager();
            //根据标签查找是否已存在对应的frame对象
            Fragment destFrament = manager.findFragmentByTag(tag);
            //如果不存在则初始化
            if (destFrament == null) {
                if (tag.equals(tabs[0])) {
                    destFrament = new Main(TabActivity.this);
                }
                if (tag.equals(tabs[1])) {
                    destFrament = new SchoolServer(TabActivity.this);
                }
                if (tag.equals(tabs[2])) {
                    destFrament = new About(TabActivity.this);
                }
            }
            //获取FramegramentTransaction事务对象
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(containId, destFrament, tag);
            ft.commit();
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                if ((System.currentTimeMillis() - exitTime) > 1000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
                {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }


    }
}
