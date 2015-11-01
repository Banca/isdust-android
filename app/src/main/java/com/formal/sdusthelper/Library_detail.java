package com.formal.sdusthelper;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pw.isdust.isdust.function.Library;

/**
 * Created by wzq on 15/11/1.
 */
public class Library_detail extends ListActivity  {
    Library mLibrary;
    List<String[]> mguancang;
    SimpleAdapter madapter;
    Context mContext;
    private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_library_detail);
            mContext=this;
            TextView title_name = (TextView) findViewById(R.id.title_bar_name);
            title_name.setText("馆藏信息");	//修改页面标题
            mLibrary=new Library();
            String bookrecnos=getIntent().getExtras().getString("bookrecnos");
            mguancang =mLibrary.getguancang(bookrecnos);
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
                R.layout.activity_library_detail_item, new String[] { "suoshuhao", "tiaomahao", "guancangzhuangtai", "guancangdi","huanshushijian"},
                new int[] { R.id.textView_library_detail_suoshuhao, R.id.textView_library_detail_tiaomahao,
                        R.id.textView_library_detail_guancangzhuangtai,	R.id.textView_library_detail_guancangdi,R.id.textView_library_detail_huanshushijian});
        setListAdapter(madapter);	//捆绑适配器}

//        madapter = new SimpleAdapter(mContext, listdata,
//                R.layout.activity_library_result_item, new String[] { "title", "author", "bookrecnos", "suoshuhao"},
//                new int[] { R.id.TextView_library_title, R.id.TextView_library_author,
//                        R.id.TextView_library_bookrecnos,	R.id.TextView_library_suoshuhao});
//        setListAdapter(madapter);	//捆绑适配器}

    }
}
