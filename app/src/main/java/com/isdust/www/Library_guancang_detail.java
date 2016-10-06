package com.isdust.www;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pw.isdust.isdust.function.Library;

/**
 * Created by Wang Ziqiang on 15/11/1.
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
public class Library_guancang_detail extends BaseSubPageActivity {
    Library mLibrary;
    List<String[]> mguancang;
    SimpleAdapter madapter;
    Context mContext;
    ListView mListView;
    private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            INIT(R.layout.activity_library_guancang_detail, "馆藏信息");
            mListView=(ListView)findViewById(R.id.listview_library_detail);
            //mListView.setEnabled(false);
            mContext=this;

            try {
                mLibrary=new Library(this);
            } catch (Exception e) {
                Toast.makeText(mContext, "在线参数获取失败，请保证网络正常的情况下重启app", Toast.LENGTH_SHORT).show();
            }
            String bookrecnos=getIntent().getExtras().getString("bookrecnos");
            try {
                mguancang =mLibrary.getguancang(bookrecnos);
            } catch (IOException e) {
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
                return;
            }
            jiazaishuju();
        }
    public void jiazaishuju(){
        Map<String, Object> map;
        int len=mguancang.size();
        for (int i=0;i<len;i++){
            map = new HashMap<String, Object>();
            map.put("suoshuhao",mguancang.get(i)[0]);
           map.put("tiaomahao",mguancang.get(i)[1]);
            map.put("guancangzhuangtai",mguancang.get(i)[2]);
            map.put("guancangdi",mguancang.get(i)[4]+","+mguancang.get(i)[5]);
            map.put("huanshushijian",mguancang.get(i)[3]);


            listdata.add(map);

        }
        madapter = new SimpleAdapter(mContext, listdata,
                R.layout.activity_library_guancang_detail_item, new String[] { "suoshuhao", "tiaomahao", "guancangzhuangtai", "guancangdi","huanshushijian"},
                new int[] { R.id.textView_library_detail_suoshuhao, R.id.textView_library_detail_tiaomahao,
                        R.id.textView_library_detail_guancangzhuangtai,	R.id.textView_library_detail_guancangdi,R.id.textView_library_detail_huanshushijian});

        mListView.setAdapter(madapter);	//捆绑适配器}


    }

}
