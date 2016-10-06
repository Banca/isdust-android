package com.isdust.www;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.isdust.www.Module.BaseModule;
import com.isdust.www.Module.CardModule;
import com.isdust.www.Module.KuaiTongModule;
import com.isdust.www.Module.ManageModule;
import com.isdust.www.Module.WlanModule;
import com.isdust.www.Module.jiaowu_ClassroomModule;
import com.isdust.www.Module.jiaowu_MarkModule;
import com.isdust.www.Module.jiaowu_ScheduleModule;
import com.isdust.www.Module.library_PersonalModule;
import com.isdust.www.Module.library_SearchModule;
import com.isdust.www.RecycleView.CheckboxAdapter;
import com.isdust.www.Utils.ConfigHelper;
import com.isdust.www.Utils.SerializableList;
import com.isdust.www.baseactivity.BaseSubPageActivity;
import com.isdust.www.frame.Main;

import java.util.HashMap;
import java.util.List;

public class ModuleManage extends BaseSubPageActivity {

    private List<BaseModule> modules = new SerializableList<>();
    private ListView listView;
    private MyApplication isdustapp;
    private List<BaseModule> list = new SerializableList<>();
    CheckboxAdapter listItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_manage_module,"模块管理");
        isdustapp = (MyApplication) getApplication();
        init();
        listView = (ListView) findViewById(R.id.listView_mymodule);
        listItemAdapter = new CheckboxAdapter(this, list);
        listView.setAdapter(listItemAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setSelect();
    }

    public void add(View view) {
        setList();
    }

    public void setList() {
        HashMap<Integer, Boolean> state = listItemAdapter.state;
        modules.clear();
        if (state.size() <= 5) {
            for (HashMap.Entry<Integer, Boolean> entry : state.entrySet()) {
                if (entry.getValue() && !modules.contains(list.get(entry.getKey())))
                    modules.add(list.get(entry.getKey()));
                Log.i(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
            modules.add(ManageModule.getInstance());
            isdustapp.setList(modules);
            ConfigHelper.saveObject(this, "modules", modules);
            Main.adapter.setDataList(modules);
            finish();
        } else
            Toast.makeText(this, "至多选择5项", Toast.LENGTH_LONG).show();
    }

    public void init() {
        list.add(CardModule.getInstance());
        list.add(jiaowu_MarkModule.getInstance());
        list.add(jiaowu_ClassroomModule.getInstance());
        list.add(jiaowu_ScheduleModule.getInstance());
        list.add(library_SearchModule.getInstance());
        list.add(library_PersonalModule.getInstance());
        list.add(KuaiTongModule.getInstance());
        list.add(WlanModule.getInstance());
    }

    public void setSelect() {
        List<BaseModule> module = isdustapp.getList();
        for (int i = 0; i < module.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (module.get(i).getName() == list.get(j).getName()) {
                    listItemAdapter.state.put(j, true);
                }
            }
        }
    }
}
