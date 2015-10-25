package com.formal.sdusthelper;

import android.app.Activity;
import android.content.Intent;
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
                form_cmcc.GoFirstNet();
                form_cmcc.GoSecNet();
                break;
            case R.id.btn_cmcc_quicklogout:  //一键登录下线

                break;
            case R.id.btn_cmcc_changepwd:  //改密
                form_cmcc.changePwd();
                break;
            case R.id.btn_cmcc_query:  //使用情况查询
                (new Networklogin_CMCC()).cmcc_query();
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
        View cmcc = form_cmcc.Init();
        if (form_cmcc.haveEmptyData()) {
            Intent intent=new Intent();
            intent.setClass(this, GoNetCMCCAcntActivity.class);
            startActivityForResult(intent, 1);  //获取返回值方式启动
        }

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
//        mEdit.setVisibility(View.VISIBLE);
        return mMessage;
    }

    public void setOnOpenListener(FlipperLayout.OnOpenListener onOpenListener) {
        mOnOpenListener = onOpenListener;
    }
}
