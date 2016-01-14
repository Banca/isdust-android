package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.baseactivity.BaseMainActivity_new;

/**
 * Created by Wang Ziqiang on 15/12/2.
 * isdust
 * Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

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
public class Library_index extends BaseMainActivity_new {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_library, "图书馆",4);


    }
    public void onFormJiaowuClick(View v) {
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.library_button_guancang:
                intent.setClass(this,Library_guancang_main.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.library_button_personel:
                intent.setClass(this,Library_personal_main.class);
                //启动activity
                this.startActivity(intent);
                break;



        }
    }
}
