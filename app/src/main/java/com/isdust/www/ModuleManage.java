package com.isdust.www;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.isdust.www.Module.BaseModule;
import com.isdust.www.Module.CardModule;
import com.isdust.www.Module.KuaiTongModule;
import com.isdust.www.Module.ManageModule;
import com.isdust.www.Module.jiaowu_ClassroomModule;
import com.isdust.www.Module.jiaowu_MarkModule;
import com.isdust.www.Module.jiaowu_ScheduleModule;
import com.isdust.www.Utils.ConfigHelper;
import com.isdust.www.Utils.SerializableList;

import java.util.List;

public class ModuleManage extends Activity {

    private List<BaseModule>modules = new SerializableList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_module);
    }
    public void add(View view){
        initMoudleData();
        ConfigHelper.saveObject(this,"modules",modules);
    }
    private void initMoudleData(){
        modules.add(CardModule.getInstance());
        modules.add(jiaowu_ClassroomModule.getInstance());
        modules.add(KuaiTongModule.getInstance());
        modules.add(jiaowu_ScheduleModule.getInstance());
        modules.add(jiaowu_MarkModule.getInstance());
        modules.add(ManageModule.getInstance());
    }
}
