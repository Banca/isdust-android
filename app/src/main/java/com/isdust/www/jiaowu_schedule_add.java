package com.isdust.www;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.datepicker.DatePickerDailog;
import com.isdust.www.baseactivity.BaseSubPageActivity_new;

/**
 * Created by wzq on 16/1/15.
 */

public class jiaowu_schedule_add extends BaseSubPageActivity_new {
    int zhoushu [];
    int zhoushu_type[];//0为未选，1为已选，0-2分别对应单周，双周，全选
    int xingqi,jieci;
//    Calendar dateandtime;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_schedule_add_main, "创建课程");
        zhoushu=new int[21];
        zhoushu_type=new int[3];


        final Button mbutton_select_zhoushu=(Button)findViewById(R.id.button_schedule_zhoushu);

        mbutton_select_zhoushu.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                View checkBoxView = View.inflate(mContext, R.layout.activity_schedule_add_zhoushu, null);
                final RelativeLayout relativelayout_schedule_add_zhoushu = (RelativeLayout) checkBoxView.findViewById(R.id.relativelayout_schedule_add_zhoushu);


                View.OnClickListener textView_zhoushu_onclick = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView mTextView_zhoushu[];
                        mTextView_zhoushu = new TextView[21];
                        mTextView_zhoushu[1] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_1);
                        mTextView_zhoushu[2] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_2);
                        mTextView_zhoushu[3] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_3);
                        mTextView_zhoushu[4] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_4);
                        mTextView_zhoushu[5] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_5);
                        mTextView_zhoushu[6] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_6);
                        mTextView_zhoushu[7] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_7);
                        mTextView_zhoushu[8] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_8);
                        mTextView_zhoushu[9] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_9);
                        mTextView_zhoushu[10] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_10);
                        mTextView_zhoushu[11] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_11);
                        mTextView_zhoushu[12] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_12);
                        mTextView_zhoushu[13] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_13);
                        mTextView_zhoushu[14] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_14);
                        mTextView_zhoushu[15] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_15);
                        mTextView_zhoushu[16] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_16);
                        mTextView_zhoushu[17] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_17);
                        mTextView_zhoushu[18] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_18);
                        mTextView_zhoushu[19] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_19);
                        mTextView_zhoushu[20] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_20);
                        TextView mTextView_zhoushu_type[];
                        mTextView_zhoushu_type = new TextView[3];
                        mTextView_zhoushu_type[0] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_single);
                        mTextView_zhoushu_type[1] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_double);
                        mTextView_zhoushu_type[2] = (TextView) relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_all);
                        int i = 0;
                        switch (view.getId()) {
                            case R.id.textView_zhoushu_1:
                                i = 1;
                                break;
                            case R.id.textView_zhoushu_2:
                                i = 2;
                                break;
                            case R.id.textView_zhoushu_3:
                                i = 3;
                                break;
                            case R.id.textView_zhoushu_4:
                                i = 4;
                                break;
                            case R.id.textView_zhoushu_5:
                                i = 5;
                                break;
                            case R.id.textView_zhoushu_6:
                                i = 6;
                                break;
                            case R.id.textView_zhoushu_7:
                                i = 7;
                                break;
                            case R.id.textView_zhoushu_8:
                                i = 8;
                                break;
                            case R.id.textView_zhoushu_9:
                                i = 9;
                                break;
                            case R.id.textView_zhoushu_10:
                                i = 10;
                                break;
                            case R.id.textView_zhoushu_11:
                                i = 11;
                                break;
                            case R.id.textView_zhoushu_12:
                                i = 12;
                                break;
                            case R.id.textView_zhoushu_13:
                                i = 13;
                                break;
                            case R.id.textView_zhoushu_14:
                                i = 14;
                                break;
                            case R.id.textView_zhoushu_15:
                                i = 15;
                                break;
                            case R.id.textView_zhoushu_16:
                                i = 16;
                                break;
                            case R.id.textView_zhoushu_17:
                                i = 17;
                                break;
                            case R.id.textView_zhoushu_18:
                                i = 18;
                                break;
                            case R.id.textView_zhoushu_19:
                                i = 19;
                                break;
                            case R.id.textView_zhoushu_20:
                                i = 20;
                                break;
                            case R.id.textView_zhoushu_single:
                                if (zhoushu_type[0] == 0) {
                                    for (int j = 1; j <= 20; j += 2) {
                                        zhoushu[j] = 1;
                                        mTextView_zhoushu[j].setBackgroundColor(0xFFA2D231);
                                        mTextView_zhoushu[j].setTextColor(0xFFFFFFFF);
                                    }
                                    for (int j = 2; j <= 20; j += 2) {
                                        zhoushu[j] = 0;
                                        mTextView_zhoushu[j].setBackgroundColor(0xffffffff);
                                        mTextView_zhoushu[j].setTextColor(0xFF000000);
                                    }
                                    zhoushu_type[0] = 1;
                                    mTextView_zhoushu_type[0].setTextColor(0xFFFFFFFF);
                                    mTextView_zhoushu_type[0].setBackgroundColor(0xFFA2D231);
                                    zhoushu_type[1] = 0;
                                    mTextView_zhoushu_type[1].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[1].setBackgroundColor(0xffffffff);
                                    zhoushu_type[2] = 0;
                                    mTextView_zhoushu_type[2].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[2].setBackgroundColor(0xffffffff);
                                    break;

                                } else {
                                    zhoushu_type[0] = 0;
                                    mTextView_zhoushu_type[0].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[0].setBackgroundColor(0xffffffff);
                                    zhoushu_type[1] = 0;
                                    mTextView_zhoushu_type[1].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[1].setBackgroundColor(0xffffffff);
                                    zhoushu_type[2] = 0;
                                    mTextView_zhoushu_type[2].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[2].setBackgroundColor(0xffffffff);
                                    for (int j = 1; j <= 20; j += 1) {
                                        zhoushu[j] = 0;
                                        mTextView_zhoushu[j].setBackgroundColor(0xffffffff);
                                        mTextView_zhoushu[j].setTextColor(0xFF000000);
                                    }

                                }

                                return;
                            case R.id.textView_zhoushu_double:
                                if (zhoushu_type[1] == 0) {
                                    for (int j = 2; j <= 20; j += 2) {
                                        zhoushu[j] = 1;
                                        mTextView_zhoushu[j].setBackgroundColor(0xFFA2D231);
                                        mTextView_zhoushu[j].setTextColor(0xFFFFFFFF);
                                    }
                                    for (int j = 1; j <= 20; j += 2) {
                                        zhoushu[j] = 0;
                                        mTextView_zhoushu[j].setBackgroundColor(0xffffffff);
                                        mTextView_zhoushu[j].setTextColor(0xFF000000);
                                    }
                                    zhoushu_type[0] = 0;
                                    mTextView_zhoushu_type[0].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[0].setBackgroundColor(0xffffffff);
                                    zhoushu_type[1] = 1;
                                    mTextView_zhoushu_type[1].setTextColor(0xFFFFFFFF);
                                    mTextView_zhoushu_type[1].setBackgroundColor(0xFFA2D231);
                                    zhoushu_type[2] = 0;
                                    mTextView_zhoushu_type[2].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[2].setBackgroundColor(0xffffffff);
                                    break;

                                } else {
                                    zhoushu_type[0] = 0;
                                    mTextView_zhoushu_type[0].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[0].setBackgroundColor(0xffffffff);
                                    zhoushu_type[1] = 0;
                                    mTextView_zhoushu_type[1].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[1].setBackgroundColor(0xffffffff);
                                    zhoushu_type[2] = 0;
                                    mTextView_zhoushu_type[2].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[2].setBackgroundColor(0xffffffff);
                                    for (int j = 1; j <= 20; j += 1) {
                                        zhoushu[j] = 0;
                                        mTextView_zhoushu[j].setBackgroundColor(0xffffffff);
                                        mTextView_zhoushu[j].setTextColor(0xFF000000);
                                    }

                                }

                                return;
                            case R.id.textView_zhoushu_all:
                                if (zhoushu_type[2] == 0) {
                                    for (int j = 1; j <= 20; j += 1) {
                                        zhoushu[j] = 1;
                                        mTextView_zhoushu[j].setBackgroundColor(0xFFA2D231);
                                        mTextView_zhoushu[j].setTextColor(0xFFFFFFFF);
                                    }
                                    zhoushu_type[0] = 0;
                                    mTextView_zhoushu_type[0].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[0].setBackgroundColor(0xffffffff);
                                    zhoushu_type[1] = 0;
                                    mTextView_zhoushu_type[1].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[1].setBackgroundColor(0xffffffff);
                                    zhoushu_type[2] = 1;
                                    mTextView_zhoushu_type[2].setTextColor(0xFFFFFFFF);
                                    mTextView_zhoushu_type[2].setBackgroundColor(0xFFA2D231);

                                    break;

                                } else {
                                    zhoushu_type[0] = 0;
                                    mTextView_zhoushu_type[0].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[0].setBackgroundColor(0xffffffff);
                                    zhoushu_type[1] = 0;
                                    mTextView_zhoushu_type[1].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[1].setBackgroundColor(0xffffffff);
                                    zhoushu_type[2] = 0;
                                    mTextView_zhoushu_type[2].setTextColor(0xFF000000);
                                    mTextView_zhoushu_type[2].setBackgroundColor(0xffffffff);
                                    for (int j = 1; j <= 20; j += 1) {
                                        zhoushu[j] = 0;
                                        mTextView_zhoushu[j].setBackgroundColor(0xffffffff);
                                        mTextView_zhoushu[j].setTextColor(0xFF000000);
                                    }

                                }

                                return;
                            default:
                                break;
                        }
                        if (i != 0) {
                            if (zhoushu[i] == 0) {
                                view.setBackgroundColor(0xFFA2D231);
                                zhoushu[i] = 1;
                                mTextView_zhoushu[i].setTextColor(0xFFFFFFFF);
                            } else {
                                view.setBackgroundColor(0xffffffff);
                                zhoushu[i] = 0;
                                mTextView_zhoushu[i].setTextColor(0xFF000000);
                            }
                        }
                    }
                };


                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_1).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_2).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_3).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_4).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_5).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_6).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_7).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_8).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_9).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_10).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_11).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_12).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_13).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_14).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_15).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_16).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_17).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_18).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_19).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_20).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_single).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_double).setOnClickListener(textView_zhoushu_onclick);
                relativelayout_schedule_add_zhoushu.findViewById(R.id.textView_zhoushu_all).setOnClickListener(textView_zhoushu_onclick);

                new AlertDialog.Builder(mContext)
                        .setView(relativelayout_schedule_add_zhoushu)
                        .setIcon(R.mipmap.isdust)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String a="";
                                for(int i=1;i<=20;i++){
                                    if(zhoushu[i]==1){
                                    a=a+","+i;}


                                }
                                mbutton_select_zhoushu.setText(a);
//							String paynum = numEditText.getText().toString();


                            }
                        }).show();
            }

        });

        final Button mbutton_schedule_zhoushu=(Button)findViewById(R.id.button_schedule_jieshu);
        mbutton_schedule_zhoushu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDailog dp = new DatePickerDailog(mContext,
                        new DatePickerDailog.DatePickerListner() {

                            @Override
                            public void OnDoneButton(Dialog datedialog,int return_xingqi,int return_jieci) {
                                datedialog.dismiss();

                                xingqi = return_xingqi;
                                jieci = return_jieci;
                                mbutton_schedule_zhoushu.setText(Jiaowu_EmptyRoom.xingqizhuanhuan(xingqi)+"，"+Jiaowu_EmptyRoom.jiecizhuanhuan(jieci));
                                System.out.println("xingqi" + return_xingqi);
                            }

                            @Override
                            public void OnCancelButton(Dialog datedialog) {
                                // TODO Auto-generated method stub
                                datedialog.dismiss();
                            }
                        });
                dp.show();
            }
        });



    }
   }

