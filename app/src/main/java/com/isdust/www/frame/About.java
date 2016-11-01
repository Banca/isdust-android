package com.isdust.www.frame;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
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

/**
 *
 * Created by zor on 2016/9/28.
 */
@SuppressLint("ValidFragment")
public class About extends Fragment {
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MobclickAgent.onEvent(mContext, "About");
        View v = inflater.inflate(R.layout.helper_about, null);


        Button mButton_feedback=(Button) v.findViewById(R.id.button_feedback);
        TextView textView = (TextView) v.findViewById(R.id.title_bar_name);
        Button mButton_update=(Button) v.findViewById(R.id.button_update);
        textView.setText("关于我们");

        mButton_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackAPI.openFeedbackActivity(mContext);
                FeedbackAPI.getFeedbackFragment();
                //FeedbackAPI.initAnnoy(Context context, String appKey)
            }
        });
        mButton_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateChecker.checkForDialog(mContext,true);
            }
        });
        return v;

    }



}
