package com.isdust.www;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.frame.About;
import com.isdust.www.frame.Main;
import com.isdust.www.frame.SchoolServer;

public class TabActivity extends FragmentActivity {

    private RadioGroup navGroup;
    private long exitTime = 0;
    String tabs[] = {"Tab1", "Tab2", "Tab3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();
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


}