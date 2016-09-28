package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isdust.www.Card.CardActivity;
import com.isdust.www.Card.CardModule;
import com.isdust.www.KuaiTong.KuaiTongModule;
import com.isdust.www.RecycleView.RecycleViewAdapter;
import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.isdust.www.baseactivity.BaseModule;

import java.util.ArrayList;
import java.util.List;

public class SchoolServerActivity extends BaseMainActivity_new {

    private CardModule cardModule;
    private RecyclerView rcv;
    GridLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.act2,"校园服务",0);
       // setContentView(R.layout.activity_recycle);
        rcv = (RecyclerView) findViewById(R.id.rcv);
        manager = new GridLayoutManager(this, 1);
        // 设置布局管理一条数据占用几行，如果是头布局则头布局自己占用一行
      /*  manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int postion) {
                if (postion == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });*/
        rcv.setLayoutManager(manager);
        List<BaseModule>list=new ArrayList<>();
        list.add(CardModule.getInstance());
        list.add(KuaiTongModule.getInstance());

        RecycleViewAdapter adapter = new RecycleViewAdapter(this,list);


        rcv.setAdapter(adapter);











//
//        cardModule = CardModule.getInstance();
//        ImageView imageView = (ImageView)findViewById(R.id.imageView);
//        imageView.setBackgroundResource(cardModule.getImage_id());
//        TextView textView = (TextView)findViewById(R.id.Sc_text_name);
//        TextView textView2 = (TextView)findViewById(R.id.Sc_text_info);
//        textView.setText(cardModule.getName());
//        textView2.setText(cardModule.getDesc());

    }
}
