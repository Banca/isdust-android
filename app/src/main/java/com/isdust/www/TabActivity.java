package com.isdust.www;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.isdust.www.Module.BaseModule;
import com.isdust.www.Module.CardModule;
import com.isdust.www.Module.Catagory;
import com.isdust.www.Module.KuaiTongModule;
import com.isdust.www.Module.ManageModule;
import com.isdust.www.Module.WlanModule;
import com.isdust.www.Module.jiaowu_ClassroomModule;
import com.isdust.www.Module.jiaowu_MarkModule;
import com.isdust.www.Module.jiaowu_ScheduleModule;
import com.isdust.www.Module.library_PersonalModule;
import com.isdust.www.Module.library_SearchModule;
import com.isdust.www.Utils.ConfigHelper;
import com.isdust.www.Utils.SerializableList;
import com.isdust.www.frame.About;
import com.isdust.www.frame.Main;
import com.isdust.www.frame.SchoolServer;

import java.util.ArrayList;
import java.util.List;

import pw.isdust.isdust.update.UpdateChecker;

public class TabActivity extends FragmentActivity {

    private RadioGroup navGroup;
    private long exitTime = 0;
    private List<BaseModule> modules = new SerializableList<>();
    private List<Catagory> catagories = new ArrayList<>();
    protected MyApplication isdustapp;


    String tabs[] = {"Tab1", "Tab2", "Tab3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        isdustapp= (MyApplication) getApplication();
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        initMoudleData();
        initSchoolServer();
        UpdateChecker.checkForDialog(this);
    }

    private void initView() {
        navGroup = (RadioGroup) findViewById(R.id.frames);
        RadioButton rbWeiHui = (RadioButton) findViewById(R.id.fram1);
        RadioButton rbAdd = (RadioButton) findViewById(R.id.fram2);
        RadioButton rbMine = (RadioButton) findViewById(R.id.fram3);
        //定义底部标签图片大小
        Drawable drawableWeiHui = getResources().getDrawable(R.drawable.bottom_main);
        drawableWeiHui.setBounds(0, 0, 60, 60);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbWeiHui.setCompoundDrawables(null, drawableWeiHui, null, null);//只放上面

        Drawable drawableAdd = getResources().getDrawable(R.drawable.bottom_service);
        drawableAdd.setBounds(0, 0, 60, 60);
        rbAdd.setCompoundDrawables(null,drawableAdd, null, null);

        Drawable drawableRight = getResources().getDrawable(R.drawable.bottom_about);
        drawableRight.setBounds(0, 0, 60, 60);
        rbMine.setCompoundDrawables(null, drawableRight, null, null);

        navGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fram1:
                        switchFragmentSupport(R.id.content, tabs[0]);
                        break;
                    case R.id.fram2:
                        switchFragmentSupport(R.id.content, tabs[1]);
                        break;
                    case R.id.fram3:
                        switchFragmentSupport(R.id.content, tabs[2]);
                        break;
                }
            }
        });
        RadioButton btn = (RadioButton) navGroup.getChildAt(0);
        btn.toggle();
    }

    private void switchFragmentSupport(int containId, String tag) {
        //获取FramegrameManager管理器
        FragmentManager manager = getFragmentManager();
        //根据标签查找是否已存在对应的frame对象
        Fragment destFrament = manager.findFragmentByTag(tag);
        //如果不存在则初始化
        if (destFrament == null) {
            if (tag.equals(tabs[0])) {
                destFrament = new Main(TabActivity.this);
            }
            if (tag.equals(tabs[1])) {
                destFrament = new SchoolServer(TabActivity.this);
            }
            if (tag.equals(tabs[2])) {
                destFrament = new About(TabActivity.this);
            }
        }
        //获取FramegramentTransaction事务对象
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(containId, destFrament, tag);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 1000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void initMoudleData(){
        modules=(List<BaseModule>) ConfigHelper.readObject(this,"modules");
        if(modules==null){
            modules=new SerializableList<BaseModule>();
        }
        if(modules.size()==0) {
            modules.add(CardModule.getInstance());
            modules.add(jiaowu_ClassroomModule.getInstance());
            modules.add(jiaowu_MarkModule.getInstance());
            modules.add(jiaowu_ScheduleModule.getInstance());
            modules.add(KuaiTongModule.getInstance());
            modules.add(ManageModule.getInstance());
        }
        isdustapp.setList(modules);
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
        catagories.add(jiaowu);
        catagories.add(net);
        catagories.add(card);
        catagories.add(library);
        isdustapp.setCatagorys(catagories);
    }
}
