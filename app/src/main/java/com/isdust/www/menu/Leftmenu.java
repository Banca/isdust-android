package com.isdust.www.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.isdust.www.AboutActivity;
import com.isdust.www.CardActivity;
import com.isdust.www.GoNetActivity;
import com.isdust.www.JiaowuActivity;
import com.isdust.www.KuaiTongActivity;
import com.isdust.www.LibraryActivity;
import com.isdust.www.MainActivity;
import com.isdust.www.NewsActivity;
import com.isdust.www.R;
import com.isdust.www.SetActivity;
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
        menu.setMenu(R.layout.layout_menu);

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
        TextView TextView_home = (TextView) thisActivity
                .findViewById(R.id.slide_menu_home);
        TextView TextView_shangwang = (TextView) thisActivity
                .findViewById(R.id.slide_menu_cmcc);
        TextView TextView_kuaitong = (TextView) thisActivity
                .findViewById(R.id.slide_menu_kuaitong);
        TextView TextView_jiaowu = (TextView) thisActivity
                .findViewById(R.id.slide_menu_jiaowu);
        TextView TextView_library = (TextView) thisActivity
                .findViewById(R.id.slide_menu_library);
        TextView TextView_card = (TextView) thisActivity
                .findViewById(R.id.slide_menu_card);
        TextView TextView_news = (TextView) thisActivity
                .findViewById(R.id.slide_menu_news);
        TextView TextView_set = (TextView) thisActivity
                .findViewById(R.id.slide_menu_set);
        TextView TextView_about = (TextView) thisActivity
                .findViewById(R.id.slide_menu_about);

        TextView_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, MainActivity.class);
                    intent.putExtra("menu", 1);
                    thisActivity.startActivity(intent);
                    if (type != 1)
                        thisActivity.finish();
                    else
                        menu.toggle();
                }
                return true;
            }
        });

        TextView_shangwang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, GoNetActivity.class);
                    intent.putExtra("menu", 1);
                    thisActivity.startActivity(intent);
                    if (type != 2)
                        thisActivity.finish();
                    else
                        menu.toggle();
                }
                return true;
            }
        });

        TextView_kuaitong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, KuaiTongActivity.class);
                    thisActivity.startActivity(intent);
                    if (type != 3)
                        thisActivity.finish();
                    else
                        menu.toggle();
                }
                return true;
            }
        });

        TextView_jiaowu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, JiaowuActivity.class);
                    thisActivity.startActivity(intent);
                    if (type != 4)
                        thisActivity.finish();
                    else
                        menu.toggle();
                }
                return true;
            }
        });

        TextView_library.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(6);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (type != 5) {
                        Intent intent = new Intent();
                        intent.setClass(thisActivity, LibraryActivity.class);
                            thisActivity.startActivity(intent);
                            thisActivity.finish();


                    } else
                        menu.toggle();
                }
                return true;
            }
        });

        TextView_card.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(3);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (type != 6) {
                        Intent intent = new Intent();
                        intent.setClass(thisActivity, CardActivity.class);
                        thisActivity.startActivity(intent);
                        thisActivity.finish();
                    } else
                        menu.toggle();
                }
                return true;
            }
        });


        TextView_news.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(4);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (type != 7) {
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

        TextView_set.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(5);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (type != 8) {
                        Intent intent = new Intent();
                        intent.setClass(thisActivity, SetActivity.class);
                        thisActivity.startActivity(intent);
                        thisActivity.finish();
                    } else
                        menu.toggle();

                }
                return true;
            }
        });
        TextView_about.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftmenu_ui(5);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent();
                    intent.setClass(thisActivity, AboutActivity.class);
                    thisActivity.startActivity(intent);
                    menu.toggle();
                }
                return true;
            }
        });
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

//    private void setalpha() {
//        if (type == 1) {
//            thisActivity.findViewById(R.id.shangwang_deglu).getBackground()
//                    .setAlpha(255);
//            thisActivity.findViewById(R.id.shangwang_duankai).getBackground()
//                    .setAlpha(255);
//        }
//    }

    public void leftmenu_ui(int a) {



        TextView[] myImageViews = {
                (TextView) thisActivity.findViewById(R.id.slide_menu_home),
                (TextView) thisActivity.findViewById(R.id.slide_menu_cmcc),
                (TextView) thisActivity.findViewById(R.id.slide_menu_kuaitong),
                (TextView) thisActivity.findViewById(R.id.slide_menu_jiaowu),
                (TextView) thisActivity.findViewById(R.id.slide_menu_library),
                (TextView) thisActivity.findViewById(R.id.slide_menu_card),
                (TextView) thisActivity.findViewById(R.id.slide_menu_news),
                (TextView) thisActivity.findViewById(R.id.slide_menu_set),
                (TextView) thisActivity.findViewById(R.id.slide_menu_about),};
//        int[] tu1 = {R.drawable.left_shangwang1, R.drawable.left_jiaowu1,
//                R.drawable.left_shenghuo1, R.drawable.left_xiaoyuanka1,
//                R.drawable.left_ditie1, R.drawable.left_about1,
//                R.drawable.left_guancang1,};
//        int[] tu0 = {R.drawable.left_shangwang0, R.drawable.left_jiaowu0,
//                R.drawable.left_shenghuo0, R.drawable.left_xiaoyuanka0,
//                R.drawable.left_ditie0, R.drawable.left_about0,
//                R.drawable.left_guancang0,};
//        int i;
//        for (i = 0; i < myImageViews.length; i++) {
//            if (i == a)
//                myImageViews[i].setBackgroundResource(tu1[i]);
//            else
//                myImageViews[i].setBackgroundResource(tu0[i]);
//        }
//    }
}}
