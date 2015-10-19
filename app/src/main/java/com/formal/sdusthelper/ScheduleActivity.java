package com.formal.sdusthelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pw.isdust.isdust.function.SchoolDate;
import pw.isdust.isdust.function.SchoolCard;
import pw.isdust.isdust.function.SelectCoursePlatform;

/**
 * Created by Administrator on 2015/10/16.
 */
public class ScheduleActivity extends Activity {
    List<TextView> mTextView=new ArrayList<TextView>();
    SelectCoursePlatform mXuankepingtai;



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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mContext=this;
        TextView title_name = (TextView) findViewById(R.id.title_bar_name);
        title_name.setText("课表查询");

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
        SchoolCard b=new SchoolCard(this);
//		Xuankepingtai a=new Xuankepingtai();
        mXuankepingtai=new SelectCoursePlatform();
        mXuankepingtai.login("201501060225", "960826wang");
        Random mRandom=new Random();

		SelectCoursePlatform.Kebiao c[]=mXuankepingtai.chaxun(SchoolDate.get_xiaoli() + "", "2015-2016", "1");
        int xingqi,jieci;
        for (int i=0;i<c.length;i++){
            String temp[]=c[i].kecheng.split("<br>");
            xingqi=Integer.parseInt(c[i].xingqi);
            jieci=Integer.parseInt(c[i].jieci);
            addcourse(xingqi,jieci,temp[0]+"\n@"+temp[3],mRandom.nextInt(5));
        }
//        addcourse(1,1,"软件工程\n@302",3);
//        addcourse(2,2,"大学英语\n@J7-302",1);

    }
    public void bangding(String xiaoli,String xuenian,String xueqi){
        Random mRandom=new Random();
        SelectCoursePlatform.Kebiao c[]=mXuankepingtai.chaxun(SchoolDate.get_xiaoli() + "", "2015-2016", "1");
        int xingqi,jieci;
        for (int i=0;i<c.length;i++){
            String temp[]=c[i].kecheng.split("<br>");
            xingqi=Integer.parseInt(c[i].xingqi);
            jieci=Integer.parseInt(c[i].jieci);
            addcourse(xingqi,jieci,temp[0]+"\n@"+temp[3],mRandom.nextInt(5));
        }

    }
    public void addcourse(int xingqi,int jieci,String neirong,int color){
        int height = dm.heightPixels;
        int gridHeight = height / 8;
        //五种颜色的背景
        int[] background = {R.drawable.course_info_blue, R.drawable.course_info_green,
                R.drawable.course_info_red, R.drawable.course_info_red,
                R.drawable.course_info_yellow};
        // 添加课程信息
        TextView courseInfo = new TextView(this);
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
                                        xiaohuiquanbu();
                                        bangding(strItem, "2015-2016", "1");
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
}
