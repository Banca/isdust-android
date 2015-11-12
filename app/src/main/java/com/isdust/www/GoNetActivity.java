package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;

import pw.isdust.isdust.function.Networkjudge;

/**
 * Created by Administrator on 2015/10/17.
 */
public class GoNetActivity extends BaseMainActivity_new {
    Networkjudge mNetworkjudge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_gonet, "上网登录", 1);
        mNetworkjudge=new Networkjudge(mContext);

    }
    public void onFormGoNetClick(View v) {
        //设置传递方向
        int networktype;
        networktype=mNetworkjudge.judgetype();
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.FormGoNet_button_cmcc:
                if (networktype!=1){
                    Toast.makeText(mContext,"请连接CMCC网络后在进入此功能",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.setClass(this,GoNetCMCCActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormGoNet_button_chinaunicom:
                if (networktype!=2){
                    Toast.makeText(mContext,"请连接ChinaUnicom网络后在进入此功能",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.setClass(this,GoNetChinaUnicomActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
        }
    }
}
