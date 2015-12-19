package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;

/**
 * Created by Leng Hanchao on 2015/10/31.
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
public class KuaiTongInfoActivity extends BaseSubPageActivity_new {

    private String data[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_kuaitong_info, "账户信息");
        Intent intent = getIntent() ;

        data = intent.getStringArrayExtra("KuaiTongData") ;
        dealData();
    }

    private void dealData() {
        TextView textView;
        textView = (TextView) findViewById(R.id.text_kuaitong_info0);
        textView.setText(textView.getText().toString()+data[0]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info1);
        textView.setText(textView.getText().toString()+data[1]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info2);
        textView.setText(textView.getText().toString()+data[2]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info3);
        textView.setText(textView.getText().toString()+data[3]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info4);
        textView.setText(textView.getText().toString()+data[4]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info5);
        textView.setText(textView.getText().toString()+data[5]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info6);
        textView.setText(textView.getText().toString()+data[6]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info7);
        textView.setText(textView.getText().toString()+data[7]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info8);
        textView.setText(textView.getText().toString()+data[8]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info9);
        textView.setText(textView.getText().toString()+data[9]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info10);
        textView.setText(textView.getText().toString()+data[10]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info11);
        textView.setText(textView.getText().toString()+data[11]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info12);
        textView.setText(textView.getText().toString()+data[12]);
        textView = (TextView) findViewById(R.id.text_kuaitong_info13);
        textView.setText(textView.getText().toString()+data[13]);
    }
}
