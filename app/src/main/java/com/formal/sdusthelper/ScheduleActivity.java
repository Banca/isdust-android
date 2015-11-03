package com.formal.sdusthelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;
import com.formal.sdusthelper.datatype.Kebiao;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.isdust.isdust.function.SchoolDate;
import pw.isdust.isdust.function.SelectCoursePlatform;

/**
 * Created by Administrator on 2015/10/16.
 */
public class ScheduleActivity extends BaseSubPageActivity {
    SharedPreferences preferences_data;
    SharedPreferences.Editor preferences_editor;
    //实例化SharedPreferences对象




    List<TextView> mTextView=new ArrayList<TextView>();
    SelectCoursePlatform mXuankepingtai;

    Button mButton_update;

    // 工具栏
    private RelativeLayout rlTopBar;

    // 左中右三个控件（工具栏里）

    private TextView tvMiddle;

    // 左中右三个弹出窗口

    private PopupWindow popMiddle;

    // 左中右三个layout

    private View layoutMiddle;

    // 左中右三个ListView控件（弹出窗口里）

    private ListView menulistMiddle;

    // 菜单数据项

    private List<Map<String, String>> listMiddle;


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

//线程池
    String xianchengchi_user;
    String xianchengchi_password;
    String xianchengchi_login_status;
    double xianchengchi_percent;
    String xianchengchi_saving_json;
    ProgressDialog xianchengchi_ProgressDialog;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    Kebiao [] mKebiao_all;

    Runnable mRunnable_login=new Runnable(){
        @Override
        public void run() {
            mXuankepingtai=new SelectCoursePlatform(mContext);
//            xianchengchi_login_status=mXuankepingtai.login_xuankepingtai(xianchengchi_user, xianchengchi_password);
            xianchengchi_login_status=mXuankepingtai.login_zhengfang(xianchengchi_user, xianchengchi_password);
            if(xianchengchi_login_status.contains("登录成功")){
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
    Runnable mRunnable_download=new Runnable() {
        @Override
        public void run() {
            int zhoushu=22;
            xianchengchi_saving_json="";
            Message mMessage=new Message();
            mMessage.what=3;
            for(int i=0;i<zhoushu;i++){
                xianchengchi_saving_json=xianchengchi_saving_json+mXuankepingtai.scheduletojson(mXuankepingtai.chaxun((i+1)+"","2015-2016","1"));
                xianchengchi_percent=((double)(i+1)/(double)zhoushu)*100;
                xianchengchi_ProgressDialog.setProgress((int)xianchengchi_percent);
            }
            xianchengchi_saving_json=xianchengchi_saving_json.replace("][",",");
            xianchengchi_saving_json=xianchengchi_saving_json.replace(",,","");
            xianchengchi_saving_json=xianchengchi_saving_json.replace(",,","");
            xianchengchi_saving_json=xianchengchi_saving_json.replace("[,","[");
            xianchengchi_saving_json=xianchengchi_saving_json.replace(",]","]");
            try {
                JSONArray m=new JSONArray(xianchengchi_saving_json);

                System.out.println(xianchengchi_saving_json);
            }catch (Exception e){

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
                xianchengchi_ProgressDialog = new ProgressDialog(mContext);
                xianchengchi_ProgressDialog.setMessage("正在下载课程表到本地");
                xianchengchi_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                xianchengchi_ProgressDialog.setCancelable(false);
                xianchengchi_ProgressDialog.show();

                executorService.execute(mRunnable_download);
                return;
            }
            if (msg.what==1){//登录失败
                preferences_editor.putBoolean("keeppwd", false);
                preferences_editor.putString("password", "");
                Intent intent=new Intent();
                intent.setClass(mContext, Schedule_login.class);
                startActivity(intent);



            }
            if (msg.what==2){//下载进度显示
//               System.out.println(xianchengchi_percent);
//                xianchengchi_ProgressDialog.setProgress((int)xianchengchi_percent);
                return;
            }
            if (msg.what==3){//下载完成
                xianchengchi_ProgressDialog.dismiss();
                writeToFile("schedule.dat",xianchengchi_saving_json);
                String kebiao_json=readFromFile("schedule.dat");
                initParam();
                getScheduleFromJson(kebiao_json);
                return;
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(this, "jiaowu_schedule");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mContext=this;
        preferences_data = mContext.getSharedPreferences("ScheduleData", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        preferences_editor = preferences_data.edit();



        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText("课表查询");
        mButton_update=(Button)findViewById(R.id.button_update);
        mButton_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToFile("schedule.dat","");
                String user_save=preferences_data.getString("username", "");
                String password_save=preferences_data.getString("password", "");
                if (user_save.equals("") || password_save.equals("")){

                        Intent intent = new Intent();
                        intent.setClass(mContext,Schedule_login.class);
                        startActivity(intent);
                        return;

                }else{
                    xianchengchi_user=user_save;
                    xianchengchi_password=password_save;}


                executorService.execute(mRunnable_login);
            }
        });


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
        //title_name.setText("空自习室查询");
        //writeToFile("schedule.dat","");
        String kebiao_json=readFromFile("schedule.dat");
//        writeToFile("schedule.dat","");
        if(kebiao_json==""){
            String user_save=preferences_data.getString("username", "");
            String password_save=preferences_data.getString("password", "");
            if (user_save.equals("") || password_save.equals("")){
                if (getIntent().getExtras()!=null){
                String user_intent=getIntent().getExtras().getString("username");;
                String password_intent=getIntent().getExtras().getString("password");;
                if (user_intent.equals("")||password_intent.equals("")){
                    Intent intent=new Intent();
                    intent.setClass(mContext,Schedule_login.class);
                    startActivity(intent);
                    return;
                }else {
                    xianchengchi_user=user_intent;
                    xianchengchi_password=password_intent;
                }}else{
                    Intent intent = new Intent();
                    intent.setClass(mContext,Schedule_login.class);
                    startActivity(intent);
                    return;
                }
                }else{
                xianchengchi_user=user_save;
                xianchengchi_password=password_save;}


            executorService.execute(mRunnable_login);
        return;}

//        Kebiao mkebiao_all=
        initParam();
        getScheduleFromJson(kebiao_json);
        tvMiddle.setText(SchoolDate.get_xiaoli() + "");
        bangding(SchoolDate.get_xiaoli() + "");



        //bangding(SchoolDate.get_xiaoli()+"", "2015-2016", "1");


    }
    public void bangding(String zhoushu){//public void bangding(String xiaoli,String xuenian,String xueqi){
//        xiaohuiquanbu();
//        int color=0;
//        Kebiao c[]=mXuankepingtai.chaxun(xiaoli + "", xuenian, xueqi);
//        int xingqi,jieci;
//        for (int i=0;i<c.length;i++){
//            String temp[]=c[i].kecheng.split("<br>");
//            xingqi=Integer.parseInt(c[i].xingqi);
//            jieci=Integer.parseInt(c[i].jieci);
//            addcourse(xingqi,jieci,temp[0]+"\n@"+temp[3],color);
//            if (color==6){
//                color=0;
//            }
//            color++;
//        }

        //以上为在线读取课表，以下为重构后的程序，读取本地课表

        xiaohuiquanbu();//销毁所有已经生成对课表
        int color=0;
        List<Kebiao> mList_kebiao=new ArrayList<Kebiao>();
        int len=mKebiao_all.length;
        for (int i=0;i<len;i++){
            if (mKebiao_all[i].zhoushu.equals(zhoushu)){
                mList_kebiao.add(mKebiao_all[i]);

            }
        }
        len=mList_kebiao.size();
        //Kebiao c[]=mXuankepingtai.chaxun(xiaoli + "", xuenian, xueqi);
        int xingqi,jieci;
        for (int i=0;i<len;i++){
            String temp[]=mList_kebiao.get(i).kecheng.split("<br>");
            xingqi=Integer.parseInt(mList_kebiao.get(i).xingqi);
            jieci=Integer.parseInt(mList_kebiao.get(i).jieci);
            addcourse(xingqi,jieci,temp[0]+"\n@"+temp[3],color,mList_kebiao.get(i).kecheng);
            if (color==6){
                color=0;
            }
            color++;
        }


    }

    public void addcourse(int xingqi,int jieci,String neirong,final int color,String raw){




        int height = dm.heightPixels;
        int gridHeight = height / 8;
        //五种颜色的背景
        int[] background = {R.drawable.course_info_blue, R.drawable.course_info_green,
                R.drawable.course_info_red, R.drawable.course_info_bluegreen,
                R.drawable.course_info_yellow,R.drawable.course_info_orange,R.drawable.course_info_purple};
        // 添加课程信息
        final TextView courseInfo = new TextView(this);
        final String mraw=raw;
        mTextView.add(courseInfo);
        //courseInfo.setVisibility(View.GONE);
        courseInfo.setText(neirong);//"软件工程\n@302"
        //该textview的高度根据其节数的跨度来设置
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                aveWidth * 31 / 32,
                (gridHeight - 5) * 2 );
        //textview的位置由课程开始节数和上课的时间（day of week）确定
        rlp.topMargin = 5 + ((2*jieci-1) - 1) * gridHeight;
        rlp.leftMargin = 1;
        // 偏移由这节课是星期几决定
        rlp.addRule(RelativeLayout.RIGHT_OF, xingqi);
        //字体剧中
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
            PopupWindow mPopupWindow;
            @Override
            public void onClick(View view) {


                int[] background = {R.drawable.course_info_blue, R.drawable.course_info_green,
                        R.drawable.course_info_red, R.drawable.course_info_bluegreen,
                        R.drawable.course_info_yellow,R.drawable.course_info_orange,R.drawable.course_info_purple};

                view = getLayoutInflater().inflate(R.layout.activity_schedule_pop, null);
                //view.setBackgroundResource(R.drawable.course_info_blue);
                TextView mTextView_detail=(TextView)view.findViewById(R.id.textView_schedule_detail);
                String temp=mraw.replace("<br>", "\n");
                mTextView_detail.setText(temp);
                mTextView_detail.setBackgroundResource(background[color]);
                mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);




                ColorDrawable cd = new ColorDrawable(-0000);
                mPopupWindow.setBackgroundDrawable(cd);
                mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
                mPopupWindow.update();
                mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                mPopupWindow.setTouchable(true); // 设置popupwindow可点击
                mPopupWindow.setOutsideTouchable(true); // 设置popupwindow外部可点击
                mPopupWindow.setFocusable(true); // 获取焦点
                mPopupWindow.showAsDropDown(findViewById(R.id.include2));
                ;
                System.out.println(mraw);
//                View mview=findViewById(R.id.include2);
//                mview.setBackgroundResource(R.drawable.course_info_blue);
//                pop.showAsDropDown(mview);

            }
        });
    }
    public void xiaohuiquanbu(){
        int len=mTextView.size();
        for (int i=0;i<len;i++){
            mTextView.get(i).setVisibility(View.INVISIBLE);
        }
        mTextView.clear();

    }
    private void initParam() {
        rlTopBar = (RelativeLayout) this.findViewById(R.id.rl_topbar);


//
        tvMiddle = (TextView) this.findViewById(R.id.tv_middle);
        tvMiddle.setOnClickListener(myListener);
        // 初始化数据项
        listMiddle = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 23; i++) {
            HashMap<String, String> mapTemp = new HashMap<String, String>();
            mapTemp.put("item", ""+ i);
            listMiddle.add(mapTemp);
        }
    }

    private View.OnClickListener myListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_middle:
                    if (popMiddle != null && popMiddle.isShowing()) {
                        popMiddle.dismiss();
                    } else {
                        layoutMiddle = getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistMiddle = (ListView) layoutMiddle
                                .findViewById(R.id.menulist);
                        SimpleAdapter listAdapter = new SimpleAdapter(
                                mContext, listMiddle, R.layout.pop_menuitem,
                                new String[] { "item" },
                                new int[] { R.id.menuitem });
                        menulistMiddle.setAdapter(listAdapter);

                        // 点击listview中item的处理
                        menulistMiddle
                                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> arg0,
                                                            View arg1, int arg2, long arg3) {
                                        String strItem = listMiddle.get(arg2).get(
                                                "item");

                                        bangding(strItem);
                                        tvMiddle.setText(strItem);

                                        if (popMiddle != null && popMiddle.isShowing()) {
                                            popMiddle.dismiss();
                                        }
                                    }
                                });

                        popMiddle = new PopupWindow(layoutMiddle, tvMiddle.getWidth(),
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                        ColorDrawable cd = new ColorDrawable(-0000);
                        popMiddle.setBackgroundDrawable(cd);
                        popMiddle.setAnimationStyle(R.style.PopupAnimation);
                        popMiddle.update();
                        popMiddle.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popMiddle.setTouchable(true); // 设置popupwindow可点击
                        popMiddle.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        popMiddle.setFocusable(true); // 获取焦点

                        // 设置popupwindow的位置
                        int topBarHeight = rlTopBar.getBottom();
                        popMiddle.showAsDropDown(tvMiddle, 0,
                                (topBarHeight - tvMiddle.getHeight()) / 2);

                        popMiddle.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    popMiddle.dismiss();
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

    private void writeToFile(String file,String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public String readFromFile(String filename) {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    public void getScheduleFromJson(String  kebiao_json){
        try {
            JSONArray mJSONArray=new JSONArray(kebiao_json);
            int len=mJSONArray.length();
            mKebiao_all=new Kebiao[len];
            JSONObject obj;
            Kebiao kebiao_temp;
            for (int i=0;i<len;i++){
                obj=mJSONArray.getJSONObject(i);
                kebiao_temp=new Kebiao();
                kebiao_temp.zhoushu=obj.getString("zhoushu");
                kebiao_temp.xingqi=obj.getString("xingqi");
                kebiao_temp.jieci=obj.getString("jieci");
                kebiao_temp.kecheng=obj.getString("kecheng");
                mKebiao_all[i]=kebiao_temp;
            }
            System.out.println("");
        } catch (JSONException e) {
            e.printStackTrace();
            writeToFile("schedule.dat","");
        }


    }



}
