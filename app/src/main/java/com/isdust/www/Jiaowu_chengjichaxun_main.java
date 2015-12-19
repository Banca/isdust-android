package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.isdust.www.view.IsdustDialog;
import com.umeng.onlineconfig.OnlineConfigAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.SelectCoursePlatform;

/**
 * Created by Wang Ziqiang on 15/12/2.
 * isdust
 Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Jiaowu_chengjichaxun_main extends BaseSubPageActivity_new {
    ListView mListView;


    //按钮
    private Button mButton_chengji_chaxun;


    // 工具栏
    private RelativeLayout mRelativeLayout_xuenian;
    private RelativeLayout mRelativeLayout_xueqi;



    // 左中右三个控件（工具栏里）

    private TextView mTextView_xuenian;
    private TextView mTextView_xueqi;


    // 左中右三个弹出窗口

    private PopupWindow mPopupWindow_xuenian;
    private PopupWindow mPopupWindow_xueqi;


    // 左中右三个layout

    private View mView_xuenian;
    private View mView_xueqi;



    // 左中右三个ListView控件（弹出窗口里）

    private ListView mListView_xuenian;
    private ListView mListView_xueqi;


    private List<Map<String, String>> mList_xuenian;
    private List<Map<String, String>> mList_xueqi;

    private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();	//列表框的数据


    SharedPreferences preferences_data;
    SharedPreferences.Editor preferences_editor;

    IsdustDialog customRuningDialog;
    SelectCoursePlatform mXuankepingtai;

    //线程池
    String xianchengchi_user;
    String xianchengchi_password;
    String xianchengchi_login_status;
    List<String []> xiancheng_list_chengji;
    String xiancheng_xueqi;
    String xiancheng_xuenian;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    SimpleAdapter adapter;	//列表的适配器

    Runnable mRunnable_login=new Runnable(){
        @Override
        public void run() {
            mXuankepingtai=new SelectCoursePlatform(mContext);
            try {
                xianchengchi_login_status=mXuankepingtai.login_zhengfang(xianchengchi_user, xianchengchi_password);
            } catch (IOException e) {
                Message mMessage=new Message();
                mMessage.what = 10;
                mHandler.sendMessage(mMessage);;
                return;
            }
            if(xianchengchi_login_status.contains("登录成功")){

                Message mMessage=new Message();
                mMessage.what=0;
                mHandler.sendMessage(mMessage);
                return;

            }
            Message mMessage=new Message();
            mMessage.what=1;
            mHandler.sendMessage(mMessage);


        }

    };

    final android.os.Handler mHandler=new Handler(){
        @Override
        public void  handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what==0){//登录成功
                customRuningDialog.dismiss();    //打开等待框

//                xianchengchi_ProgressDialog = new ProgressDialog(mContext);
//                xianchengchi_ProgressDialog.setMessage("正在下载课程表到本地");
//                xianchengchi_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                xianchengchi_ProgressDialog.setCancelable(false);
//                xianchengchi_ProgressDialog.show();

//                executorService.execute(mRunnable_download);
                return;
            }
            if (msg.what==1){//登录失败
                customRuningDialog.dismiss();
                preferences_editor.putBoolean("keeppwd", false);
                preferences_editor.putString("password", "");
                preferences_editor.commit();
                //Toast.makeText(mContext,"test",Toast.LENGTH_SHORT).show();

                Toast.makeText(mContext, xianchengchi_login_status, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(mContext, jiaowu_Schedule_login.class);
                startActivityForResult(intent, 1);



            }
            if (msg.what==2){//查询成功
                listdata.clear();//清空列表
                if (xiancheng_list_chengji.size()==0){
                    Toast.makeText(mContext,"没有记录",Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0;i< xiancheng_list_chengji.size();i++){
                    Map<String, Object> map;

                    map = new HashMap<String, Object>();

                    map.put("subject", xiancheng_list_chengji.get(i)[3]);
                    map.put("xuefen",xiancheng_list_chengji.get(i)[6]);
                    map.put("jidian",xiancheng_list_chengji.get(i)[7]);
                    map.put("chengji",xiancheng_list_chengji.get(i)[8]);
                    listdata.add(map);
                }
                adapter = new SimpleAdapter(mContext, listdata,
                        R.layout.activity_jiaowu_chengjichaxun_item, new String[] { "subject", "xuefen", "jidian", "chengji"},
                        new int[] { R.id.textView_chengji_subject, R.id.textView_chengji_xuefen,
                                R.id.textView_chengji_jidian,	R.id.textView_chengji_chengji});
                mListView.setAdapter(adapter);	//捆绑适配器
                customRuningDialog.dismiss();// }




            }
            if (msg.what == 10){//网络超时
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
            }
        }
    };

    Runnable mRunnable_jiazaizixishi=new Runnable(){
        @Override
        public void run() {

            //listdata=null;



            try {
                xiancheng_list_chengji = mXuankepingtai.chengji_chaxun(xiancheng_xuenian, xiancheng_xueqi);//
                Message message=new Message();
                message.what=2;//查询成功
                mHandler.sendMessage(message);
            }catch (Exception e){
                Message message=new Message();
                message.what=10;
                mHandler.sendMessage(message);
            }



        }

    };
    void cha(String xuenian,String xueqi){
        xiancheng_xuenian=xuenian;
        xiancheng_xueqi=xueqi;
        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在查询");
        executorService.execute(mRunnable_jiazaizixishi);

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        INIT(R.layout.activity_jiaowu_chengjichaxun, "成绩查询");
        preferences_data = mContext.getSharedPreferences("ScheduleData", Activity.MODE_PRIVATE);
        preferences_editor = preferences_data.edit();
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);
        initParam();
        mListView=(ListView)findViewById(R.id.listview_emptyroom);
        mButton_chengji_chaxun=(Button)findViewById(R.id.button_chegnji_chaxun);
        mButton_chengji_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mString_xuenian= mTextView_xuenian.getText().toString();
                String mString_xueqi= mTextView_xueqi.getText().toString();

                cha(mString_xuenian,mString_xueqi);
            }
        });

        String user_save=preferences_data.getString("username", "");
        String password_save=preferences_data.getString("password", "");
        if (user_save.equals("") || password_save.equals("")){

            Intent intent = new Intent();
            intent.setClass(mContext, jiaowu_Schedule_login.class);
            startActivityForResult(intent, 1);
            return;
        }else{
            xianchengchi_user=user_save;
            xianchengchi_password=password_save;}

        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在登录");
        executorService.execute(mRunnable_login);
    }
    private void initParam() {
        //初始化xuenian
        mRelativeLayout_xuenian = (RelativeLayout) this.findViewById(R.id.relativeLayout_xuenian);
        mTextView_xuenian= (TextView) this.findViewById(R.id.textView_chengji_xuenian);
        mTextView_xuenian.setOnClickListener(myListener);
        //初始化xueqi
        mRelativeLayout_xueqi= (RelativeLayout) this.findViewById(R.id.relativeLayout_xueqi);
        mTextView_xueqi = (TextView) this.findViewById(R.id.textView_chengji_xueqi);
        mTextView_xueqi.setOnClickListener(myListener);




        // 初始化xuenian数据项
        String xuenian_raw = OnlineConfigAgent.getInstance().getConfigParams(mContext, "jiaowu_chengji_xuenian");
        String [] xuenian;
        xuenian=xuenian_raw.split("\n");
        mList_xuenian = new ArrayList<Map<String, String>>();
        for (int i = 1; i < xuenian.length;i++) {
            HashMap<String, String> mapTemp = new HashMap<String, String>();
            mapTemp.put("xuenian", xuenian[i]);
            mList_xuenian.add(mapTemp);
        }
        mTextView_xuenian.setText(xuenian[1]);


        // 初始化xueqi数据项
        String xueqi_raw = OnlineConfigAgent.getInstance().getConfigParams(mContext, "jiaowu_chengji_xueqi");
        String [] xueqi;
        xueqi=xueqi_raw.split("\n");
        mList_xueqi = new ArrayList<Map<String, String>>();
        for (int i = 1; i < xueqi.length;i++) {
            HashMap<String, String> mapTemp = new HashMap<String, String>();
            mapTemp.put("xueqi", xueqi[i]);
            mList_xueqi.add(mapTemp);
        }
        mTextView_xueqi.setText(xueqi[1]);


    }
    private View.OnClickListener myListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textView_chengji_xuenian:
                    if (mPopupWindow_xuenian != null && mPopupWindow_xuenian.isShowing()) {
                        mPopupWindow_xuenian.dismiss();
                    } else {
                        mView_xuenian = getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        mListView_xuenian = (ListView) mView_xuenian.findViewById(R.id.menulist);
                        SimpleAdapter listAdapter = new SimpleAdapter(
                                mContext, mList_xuenian, R.layout.pop_menuitem,
                                new String[] { "xuenian" },
                                new int[] { R.id.menuitem });
                        mListView_xuenian.setAdapter(listAdapter);

                        // 点击listview中item的处理
                        mListView_xuenian
                                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> arg0,
                                                            View arg1, int arg2, long arg3) {
                                        String strItem = mList_xuenian.get(arg2).get(
                                                "xuenian");

//                                        bangding(strItem);
                                        mTextView_xuenian.setText(strItem);

                                        if (mPopupWindow_xuenian != null && mPopupWindow_xuenian.isShowing()) {
                                            mPopupWindow_xuenian.dismiss();
                                        }
                                    }
                                });

                        mPopupWindow_xuenian = new PopupWindow(mView_xuenian, mTextView_xuenian.getWidth(),
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                        ColorDrawable cd = new ColorDrawable(-0000);
                        mPopupWindow_xuenian.setBackgroundDrawable(cd);
                        mPopupWindow_xuenian.setAnimationStyle(R.style.PopupAnimation);
                        mPopupWindow_xuenian.update();
                        mPopupWindow_xuenian.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        mPopupWindow_xuenian.setTouchable(true); // 设置popupwindow可点击
                        mPopupWindow_xuenian.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        mPopupWindow_xuenian.setFocusable(true); // 获取焦点

                        // 设置popupwindow的位置
                        int topBarHeight = mRelativeLayout_xuenian.getBottom();
                        mPopupWindow_xuenian.showAsDropDown(mTextView_xuenian, 0,
                                0);

                        mPopupWindow_xuenian.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    mPopupWindow_xuenian.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });
                    }
                    break;

                case R.id.textView_chengji_xueqi:
                    if (mPopupWindow_xueqi != null && mPopupWindow_xueqi.isShowing()) {
                        mPopupWindow_xueqi.dismiss();
                    } else {
                        mView_xueqi = getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        mListView_xueqi = (ListView) mView_xueqi.findViewById(R.id.menulist);
                        SimpleAdapter listAdapter = new SimpleAdapter(
                                mContext, mList_xueqi, R.layout.pop_menuitem,
                                new String[] { "xueqi" },
                                new int[] { R.id.menuitem });
                        mListView_xueqi.setAdapter(listAdapter);

                        // 点击listview中item的处理
                        mListView_xueqi
                                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> arg0,
                                                            View arg1, int arg2, long arg3) {
                                        String strItem = mList_xueqi.get(arg2).get(
                                                "xueqi");

//                                        bangding(strItem);
                                        mTextView_xueqi.setText(strItem);

                                        if (mPopupWindow_xueqi != null && mPopupWindow_xueqi.isShowing()) {
                                            mPopupWindow_xueqi.dismiss();
                                        }
                                    }
                                });

                        mPopupWindow_xueqi = new PopupWindow(mView_xueqi, mTextView_xueqi.getWidth(),
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                        ColorDrawable cd = new ColorDrawable(-0000);
                        mPopupWindow_xueqi.setBackgroundDrawable(cd);
                        mPopupWindow_xueqi.setAnimationStyle(R.style.PopupAnimation);
                        mPopupWindow_xueqi.update();
                        mPopupWindow_xueqi.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        mPopupWindow_xueqi.setTouchable(true); // 设置popupwindow可点击
                        mPopupWindow_xueqi.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        mPopupWindow_xueqi.setFocusable(true); // 获取焦点

                        // 设置popupwindow的位置
                        int topBarHeight = mRelativeLayout_xueqi.getBottom();
                        mPopupWindow_xueqi.showAsDropDown(mTextView_xueqi, 0,
                                0);

                        mPopupWindow_xueqi.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    mPopupWindow_xueqi.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });
                    }
                    break;



                default:
                    break;
            }
        }

    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    xianchengchi_user=bundle.getString("username");
                    xianchengchi_password=bundle.getString("password");
                    customRuningDialog.show();    //打开等待框
                    customRuningDialog.setMessage("正在登录");
                    executorService.execute(mRunnable_login);
                    break;
                }
                if (resultCode == RESULT_CANCELED) {
                    finish();
                    break;

                }
                break;
        }
    }
}
