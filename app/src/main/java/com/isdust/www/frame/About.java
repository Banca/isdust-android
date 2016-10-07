package com.isdust.www.frame;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.isdust.www.R;
import com.umeng.analytics.MobclickAgent;
import pw.isdust.isdust.update.UpdateChecker;

//import com.isdust.www.R;

//import static u.aly.au.R;


/**
 *
 * Created by zor on 2016/9/28.
 */
@SuppressLint("ValidFragment")
public class About extends Fragment {
    private View v = null;
    private TextView tx;
    private Activity thisActivity;



    public About(Activity activity) {
        this.thisActivity=activity;
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MobclickAgent.onEvent(thisActivity, "About");
        v = inflater.inflate(R.layout.helper_about, null);


        Button mButton_feedback=(Button)v.findViewById(R.id.button_feedback);
        TextView textView = (TextView)v.findViewById(R.id.title_bar_name);
        Button mButton_update=(Button)v.findViewById(R.id.button_update);
        textView.setText("关于我们");

        mButton_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果发生错误，请查看logcat日志
                FeedbackAPI.openFeedbackActivity(thisActivity);
//如果希望使用Fragment方式打开请调用一下API
                FeedbackAPI.getFeedbackFragment();
                //FeedbackAPI.initAnnoy(Context context, String appKey)
            }
        });
        mButton_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateChecker.checkForDialog(thisActivity,true);
            }
        });
        return v;

    }

//            @Override
//            public void onClick(View view) {
//                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//                    @Override
//                    public void onUpdateReturned(int arg0, UpdateResponse arg1) {
//                        // TODO Auto-generated method stub
//                        switch (arg0) {
//                            case UpdateStatus.Yes: // has update
//                                UmengUpdateAgent.showUpdateDialog(thisActivity, arg1);
//                                break;
//                            case UpdateStatus.No: // has no update
//                                Toast.makeText(thisActivity, getString(R.string.umeng_isNewest), Toast.LENGTH_SHORT)
//                                        .show();
//                                break;
//                            case UpdateStatus.NoneWifi: // none wifi
//                                Toast.makeText(thisActivity, getString(R.string.umeng_notWifi), Toast.LENGTH_SHORT)
//                                        .show();
//                                break;
//                            case UpdateStatus.Timeout: // time out
//                                Toast.makeText(thisActivity, getString(R.string.umeng_timeout), Toast.LENGTH_SHORT)
//                                        .show();
//                                break;
//                        }
//                    }
//                });
//
//                UmengUpdateAgent.forceUpdate(thisActivity);
//            }
//        });
//
//        return v;
//    }

}
