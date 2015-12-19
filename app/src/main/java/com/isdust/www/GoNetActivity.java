package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;

import pw.isdust.isdust.function.Networkjudge;

/**
 * Created by Leng Hanchao on 2015/10/17.
 * Midified and Refactored by Wang Ziqiang.
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
public class GoNetActivity extends BaseMainActivity_new {
    Networkjudge mNetworkjudge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_gonet, "上网登录", 1);
        mNetworkjudge=new Networkjudge(mContext);

    }
    public void onFormGoNetClick(View v) {
        //设置传递方向
        int networktype;
        networktype=mNetworkjudge.judgetype();
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.FormGoNet_button_cmcc:
                if (networktype!=1){
                    Toast.makeText(mContext,"请连接CMCC网络后在进入此功能",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.setClass(this,GoNetCMCCActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormGoNet_button_chinaunicom:
                if (networktype!=2){
                    Toast.makeText(mContext,"请连接ChinaUnicom网络后在进入此功能",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.setClass(this,GoNetChinaUnicomActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
        }
    }
}
