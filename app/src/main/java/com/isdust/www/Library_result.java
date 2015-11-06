package com.isdust.www;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isdust.www.baseactivity.BaseSubListPageActivity;
import com.isdust.www.datatype.Book;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzq on 15/11/1.
 */
public class Library_result extends BaseSubListPageActivity {


    Context mContext;
    List<Book> mBooks;
    SimpleAdapter madapter;
    private MyApplication isdustapp;
    private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(this, "Library_guancang");
        INIT(R.layout.activity_library_result,"查询结果");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_library_result);
        mContext=this;
        isdustapp=(MyApplication)getApplication();

        mBooks=isdustapp.getBooks();

        jiazaishuju();
    }
    public void jiazaishuju(){
        Map<String, Object> map;
        int len=mBooks.size();
        for (int i=0;i<len;i++){
            map = new HashMap<String, Object>();
            map.put("title",mBooks.get(i).getName());
            map.put("author","作者："+mBooks.get(i).getWriter());
            map.put("bookrecnos","书本编号："+mBooks.get(i).getbookrecno());
            map.put("suoshuhao","索书号："+mBooks.get(i).getSuoshuhao());
            listdata.add(map);

        }

        madapter = new SimpleAdapter(mContext, listdata,
                R.layout.activity_library_result_item, new String[] { "title", "author", "bookrecnos", "suoshuhao"},
                new int[] { R.id.TextView_library_title, R.id.TextView_library_author,
                        R.id.TextView_library_bookrecnos,	R.id.TextView_library_suoshuhao});
        setListAdapter(madapter);	//捆绑适配器}

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        Toast.makeText(this, "positon = " + position, Toast.LENGTH_SHORT).show();
        l.getLastVisiblePosition();
        l.getFirstVisiblePosition();
        try {
//            l.getChildAt(position).findViewById(R.id.TextView_library_bookrecnos);
            TextView mTextView_library_bookrecnos=(TextView)v.findViewById(R.id.TextView_library_bookrecnos);
            String bookrecnos=mTextView_library_bookrecnos.getText().toString().replace("书本编号：","");
            Intent intent=new Intent();
            intent.putExtra("bookrecnos", bookrecnos);
            intent.setClass(mContext, Library_detail.class);
            startActivity(intent);
        }catch (Exception e){
            System.out.println(e);
        }



    }
}
