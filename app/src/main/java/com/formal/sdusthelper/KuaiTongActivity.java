package com.formal.sdusthelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;
import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;

import pw.isdust.isdust.function.Network_Kuaitong;

/**
 * Created by Administrator on 2015/10/31.
 */
public class KuaiTongActivity extends BaseMainActivity {
    private Network_Kuaitong obj_kuaitong;
    private String carddata[];
    private TextView textuser,textuserstate,textpackage,
            textflow,textbala;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_kuaitong, "快通有线");
        findView();
        obj_kuaitong = new Network_Kuaitong();
        String result;
        result = obj_kuaitong.loginKuaitong("1401061309","46386977");
        Toast.makeText(this,result, Toast.LENGTH_SHORT).show();
        carddata = obj_kuaitong.getKuaitongInfo();
        textuserstate.setText(carddata[11]);
        textpackage.setText(carddata[2]);
        textflow.setText("   剩余流量："+carddata[5]);
        textbala.setText("  下月余额："+carddata[13]);
    }

    private void findView() {
        textuser = (TextView) findViewById(R.id.text_kuaitong_user);
        textuserstate = (TextView) findViewById(R.id.text_kuaitong_userstate);
        textpackage = (TextView) findViewById(R.id.text_kuaitong_package);
        textflow = (TextView) findViewById(R.id.text_kuaitong_flowmeter);
        textbala = (TextView) findViewById(R.id.text_kuaitong_balance);
    }
}
