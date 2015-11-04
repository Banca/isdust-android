package com.formal.sdusthelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;
import com.formal.sdusthelper.view.IsdustDialog;
import com.umeng.analytics.MobclickAgent;

import pw.isdust.isdust.function.Network_Kuaitong;

/**
 * Created by Administrator on 2015/10/31.
 */
public class KuaiTongActivity extends BaseMainActivity {
    private Network_Kuaitong obj_kuaitong;
    private String carddata[];
    private TextView textuser,textuserstate,textpackage,
            textflow,textbala;
    private ImageView imgstate;

    private String kuaitong_user,kuaitong_pwd;
    protected IsdustDialog customRuningDialog;  //自定义运行中提示框

    protected void onCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(this, "network_kuaitong");

        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_kuaitong, "快通有线");
        obj_kuaitong = new Network_Kuaitong();
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框
        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= getSharedPreferences("KuaiTongData", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        kuaitong_user =sharedPreferences.getString("username", "");
        kuaitong_pwd =sharedPreferences.getString("password", "");
        findView();
        getData();
    }

    public void onFormKuaiTongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_kuaitong_infomation:  //切换账户
                Intent intent = new Intent(KuaiTongActivity.this,KuaiTongInfoActivity.class) ;
                Bundle bundle = new Bundle();
                bundle.putSerializable("KuaiTongData", carddata) ;
                intent.putExtras(bundle) ;
                startActivity(intent);
                break;
            case R.id.btn_kuaitong_su:  //切换账户
                startAcntActivity();
                break;
            case R.id.btn_kuaitong_pay: //充值
                dealPay();
                break;
        }
    }

    private void dealPay() {

    }   //处理充值任务
    
    private void startAcntActivity() {
        Intent intent = new Intent();
        intent.setClass(this, KuaiTongAcntActivity.class);
        startActivityForResult(intent, KuaiTongAcntActivity.RESULT_CODE);
    }   //以获取结果的方式打开账户页面

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的
        if (resultCode==KuaiTongAcntActivity.RESULT_CODE)
        {
            Bundle bundle=data.getExtras();
            kuaitong_user = bundle.getString("str_user");
            kuaitong_pwd = bundle.getString("str_pwd");
            getData();
        }
    }   //处理子页面返回的数据

    private void findView() {
        textuser = (TextView) findViewById(R.id.text_kuaitong_user);
        textuserstate = (TextView) findViewById(R.id.text_kuaitong_userstate);
        textpackage = (TextView) findViewById(R.id.text_kuaitong_package);
        textflow = (TextView) findViewById(R.id.text_kuaitong_flowmeter);
        textbala = (TextView) findViewById(R.id.text_kuaitong_balance);
        imgstate = (ImageView) findViewById(R.id.image_kuaitong_state);
    }

    private void getData() {
        if (kuaitong_user.isEmpty()||kuaitong_pwd.isEmpty()) {
            startAcntActivity();
            return;
        }   //有空数据，打开账号管理
        textuser.setText(textuser.getText().toString()+kuaitong_user);
        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在获取数据...");
        Thread threadRe = new Thread(new Runnable() {
            @Override
            public void run() {
                final String result;
                result = obj_kuaitong.loginKuaitong(kuaitong_user,kuaitong_pwd);
                KuaiTongActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("登录成功")){
                            imgstate.setBackgroundResource(R.drawable.pwd);
                            carddata = obj_kuaitong.getKuaitongInfo();
                            textuserstate.setText(textuserstate.getText().toString()+carddata[11]);//用户状态
                            textpackage.setText(textpackage.getText().toString()+carddata[2]);//当前套餐
                            textflow.setText("   剩余流量："+carddata[5]);
                            textbala.setText("  下月余额："+carddata[13]);
                        }
                        customRuningDialog.hide();
                        Toast.makeText(KuaiTongActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });     //登录线程
        threadRe.start();
    }
}
