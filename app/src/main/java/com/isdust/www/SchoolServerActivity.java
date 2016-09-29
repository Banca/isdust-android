package com.isdust.www;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ExpandableListView;

import com.isdust.www.Module.*;

import com.isdust.www.RecycleView.MyAdapter;
import com.isdust.www.baseactivity.BaseMainActivity_new;




import java.util.ArrayList;
import java.util.List;

public class SchoolServerActivity extends BaseMainActivity_new {

    private CardModule cardModule;
    private ExpandableListView rcv;
    GridLayoutManager manager;
    MyAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.act2, "校园服务", 0);
        // setContentView(R.layout.activity_recycle);
        rcv = (ExpandableListView) findViewById(R.id.list);
//        manager = new GridLayoutManager(this, 1);
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
//        rcv.setLayoutManager(manager);


        Catagory card = new Catagory(R.string.schoolcard_catgory);
        card.addItem(cardModule.getInstance());

        Catagory jiaowu = new Catagory(R.string.Jiaowu_catgory);
        jiaowu.addItem(jiaowu_MarkModule.getInstance());
        jiaowu.addItem(jiaowu_ClassroomModule.getInstance());
        jiaowu.addItem(jiaowu_ScheduleModule.getInstance());

        Catagory library = new Catagory(R.string.library_catagory);
        library.addItem(library_SearchModule.getInstance());
        library.addItem(library_PersonalModule.getInstance());

        Catagory net = new Catagory(R.string.net_catgory);
        net.addItem(KuaiTongModule.getInstance());
        net.addItem(WlanModule.getInstance());

        List<Catagory> list = new ArrayList<>();
        list.add(card);
        list.add(jiaowu);
        list.add(library);
        list.add(net);
        listAdapter = new MyAdapter(this, list);
        rcv.setAdapter(listAdapter);
        expandAll();

//        RecycleViewAdapter adapter = new RecycleViewAdapter(this, list);
//
//
//        rcv.setAdapter(adapter);


//
//        cardModule = CardModule.getInstance();
//        ImageView imageView = (ImageView)findViewById(R.id.imageView);
//        imageView.setBackgroundResource(cardModule.getImage_id());
//        TextView textView = (TextView)findViewById(R.id.Sc_text_name);
//        TextView textView2 = (TextView)findViewById(R.id.Sc_text_info);
//        textView.setText(cardModule.getName());
//        textView2.setText(cardModule.getDesc());

    }
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            rcv.expandGroup(i);
        }
    }

}
