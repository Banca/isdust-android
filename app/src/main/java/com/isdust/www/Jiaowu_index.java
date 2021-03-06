package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity;

/**
 * Created by Leng Hanchao on 2015/10/17.
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
public class Jiaowu_index extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_jiaowu, "教务查询",3);
    }
    public void onFormJiaowuClick(View v) {
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.FormJiaowu_button_emptyroom:
                intent.setClass(this,Jiaowu_EmptyRoom.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormJiaowu_button_schedule:
                intent.setClass(this,jiaowu_Schedule_main.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormJiaowu_button_chengjichaxun:
                intent.setClass(this,Jiaowu_chengjichaxun_main.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormJiaowu_button_ticechaxun:
                intent.setClass(this,Jiaowu_tice_search.class);
                //启动activity
                this.startActivity(intent);
                break;

        }
    }
}
