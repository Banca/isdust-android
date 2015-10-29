package com.formal.sdusthelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;
import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;
import com.formal.sdusthelper.view.FlipperLayout;
import com.formal.sdusthelper.view.IsdustDialog;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

import android.content.Context;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import pw.isdust.isdust.function.Networkjudge;
import pw.isdust.isdust.function.Networklogin_CMCC;

/**
 * Created by Administrator on 2015/10/17.
 */
public class GoNetActivity extends BaseSubPageActivity {
    public android.os.Handler mHandler = null;
    public final static int RESULT_CODE=1;
    private View mMessage;

    private ImageView mFlip;
    private Button mEdit;
    private ViewPager mPager;
    private RadioGroup mTab;
    private RadioButton mMessageButton;
    private RadioButton mFriendButton;
    private RadioButton mBirthDayButton;

    private GoNetCMCCActivity form_cmcc;

    private ViewPagerAdapter mAdapter;
    private FlipperLayout.OnOpenListener mOnOpenListener;
    private List<View> mList = new ArrayList<View>();

    private Thread threadLogin; //用于一键登录的线程
    private Thread threadJudgeNet; //用于判断网络的线程
    private IsdustDialog customRuningDialog;  //自定义运行中提示框
    private Networkjudge obj_netstate;  //用于网络判断的对象

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        INIT(R.layout.helper_gonet, "上网登录");
        form_cmcc = new GoNetCMCCActivity(mContext);
        findView();
        setListener();
        init();
	}

    public void onFormCMCCClick(View v) {

        switch (v.getId()) {
            case R.id.btn_cmcc_state:   //点击状态按钮设置用户名密码
                Intent intent=new Intent();
                intent.setClass(this, GoNetCMCCAcntActivity.class);
                startActivityForResult(intent, GoNetCMCCAcntActivity.RESULT_CODE);
                break;
            case R.id.btn_cmcc_quicklogin:  //一键登录
                customRuningDialog.show();    //打开等待框
                initThreadLogin();  //初始化进程
                threadLogin.start();    //打开登录进程
                break;
            case R.id.btn_cmcc_quicklogout:  //一键登录下线
//                customDialog = new IsdustDialog(this,
//                        IsdustDialog.PROGRESSBAR_DIALOG, R.style.DialogTheme);
//                customDialog.show();
//                customDialog.setProgress(50);
//                customDialog.hide();
                break;
            case R.id.btn_cmcc_changepwd:  //改密
//                form_cmcc.changePwd();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final EditText password = new EditText(this);
                password.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(password);
                builder.setMessage("您要将密码设置为：");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        form_cmcc.changePwd(password.getText().toString());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            case R.id.btn_cmcc_query:  //使用情况查询
                (new Networklogin_CMCC()).cmcc_query();
                break;
        }
    }

    private void findView() {
        mPager = (ViewPager) this.findViewById(R.id.message_pager);
        mTab = (RadioGroup) this.findViewById(R.id.message_radiogroup);
        mMessageButton = (RadioButton) this.findViewById(R.id.tab_cmcc);
        mFriendButton = (RadioButton) this.findViewById(R.id.tab_chinaunicom);
        mBirthDayButton = (RadioButton) this.findViewById(R.id.tab_kuaitong);
    }
    private void setListener() {
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            public void onPageSelected(int arg0) {

            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
                    switch (mPager.getCurrentItem()) {
                        case 0:
                            mMessageButton.setChecked(true);
                            break;

                        case 1:
                            mFriendButton.setChecked(true);
                            break;

                        case 2:
                            mBirthDayButton.setChecked(true);
                            break;
                    }
                }
            }
        });
        mTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.tab_cmcc:
                        mPager.setCurrentItem(0);   // 设置当前页面
                        Toast.makeText(GoNetActivity.this,"0", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.tab_chinaunicom:
                        mPager.setCurrentItem(1);
                        Toast.makeText(GoNetActivity.this,"1", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.tab_kuaitong:
                        mPager.setCurrentItem(2);
                        Toast.makeText(GoNetActivity.this,"2", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    private void init() {
        //初始化CMCC页面
        View cmcc = form_cmcc.Init();
        //ChinaUnicom初始化
        View chinaunicom = LayoutInflater.from(mContext).inflate(
                R.layout.gonet_chinaunicom, null);
        View kuaitong = LayoutInflater.from(mContext).inflate(
                R.layout.gonet_kuaitong, null);
        mList.add(cmcc);
        mList.add(chinaunicom);
        mList.add(kuaitong);
        mAdapter = new ViewPagerAdapter();
        mPager.setAdapter(mAdapter);

        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);
        customRuningDialog.show();    //打开等待框

        initTreadJudgeNet();  //初始化进程
        threadJudgeNet.start();    //打开网络判断线程


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的
        if (resultCode==GoNetCMCCAcntActivity.RESULT_CODE) //cmcc
        {
            Bundle bundle=data.getExtras();
            form_cmcc.setData(bundle);
        }
    }   //处理子页面返回的数据

    private class ViewPagerAdapter extends PagerAdapter {

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mList.get(arg1));
        }

        public void finishUpdate(View arg0) {

        }

        public int getCount() {
            return mList.size();
        }

        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mList.get(arg1));
            return mList.get(arg1);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        public Parcelable saveState() {
            return null;
        }

        public void startUpdate(View arg0) {

        }

    }

    public View getView() {
        mPager.setCurrentItem(0);
        mMessageButton.setChecked(true);
        return mMessage;
    }

    public void setOnOpenListener(FlipperLayout.OnOpenListener onOpenListener) {
        mOnOpenListener = onOpenListener;
    }

    private void initTreadJudgeNet() {
        obj_netstate = new Networkjudge(this);
        threadJudgeNet = new Thread(new Runnable() {
            @Override
            public void run() {
                int state = obj_netstate.judgetype();   //分析ssid
                if (state == 1) {
                    final int cmcc_result = obj_netstate.cmcc_judge();
                    GoNetActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPager.setCurrentItem(0);   //tab切换到CMCC
                            if (cmcc_result == 2){

                            }   //设置状态栏显示已登录
                            else {
                                if (form_cmcc.haveEmptyData()) {
                                    Intent intent=new Intent();
                                    intent.setClass(GoNetActivity.this, GoNetCMCCAcntActivity.class);
                                    startActivityForResult(intent, 1);  //获取返回值方式启动
                                }   //未连接，并且账号不全
                            }
                            customRuningDialog.hide();
                        }
                    }); //在UI线程的操作
                }   //CMCC
                else if (state == 2) {
                    final int uni_result = obj_netstate.chinaunicom_judge();
                    GoNetActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPager.setCurrentItem(1);   //tab切换到china unicon
                            if (uni_result == 2){

                            }   //设置状态栏显示已登录
                            customRuningDialog.hide();
                        }
                    }); //在UI线程的操作
                }   //china Unicom
                else {
                    GoNetActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPager.setCurrentItem(2);   //tab切换到快通
                            customRuningDialog.hide();
                        }
                    }); //在UI线程的操作
                }
            }
        });
    }   //判断当前网络及界面切换

    private void initThreadLogin() {
        threadLogin = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result;
                GoNetActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customRuningDialog.setMessage("正在登录一层账号");
                    }
                });
                result = form_cmcc.GoFirstNet();
                if (result) { //一层登录成功
                    GoNetActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customRuningDialog.setMessage("正在登录二层账号");
                        }
                    });
                    result = form_cmcc.GoSecNet();
                    if (result) {   //二层登录成功
                        GoNetActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                customRuningDialog.hide();
                                Toast.makeText(GoNetActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }

                GoNetActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customRuningDialog.hide();
                        Toast.makeText(GoNetActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });     //初始化登录线程
    }   //快捷登录CMCC

}
