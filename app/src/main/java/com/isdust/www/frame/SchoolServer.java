package com.isdust.www.frame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.isdust.www.Module.CardModule;
import com.isdust.www.Module.Catagory;
import com.isdust.www.Module.KuaiTongModule;
import com.isdust.www.Module.WlanModule;
import com.isdust.www.Module.jiaowu_ClassroomModule;
import com.isdust.www.Module.jiaowu_MarkModule;
import com.isdust.www.Module.jiaowu_ScheduleModule;
import com.isdust.www.Module.library_PersonalModule;
import com.isdust.www.Module.library_SearchModule;
import com.isdust.www.MyApplication;
import com.isdust.www.R;
import com.isdust.www.RecycleView.MyAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SchoolServer extends Fragment {

    private CardModule cardModule;
    private ExpandableListView rcv;
    GridLayoutManager manager;
    List<Catagory>list = new ArrayList<>();
    View v;
    MyAdapter listAdapter;
    protected MyApplication isdustapp;
    private Activity thisActivity;

    public SchoolServer(Activity activity){
        thisActivity = activity;
        isdustapp=new MyApplication();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act2, null);
        TextView textView = (TextView)v.findViewById(R.id.title_bar_name);
        textView.setText("校园服务");
        rcv = (ExpandableListView) v.findViewById(R.id.list);
        initSchoolServer();
        listAdapter = new MyAdapter(thisActivity, list);
        rcv.setGroupIndicator(null);
        rcv.setDividerHeight(0);
        rcv.setAdapter(listAdapter);
        expandAll();
        return v;
    }
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            rcv.expandGroup(i);
        }
    }
    private void initSchoolServer(){
        Catagory card = new Catagory(R.string.schoolcard_catgory);
        card.addItem(CardModule.getInstance());

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


        list.add(card);
        list.add(jiaowu);
        list.add(library);
        list.add(net);
    }

}
