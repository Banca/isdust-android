package com.formal.sdusthelper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;
import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;
import com.formal.sdusthelper.view.FlipperLayout;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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

import pw.isdust.isdust.function.Networklogin_CMCC;

/**
 * Created by Administrator on 2015/10/17.
 */
public class GoNetActivity extends BaseSubPageActivity {
    private View mMessage;

    private ImageView mFlip;
    private Button mEdit;
    private ViewPager mPager;
    private RadioGroup mTab;
    private RadioButton mMessageButton;
    private RadioButton mFriendButton;
    private RadioButton mBirthDayButton;
    private EditText text_cmcc_user_first,text_cmcc_user_sec,
            text_cmcc_pwd_first,text_cmcc_pwd_sec;
    private Button btn_login_first,btn_login_sec;
    private CheckBox check_cmcc_first,check_cmcc_sec;

    private ViewPagerAdapter mAdapter;
    private FlipperLayout.OnOpenListener mOnOpenListener;
    private List<View> mList = new ArrayList<View>();

    private Networklogin_CMCC obj_cmcc;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        INIT(R.layout.helper_gonet, "上网登录");
        findView();
        setListener();
        init();
        obj_cmcc = new Networklogin_CMCC(); //实例化cmcc
	}

    public void onFormCMCCClick(View v) {
        String result,str_user,str_pwd;

        //实例化SharedPreferences对象
        SharedPreferences mySharedPreferences= getSharedPreferences("CMCCData",	Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        switch (v.getId()) {
            case R.id.cmcc_first_button_login:  //登陆一层账号
                str_user = text_cmcc_user_first.getText().toString();
                str_pwd = text_cmcc_pwd_first.getText().toString();
                //用putString的方法保存数据
                editor.putString("username_first", str_user);
                if (check_cmcc_first.isChecked()) {	//记住密码
                    editor.putBoolean("keeppwd_first", true);
                    editor.putString("password_first", str_pwd);
                }
                else {	//不记住密码
                    editor.putBoolean("keeppwd_first", false);
                    editor.putString("password_first", "");
                }
                //提交当前数据
                editor.commit();
                result = obj_cmcc.login(str_user,str_pwd);  //登陆一层
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
//                if (result.equals("登陆成功"))
//                    obj_cmcc.cmcc_init();   //为登陆二层做准备
                break;
            case R.id.cmcc_sec_button_login:
                str_user = text_cmcc_user_sec.getText().toString();
                str_pwd = text_cmcc_pwd_sec.getText().toString();
                //用putString的方法保存数据
                editor.putString("username_sec", text_cmcc_user_sec.getText().toString());
                if (check_cmcc_sec.isChecked()) {	//记住密码
                    editor.putBoolean("keeppwd_sec", true);
                    editor.putString("password_sec", text_cmcc_pwd_sec.getText().toString());
                }
                else {	//不记住密码
                    editor.putBoolean("keeppwd_sec", false);
                    editor.putString("password_sec", "");
                }
                //提交当前数据
                editor.commit();
                obj_cmcc.cmcc_init();   //为登陆二层做准备
                result = obj_cmcc.cmcc_login(str_user, str_pwd);  //登陆二层
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void findView() {
//        mFlip = (ImageView) this.findViewById(R.id.title_bar_menu_btn);
//        mEdit = (Button) this.findViewById(R.id.message_edit);
        mPager = (ViewPager) this.findViewById(R.id.message_pager);
        mTab = (RadioGroup) this.findViewById(R.id.message_radiogroup);
        mMessageButton = (RadioButton) this.findViewById(R.id.tab_cmcc);
        mFriendButton = (RadioButton) this.findViewById(R.id.tab_chinaunicom);
        mBirthDayButton = (RadioButton) this.findViewById(R.id.tab_kuaitong);
    }
    private void setListener() {
//        mFlip.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//                if (mOnOpenListener != null) {
//                    mOnOpenListener.open();
//                }
//            }
//        });
//        mEdit.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//                Toast.makeText(mContext, "test", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            public void onPageSelected(int arg0) {

            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
                    switch (mPager.getCurrentItem()) {
                        case 0:
//                            mEdit.setVisibility(View.VISIBLE);
                            mMessageButton.setChecked(true);
                            break;

                        case 1:
//                            mEdit.setVisibility(View.VISIBLE);
                            mFriendButton.setChecked(true);
                            break;

                        case 2:
                            mBirthDayButton.setChecked(true);
//                            mEdit.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
        mTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_cmcc:
                        mPager.setCurrentItem(0);
//                        mEdit.setVisibility(View.VISIBLE);
                        break;

                    case R.id.tab_chinaunicom:
                        mPager.setCurrentItem(1);
//                        mEdit.setVisibility(View.VISIBLE);
                        break;

                    case R.id.tab_kuaitong:
                        mPager.setCurrentItem(2);
//                        mEdit.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void init() {
        //初始化CMCC页面
        View cmcc = LayoutInflater.from(mContext).inflate(
                R.layout.gonet_cmcc, null);
        text_cmcc_user_first = (EditText) cmcc.findViewById(R.id.cmcc_first_editText_user);
        text_cmcc_user_sec = (EditText) cmcc.findViewById(R.id.cmcc_sec_editText_user);
        text_cmcc_pwd_first = (EditText) cmcc.findViewById(R.id.cmcc_first_editText_pwd);
        text_cmcc_pwd_sec = (EditText) cmcc.findViewById(R.id.cmcc_sec_editText_pwd);
        btn_login_first = (Button) cmcc.findViewById(R.id.cmcc_first_button_login);
        btn_login_sec = (Button) cmcc.findViewById(R.id.cmcc_sec_button_login);
        check_cmcc_first = (CheckBox) cmcc.findViewById(R.id.cmcc_first_checkBox_savepwd);
        check_cmcc_sec = (CheckBox) cmcc.findViewById(R.id.cmcc_sec_checkBox_savepwd);
        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferencesCMCC= getSharedPreferences("CMCCData", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String name =sharedPreferencesCMCC.getString("username_first", "");
        String pwd =sharedPreferencesCMCC.getString("password_first", "");
        Boolean keeppwd = sharedPreferencesCMCC.getBoolean("keeppwd_first", true);
        text_cmcc_user_first.setText(name);
        text_cmcc_pwd_first.setText(pwd);
        check_cmcc_first.setChecked(keeppwd);
        name =sharedPreferencesCMCC.getString("username_sec", "");
        pwd =sharedPreferencesCMCC.getString("password_sec", "");
        keeppwd = sharedPreferencesCMCC.getBoolean("keeppwd_sec", true);
        text_cmcc_user_sec.setText(name);
        text_cmcc_pwd_sec.setText(pwd);
        check_cmcc_sec.setChecked(keeppwd);
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
        mPager.setCurrentItem(0);
    }

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
//        mEdit.setVisibility(View.VISIBLE);
        return mMessage;
    }

    public void setOnOpenListener(FlipperLayout.OnOpenListener onOpenListener) {
        mOnOpenListener = onOpenListener;
    }
}
