package com.isdust.www.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.isdust.www.AboutActivity;
import com.isdust.www.CardActivity;
import com.isdust.www.GoNetActivity;
import com.isdust.www.JiaowuActivity;
import com.isdust.www.KuaiTongActivity;
import com.isdust.www.LibraryActivity;
import com.isdust.www.LifeActivity;
import com.isdust.www.MainActivity;
import com.isdust.www.NewsActivity;
import com.isdust.www.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;

public class Leftmenu {
    public SlidingMenu menu;
    public Activity thisActivity;
    int type;

    public Leftmenu(Activity activity, int mytype) {
        type = mytype;
        thisActivity = activity;
        // configure the SlidingMenu
        menu = new SlidingMenu(thisActivity);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // menu.setShadowWidth(40);
        // menu.setShadowDrawable(R.drawable.shadow_left);

        // 设置滑动菜单视图的宽度
        menu.setBehindWidth(dip2px(thisActivity, 200));
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.55f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(thisActivity, SlidingMenu.SLIDING_CONTENT);
        // 为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);

        menu.setBehindScrollScale(0);

        menu.setOnClosedListener(new OnClosedListener() {
            @Override
            public void onClosed() {
                System.out.println("关闭菜单");
                try {
//                    setalpha();
                } catch (Exception ignored) {
                }
            }
        });

        menu.setOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpened() {
                System.out.println("打开菜单");
                try {
//                    setalpha();
                } catch (Exception ignored) {
                }
                /**
                 * 在菜单被打开完毕之后,我们把登录按钮的透明度调到255,这样避免了按钮变灰的bug
                 */
            }
        });
        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("滑动菜单被点击");
                menu.toggle();
                /**
                 * 当滑动菜单被点击的时候,一般是用户想将菜单滑回去,不然他也不会点空白处,而是按钮
                 * 此时我们使用menu.toggle()这个函数,将菜单弹回左边
                 */
            }
        });
        ImageView ImageView_home = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_home);
        ImageView ImageView_shangwang = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_cmcc);
        ImageView ImageView_kuaitong = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_kuaitong);
        ImageView ImageView_jiaowu = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_jiaowu);
        ImageView ImageView_library = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_library);
        ImageView ImageView_card = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_card);
        ImageView ImageView_news = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_news);
        ImageView ImageView_about = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_about);
        ImageView ImageView_life = (ImageView) thisActivity
                .findViewById(R.id.leftmenu_life);


        ImageView_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, MainActivity.class);
                    intent.putExtra("menu", 0);

                    if (type != 0){
                        thisActivity.startActivity(intent);
                        thisActivity.finish();
                    }
                    else{
                        menu.toggle();}
                }
                return true;
            }
        });

        ImageView_shangwang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, GoNetActivity.class);
                    intent.putExtra("menu", 1);

                    if (type != 1){
                        thisActivity.startActivity(intent);
                        thisActivity.finish();
                    }
                    else{
                        menu.toggle();}
                }
                return true;
            }
        });

        ImageView_kuaitong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, KuaiTongActivity.class);

                    if (type != 2){
                        thisActivity.startActivity(intent);
                        thisActivity.finish();
                    }
                    else{
                        menu.toggle();}
                }
                return true;
            }
        });

        ImageView_jiaowu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, JiaowuActivity.class);

                    if (type != 3){
                        thisActivity.startActivity(intent);
                        thisActivity.finish();}
                    else{
                        menu.toggle();
                    }
                }
                return true;
            }
        });

        ImageView_library.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(4);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, LibraryActivity.class);
                    if (type != 4) {
                            thisActivity.startActivity(intent);
                            thisActivity.finish();
                    } else{
                        menu.toggle();
                    }
                }
                return true;
            }
        });

        ImageView_card.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(5);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, CardActivity.class);
                    if (type != 5) {
                        thisActivity.startActivity(intent);
                        thisActivity.finish();
                    } else
                        menu.toggle();
                }
                return true;
            }
        });


        ImageView_news.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(6);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (type != 6) {
                        Intent intent = new Intent();
                        intent.setClass(thisActivity, NewsActivity.class);
                        thisActivity.startActivity(intent);
                        thisActivity.finish();
                    } else
                        menu.toggle();



                }
                return true;
            }
        });


        ImageView_about.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(7);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, AboutActivity.class);
                    thisActivity.startActivity(intent);
                    menu.toggle();
                }
                return true;
            }
        });



        ImageView_life.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(8);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, LifeActivity.class);

                    if (type != 8){
                        thisActivity.startActivity(intent);
                        thisActivity.finish();}
                    else{
                        menu.toggle();
                    }
                }
                return true;
            }
        });
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void leftmenu_ui(int a) {


        ImageView[] myImageViews = {
                (ImageView) thisActivity.findViewById(R.id.leftmenu_home),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_cmcc),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_kuaitong),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_jiaowu),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_library),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_card),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_news),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_about),
                (ImageView) thisActivity.findViewById(R.id.leftmenu_life),};


        int[] tu0 = {R.drawable.leftmenu_home1, R.drawable.leftmenu_wifi1,
                R.drawable.leftmenu_kuaitong1, R.drawable.leftmenu_jiaowu1,
                R.drawable.leftmenu_library1, R.drawable.leftmenu_card1,
                R.drawable.leftmenu_news1,R.drawable.leftmenu_about1,
                R.drawable.leftmenu_life1};
        int[] tu1 = {R.drawable.leftmenu_home2, R.drawable.leftmenu_wifi2,
                R.drawable.leftmenu_kuaitong2, R.drawable.leftmenu_jiaowu2,
                R.drawable.leftmenu_library2, R.drawable.leftmenu_card2,
                R.drawable.leftmenu_news2,R.drawable.leftmenu_about2,
                R.drawable.leftmenu_life2};
        int i;
        for (i = 0; i < myImageViews.length; i++) {
            if (i == (a))
                myImageViews[i].setBackgroundResource(tu1[i]);
            else
                myImageViews[i].setBackgroundResource(tu0[i]);
        }
    }
}
