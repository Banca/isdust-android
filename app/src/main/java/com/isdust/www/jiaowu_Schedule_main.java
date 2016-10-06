package com.isdust.www;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
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

import com.isdust.www.baseactivity.BaseSubPageActivity;
import com.isdust.www.datatype.Kebiao;
import com.isdust.www.view.IsdustDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.OnlineConfig;
import pw.isdust.isdust.function.ScheduleDB;
import pw.isdust.isdust.function.SchoolDate;
import pw.isdust.isdust.function.SelectCoursePlatform;

/**
 * Created by Wang Ziqiang on 2015/10/16.
 * isdust
 * Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

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
public class jiaowu_Schedule_main extends BaseSubPageActivity {
    int zhoushu;
    public ScheduleDB mScheduleDB;


    SharedPreferences preferences_data;
    SharedPreferences.Editor preferences_editor;
    //实例化SharedPreferences对象




    List<Object> mTextView=new ArrayList<Object>();
    SelectCoursePlatform mXuankepingtai;

    Button mButton_update;
    Button mButton_logout;
    Button mButton_add;

    // 工具栏
    private RelativeLayout rlTopBar;

    private TextView mTextView_zhoushu;
    private PopupWindow mPopupWindow_zhoushu;
    private View mView_zhoushu;

    // 左中右三个ListView控件（弹出窗口里）

    private ListView menulistMiddle;

    private List<Map<String, String>> mList_zhoushu;


    Context mContext;
    /** 第一个无内容的格子 */
    protected TextView empty;
    /** 星期一的格子 */
    protected TextView monColum;
    /** 星期二的格子 */
    protected TextView tueColum;
    /** 星期三的格子 */
    protected TextView wedColum;
    /** 星期四的格子 */
    protected TextView thrusColum;
    /** 星期五的格子 */
    protected TextView friColum;
    /** 星期六的格子 */
    protected TextView satColum;
    /** 星期日的格子 */
    protected TextView sunColum;
    /** 课程表body部分布局 */
    protected RelativeLayout course_table_layout;
    /** 屏幕宽度 **/
    protected int screenWidth;
    /** 课程格子平均宽度 **/
    protected int aveWidth;
    DisplayMetrics dm;

     IsdustDialog customRuningDialog;

    //线程池
    String xianchengchi_user;
    String xianchengchi_password;
    String xianchengchi_login_status;
    double xianchengchi_percent;
    //String xianchengchi_saving_json;
    ProgressDialog xianchengchi_ProgressDialog;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    //Kebiao [] mKebiao_all;

    Runnable mRunnable_login=new Runnable(){
        @Override
        public void run() {
            try {
                mXuankepingtai=new SelectCoursePlatform(mContext);
            } catch (Exception e) {
                Message mMessage=new Message();
                mMessage.what = 11;
                mHandler.sendMessage(mMessage);
                return;
            }
            try {
                xianchengchi_login_status=mXuankepingtai.login_zhengfang(xianchengchi_user, xianchengchi_password);
            } catch (IOException e) {
                Message mMessage=new Message();
                mMessage.what = 10;
                mHandler.sendMessage(mMessage);;
                return;
            }
            if(xianchengchi_login_status.contains("登录成功")){
                try {
                    mXuankepingtai.zhengfang_tiaozhuan_xuankepingtai();
                } catch (IOException e) {
                    Message mMessage=new Message();
                    mMessage.what = 10;
                    mHandler.sendMessage(mMessage);;
                    return;
                }
                Message mMessage=new Message();
                mMessage.what=0;
                mHandler.sendMessage(mMessage);
                return;

                //下载课程表
            }
            Message mMessage=new Message();
            mMessage.what=1;
            mHandler.sendMessage(mMessage);


        }

    };
    Runnable mRunnable_download_zhengfang=new Runnable() {
        @Override
        public void run() {

            Message mMessage=new Message();
            mMessage.what=3;

            try{
                mXuankepingtai.kebiao_chaxun_zhengfang();
            }catch (Exception e){
                mMessage.what = 10;
                mHandler.sendMessage(mMessage);;
                return;
            }
            xianchengchi_ProgressDialog.setProgress(1);


            mHandler.sendMessage(mMessage);
            return;
        }
    };
    Runnable mRunnable_download_xuanke=new Runnable() {
        @Override
        public void run() {
            int zhoushu=22;
            //xianchengchi_saving_json="";
            Message mMessage=new Message();
            mMessage.what=3;
//            OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
            String xuenian= OnlineConfig.getConfigParams("schedule_xuenian");
            String xueqi= OnlineConfig.getConfigParams("schedule_xueqi");
//            xuenian="2015-2016";xueqi="1";//debug
            for(int i=0;i<zhoushu;i++){
                try {
//                    db.execSQL("INSERT INTO person VALUES (NULL, ?, ?,?,?)", new Object[]{person.name, person.age});
                    mScheduleDB.add(mXuankepingtai.kebiao_chaxun((i + 1) + "", xuenian, xueqi));
                    //sql_import();
//                    xianchengchi_saving_json=xianchengchi_saving_json+mXuankepingtai.scheduletojson(mXuankepingtai.kebiao_chaxun((i + 1) + "", xuenian, xueqi));
                } catch (Exception e) {
                    mMessage.what = 10;
                    mHandler.sendMessage(mMessage);;
                    return;
                }
                xianchengchi_percent=((double)(i+1)/(double)zhoushu)*100;
                xianchengchi_ProgressDialog.setProgress((int)xianchengchi_percent);
            }

            mHandler.sendMessage(mMessage);
            return;
        }
    };

    final android.os.Handler mHandler=new Handler(){
        @Override
        public void  handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what==0){//登录成功
                customRuningDialog.dismiss();    //打开等待框

                xianchengchi_ProgressDialog = new ProgressDialog(mContext);
                xianchengchi_ProgressDialog.setMessage("正在下载课程表到本地");
                xianchengchi_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                xianchengchi_ProgressDialog.setCancelable(false);
                xianchengchi_ProgressDialog.show();

                executorService.execute(mRunnable_download_zhengfang);
                return;
            }
            if (msg.what==1){//登录失败
                customRuningDialog.dismiss();
                preferences_editor.putBoolean("keeppwd", false);
                preferences_editor.putString("password", "");
                preferences_editor.commit();
                //Toast.makeText(mContext,"test",Toast.LENGTH_SHORT).show();

                Toast.makeText(mContext,xianchengchi_login_status,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(mContext, jiaowu_Schedule_login.class);
                startActivityForResult(intent, 1);



            }
            if (msg.what==2){//下载进度显示
//               System.out.println(xianchengchi_percent);
//                xianchengchi_ProgressDialog.setProgress((int)xianchengchi_percent);
                return;
            }
            if (msg.what==3){//下载完成
                xianchengchi_ProgressDialog.dismiss();
                initParam();
                xianshidangqian();
                mButton_update.setClickable(true);
                return;
            }
            if (msg.what == 10){//网络超时
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 11){//网络超时
                customRuningDialog.dismiss();
                Toast.makeText(mContext, "在线参数获取失败，请保证网络正常的情况下重启app", Toast.LENGTH_SHORT).show();
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(this, "jiaowu_schedule");

        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_schedule,"课表查询");
        //setContentView(R.layout.activity_schedule);
        mContext=this;
        mScheduleDB=new ScheduleDB();
        preferences_data = mContext.getSharedPreferences("ScheduleData", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        preferences_editor = preferences_data.edit();
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);

//        db = openOrCreateDatabase("jiaowu_schedule.db", Context.MODE_PRIVATE, null);


//        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
//        title_name.setText("课表查询");
        init_button();//初始化按钮⌛事件
        init_biaoge();//初始化表格

        if(mScheduleDB.count()==0){
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
        return;}

        initParam();
        xianshidangqian();

    }
    public void xianshidangqian(){
        try {
            mTextView_zhoushu.setText(SchoolDate.get_xiaoli() + "");
            bangding(SchoolDate.get_xiaoli() + "");
        } catch (Exception e) {
            Message message=new Message();
            message.what=11;
            mHandler.sendMessage(message);
            return;
        }


    }
    public void bangding(String zhoushu){//public void bangding(String xiaoli,String xuenian,String xueqi){
            this.zhoushu=Integer.valueOf(zhoushu) ;


        xiaohuiquanbu();//销毁所有已经生成对课表
        int color=0;


        Kebiao[] mkebiao=mScheduleDB.search(zhoushu);
        int xingqi,jieci;
        for (int i=0;i<mkebiao.length;i++){
            addcourse(mkebiao[i],color);
            if (color==6){
                color=0;
            }
            color++;
        }


    }

    public void addcourse(Kebiao mkebiao,final int color){




        int height = dm.heightPixels;
        int gridHeight = height / 8;
        //五种颜色的背景
        int[] background = {R.drawable.course_info_blue, R.drawable.course_info_green,
                R.drawable.course_info_red, R.drawable.course_info_bluegreen,
                R.drawable.course_info_yellow,R.drawable.course_info_orange,R.drawable.course_info_purple};
        // 添加课程信息
        final TextView courseInfo = new TextView(this);
        final Kebiao mraw=mkebiao;
        List<Object> temp=new ArrayList<Object>();
        temp.add(courseInfo);
        temp.add(mkebiao);
        //temp.add()
        mTextView.add(temp);
        //courseInfo.setVisibility(View.GONE);
        courseInfo.setText(mkebiao.kecheng+"\n@"+mkebiao.location);//"软件工程\n@302"
        //该textview的高度根据其节数的跨度来设置
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                aveWidth * 31 / 32,
                (gridHeight - 5) * 2 );
        //textview的位置由课程开始节数和上课的时间（day of week）确定
        rlp.topMargin = 5 + ((2*Integer.parseInt(mkebiao.jieci)-1) - 1) * gridHeight;
        rlp.leftMargin = 1;
        // 偏移由这节课是星期几决定
        rlp.addRule(RelativeLayout.RIGHT_OF, Integer.parseInt(mkebiao.xingqi));
        //字体剧中
       // courseInfo.setTransitionName(xingqi+","+jieci+"");
        courseInfo.setGravity(Gravity.CENTER);
        // 设置一种背景
        courseInfo.setBackgroundResource(background[color]);
        courseInfo.setTextSize(12);
        courseInfo.setLayoutParams(rlp);
        courseInfo.setTextColor(Color.WHITE);
        //设置不透明度
        courseInfo.getBackground().setAlpha(255);

        course_table_layout.addView(courseInfo);
        initParam();
        courseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = getLayoutInflater().inflate(R.layout.activity_schedule_pop, null);
                TextView mTextView_detail=(TextView)view.findViewById(R.id.textView_schedule_detail);
                final String temp=mraw.kecheng+"\n"+mraw.teacher+"\n@"+mraw.location;
                mTextView_detail.setText(temp);
                mTextView_detail.setGravity(Gravity.CENTER);
                mTextView_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setClipboard(mContext,temp);
                        Toast.makeText(mContext, "课程信息已复制到剪切板", Toast.LENGTH_SHORT).show();
                    }
                });
                new AlertDialog.Builder(mContext)
                        .setTitle("课程详情")
                        .setView(view)
                        .setIcon(R.mipmap.isdust)
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int j = 0; j < mTextView.size(); j++) {
                                    List<Object> temp = (List<Object>) mTextView.get(j);
                                    TextView mVieww = (TextView) temp.get(0);
                                    if (mVieww.equals(courseInfo)) {
//                                        int a=(int)temp.get(1);
//                                        int b=(int)temp.get(2);
                                        Kebiao mkebiao=(Kebiao)temp.get(1);
                                        mScheduleDB.delete(mkebiao,0);
                                        //sql_course_delete((int)temp.get(1),(int)temp.get(2),(int)temp.get(3));
                                        bangding(zhoushu + "");



                                    }


                                }


                            }
                        })
                        .setPositiveButton("关闭", null).show();

            }
        });
    }
    public void xiaohuiquanbu(){
        int len=mTextView.size();
        for (int i=0;i<len;i++){
            List<Object> temp=(List<Object>)mTextView.get(i);
            TextView mTextView= (TextView)temp.get(0);
            mTextView.setVisibility(View.INVISIBLE);
        }
        mTextView.clear();

    }
    private void initParam() {
        rlTopBar = (RelativeLayout) this.findViewById(R.id.rl_topbar);


//
        mTextView_zhoushu = (TextView) this.findViewById(R.id.TextView_zhoushu);
        mTextView_zhoushu.setOnClickListener(myListener);
        // 初始化数据项
        mList_zhoushu = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 23; i++) {
            HashMap<String, String> mapTemp = new HashMap<String, String>();
            mapTemp.put("item", ""+ i);
            mList_zhoushu.add(mapTemp);
        }
    }

    private View.OnClickListener myListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.TextView_zhoushu:
                    if (mPopupWindow_zhoushu != null && mPopupWindow_zhoushu.isShowing()) {
                        mPopupWindow_zhoushu.dismiss();
                    } else {
                        mView_zhoushu = getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistMiddle = (ListView) mView_zhoushu
                                .findViewById(R.id.menulist);
                        SimpleAdapter listAdapter = new SimpleAdapter(
                                mContext, mList_zhoushu, R.layout.pop_menuitem,
                                new String[] { "item" },
                                new int[] { R.id.menuitem });
                        menulistMiddle.setAdapter(listAdapter);

                        // 点击listview中item的处理
                        menulistMiddle
                                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> arg0,
                                                            View arg1, int arg2, long arg3) {
                                        String strItem = mList_zhoushu.get(arg2).get(
                                                "item");

                                        bangding(strItem);
                                        mTextView_zhoushu.setText(strItem);

                                        if (mPopupWindow_zhoushu != null && mPopupWindow_zhoushu.isShowing()) {
                                            mPopupWindow_zhoushu.dismiss();
                                        }
                                    }
                                });

                        mPopupWindow_zhoushu = new PopupWindow(mView_zhoushu, mTextView_zhoushu.getWidth(),
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                        ColorDrawable cd = new ColorDrawable(0);
                        mPopupWindow_zhoushu.setBackgroundDrawable(cd);
                        mPopupWindow_zhoushu.setAnimationStyle(R.style.PopupAnimation);
                        mPopupWindow_zhoushu.update();
                        mPopupWindow_zhoushu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        mPopupWindow_zhoushu.setTouchable(true); // 设置popupwindow可点击
                        mPopupWindow_zhoushu.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        mPopupWindow_zhoushu.setFocusable(true); // 获取焦点

                        // 设置popupwindow的位置
                        int topBarHeight = rlTopBar.getBottom();
                        mPopupWindow_zhoushu.showAsDropDown(mTextView_zhoushu, 0,
                                0);

                        mPopupWindow_zhoushu.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    mPopupWindow_zhoushu.dismiss();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void init_biaoge(){
        empty = (TextView) this.findViewById(R.id.test_empty);
        monColum = (TextView) this.findViewById(R.id.test_monday_course);
        tueColum = (TextView) this.findViewById(R.id.test_tuesday_course);
        wedColum = (TextView) this.findViewById(R.id.test_wednesday_course);
        thrusColum = (TextView) this.findViewById(R.id.test_thursday_course);
        friColum = (TextView) this.findViewById(R.id.test_friday_course);
        satColum  = (TextView) this.findViewById(R.id.test_saturday_course);
        sunColum = (TextView) this.findViewById(R.id.test_sunday_course);
        course_table_layout = (RelativeLayout) this.findViewById(R.id.test_course_rl);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 8;
        //第一个空白格子设置为25宽
        empty.setWidth(aveWidth * 3/4);
        monColum.setWidth(aveWidth * 33/32 + 1);
        tueColum.setWidth(aveWidth * 33/32 + 1);
        wedColum.setWidth(aveWidth * 33/32 + 1);
        thrusColum.setWidth(aveWidth * 33/32 + 1);
        friColum.setWidth(aveWidth * 33/32 + 1);
        satColum.setWidth(aveWidth * 33/32 + 1);
        sunColum.setWidth(aveWidth * 33/32 + 1);
        this.screenWidth = width;
        this.aveWidth = aveWidth;
        int height = dm.heightPixels;
        int gridHeight = height / 8;
        //设置课表界面
        //动态生成12 * maxCourseNum个textview
        for(int i = 1; i <= 10; i ++){

            for(int j = 1; j <= 8; j ++){

                TextView tx = new TextView(this);
                tx.setId((i - 1) * 8  + j);
                //除了最后一列，都使用course_text_view_bg背景（最后一列没有右边框）
                if(j < 8)
                    tx.setBackgroundDrawable(this.
                            getResources().getDrawable(R.drawable.course_text_view_bg));
                else
                    tx.setBackgroundDrawable(this.
                            getResources().getDrawable(R.drawable.course_table_last_colum));
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 33 / 32 + 1,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                //字体样式
                tx.setTextAppearance(this, R.style.courseTableText);
                //如果是第一列，需要设置课的序号（1 到 12）
                if(j == 1)
                {
                    tx.setText(String.valueOf(i));
                    rp.width = aveWidth * 3/4;
                    //设置他们的相对位置
                    if(i == 1)
                        rp.addRule(RelativeLayout.BELOW, empty.getId());
                    else
                        rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
                }
                else
                {
                    rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8  + j - 1);
                    rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * 8  + j - 1);
                    tx.setText("");
                }

                tx.setLayoutParams(rp);
                course_table_layout.addView(tx);
            }
        }
    }
    public void init_button(){
        mButton_update=(Button)findViewById(R.id.button_update);
        mButton_logout=(Button)findViewById(R.id.button_logout);
        mButton_add=(Button)findViewById(R.id.button_schedule_add);
        mButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, jiaowu_schedule_add.class);
                startActivityForResult(intent, 2);

            }
        });
        mButton_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton_update.setClickable(false);
                mScheduleDB.drop();
                mScheduleDB.create();
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
        });

        mButton_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences_editor.putString("password", "");
                mScheduleDB.drop();
                mScheduleDB.create();
                Intent intent = new Intent();
                intent.setClass(mContext, jiaowu_Schedule_login.class);
                startActivityForResult(intent, 1);

            }
        });
    }

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
            case 2:
                if (resultCode == RESULT_OK) {
//                    String zhoushu=mTextView_zhoushu.getText().toString();
                    bangding(zhoushu+"");
                    break;



                }
                if (resultCode == RESULT_CANCELED) {

                    break;

                }
                break;

        }
    }








    public String kecheng_generate(String subject,String time,String teacher,String location){
        return subject+"<br>"+time+"<br>"+teacher+"<br>"+location;
    }
    private void setClipboard(Context context,String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }


}
