package com.isdust.www;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity;
import com.isdust.www.view.IsdustDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

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

    private String kuaitong_user,kuaitong_pwd;  //快通账号密码
    private String smartcard_user,smartcard_pwd;  //SmartCard账号密码
    private String smartcard_result = "";

    protected IsdustDialog customRuningDialog;  //自定义运行中提示框

    private String paynum; //充值金额
    private String oldpwd,newpwd1,newpwd2;  //改密

    protected void onCreate(Bundle savedInstanceState) {
        MobclickAgent.onEvent(this, "network_kuaitong");

        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_kuaitong, "快通有线");
        obj_kuaitong = new Network_Kuaitong(this);
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框
        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= getSharedPreferences("KuaiTongData", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        kuaitong_user =sharedPreferences.getString("username", "");
        kuaitong_pwd =sharedPreferences.getString("password", "");
        sharedPreferences= getSharedPreferences("CardData", Activity.MODE_PRIVATE); //读取校园卡账号密码
        smartcard_user =sharedPreferences.getString("username", "");
        smartcard_pwd =sharedPreferences.getString("password", "");

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
                if (smartcard_result.equals("登录成功"))
                    dealPay();
                break;
            case R.id.btn_kuaitong_changepwd: //改密
                if (smartcard_result.equals("登录成功"))
                    dealChangePwd();
                break;
            case R.id.btn_kuaitong_switch: //改密
                if (smartcard_result.equals("登录成功"))
                    dealSwitch();
                break;
        }
    }

    private void dealPay() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView userTextView = new TextView(this);
        userTextView.setText("充值账户:"+smartcard_user);
        userTextView.setTextSize(18f);
        TextView numTextView = new TextView(this);
        numTextView.setText("请输入充值金额（元）:");
        numTextView.setTextSize(18f);

        final EditText numEditText = new EditText(this);    //充值输入框
        if (android.os.Build.VERSION.SDK_INT > 15)
            numEditText.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        else
            numEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        layout.addView(userTextView);
        layout.addView(numTextView);
        layout.addView(numEditText);

        new AlertDialog.Builder(this).setTitle("充值")
                .setView(layout)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        paynum = numEditText.getText().toString();
                        startPayThread();
                    }
                }).show();
    }   //处理充值任务

    private void dealSwitch() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView stateTextView = new TextView(this);
        stateTextView.setText("当前状态:"+carddata[0]);
        stateTextView.setTextSize(18f);
        Switch stateSwitch = new Switch(this);
        stateSwitch.setChecked(carddata[0].equals("正常"));
        stateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked)  //Switch之前状态为关
                    Toast.makeText(KuaiTongActivity.this,"1",Toast.LENGTH_SHORT);
                else           //Switch之前状态为开
                    Toast.makeText(KuaiTongActivity.this,"2",Toast.LENGTH_SHORT);
            }
        });
        layout.addView(stateTextView);
        layout.addView(stateSwitch);

        new AlertDialog.Builder(this).setTitle("开停机")
                .setView(layout)
                .setNegativeButton("关闭", null).show();
    }   //处理充值任务

    private void dealChangePwd() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView text1 = new TextView(this);
        TextView text2 = new TextView(this);
        TextView text3 = new TextView(this);
        text1.setText("原密码：");
        text2.setText("新密码：");
        text3.setText("确认密码：");
        text1.setTextSize(18f);
        text2.setTextSize(18f);
        text3.setTextSize(18f);

        final EditText edit_orgpwd = new EditText(this);    //原密码输入框
        final EditText edit_newpwd1 = new EditText(this);    //新密码输入框
        final EditText edit_newpwd2 = new EditText(this);    //确认密码输入框
        edit_orgpwd.setInputType(InputType.TYPE_CLASS_NUMBER);
        edit_newpwd1.setInputType(InputType.TYPE_CLASS_NUMBER);
        edit_newpwd2.setInputType(InputType.TYPE_CLASS_NUMBER);

        layout.addView(text1);
        layout.addView(edit_orgpwd);
        layout.addView(text2);
        layout.addView(edit_newpwd1);
        layout.addView(text3);
        layout.addView(edit_newpwd2);

        new AlertDialog.Builder(this).setTitle("密码修改")
                .setView(layout)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        oldpwd = edit_orgpwd.getText().toString();
                        newpwd1 = edit_newpwd1.getText().toString();
                        newpwd2 = edit_newpwd2.getText().toString();
                        startChangePwdThread();
                    }
                }).show();
    }   //处理改密任务

    private void startAcntActivity() {
        Intent intent = new Intent();
        intent.setClass(this, KuaiTongAcntActivity.class);
        startActivityForResult(intent, KuaiTongAcntActivity.RESULT_CODE);
    }   //以获取结果的方式打开账户页面

//    private void startCardAcntActivity() {
//        Intent intent = new Intent();
//        intent.setClass(this, KuaiTongAcntActivity.class);
//        startActivityForResult(intent, KuaiTongAcntActivity.RESULT_CODE);
//    }   //以获取结果的方式打开校园卡账户页面

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
        textuser.setText("用户："+kuaitong_user);
        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在获取数据...");
        Thread threadRe = new Thread(new Runnable() {
            @Override
            public void run() {
                final String result;

                try {
                    result = obj_kuaitong.loginKuaitong(kuaitong_user,kuaitong_pwd);
                } catch (IOException e) {
                    Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                KuaiTongActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("登录成功")) {
                            imgstate.setBackgroundResource(R.drawable.pwd);
                            try {
                                carddata = obj_kuaitong.getKuaitongInfo();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            textuserstate.setText("当前状态:"+carddata[11]);//用户状态
                            textpackage.setText("当前套餐:"+carddata[2]);//当前套餐
                            try {
                                carddata = obj_kuaitong.getKuaitongInfo();
                            } catch (IOException e) {
                                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
                                return;                            }
                            textuserstate.setText(textuserstate.getText().toString()+carddata[11]);//用户状态
                            textpackage.setText(textpackage.getText().toString()+carddata[2]);//当前套餐
                            textflow.setText("   剩余流量："+carddata[5]);
                            textbala.setText("  下月余额："+carddata[13]);
                            InitSmartCard();    //登录SmartCard
                        }
                        else     //出错才提示
                            if (customRuningDialog.isShowing())
                                Toast.makeText(KuaiTongActivity.this, result, Toast.LENGTH_SHORT).show();
                        customRuningDialog.hide();
                    }
                });
            }
        });     //登录线程
        threadRe.start();
    }   //登录快通有线微信内置查询接口

    private void InitSmartCard() {
        final String[] result = new String[1];

        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在配置环境...");

        final Thread threadRe = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result[0] = obj_kuaitong.loginSmartCard(smartcard_user, smartcard_pwd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                KuaiTongActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        smartcard_result = result[0];
                        if (!result[0].equals("登录成功") && customRuningDialog.isShowing()){
                            Toast.makeText(KuaiTongActivity.this, result[0], Toast.LENGTH_SHORT).show();
                        }   //登录不成功，显示错误信息
                        customRuningDialog.hide();
                    }
                });
            }
        });     //登录线程
        threadRe.start();
    }   //登录SmartCard

    private void startPayThread() {
        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在支付...");

        final Thread threadRe = new Thread(new Runnable() {
            @Override
            public void run() {
                final String result;
                result = obj_kuaitong.pay(paynum);
                KuaiTongActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (customRuningDialog.isShowing()) {
                            Toast.makeText(KuaiTongActivity.this, result, Toast.LENGTH_SHORT).show();
                            customRuningDialog.hide();
                        }   //加载框还在，才提示
                    }
                });
            }
        });     //支付线程
        threadRe.start();
    }   //支付paynum

    private void startChangePwdThread() {
        customRuningDialog.show();    //打开等待框
        customRuningDialog.setMessage("正在修改...");

        final Thread threadRe = new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = "";
                try {
                    obj_kuaitong.gaimima(oldpwd,newpwd1,newpwd2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                KuaiTongActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (customRuningDialog.isShowing()) {
                            Toast.makeText(KuaiTongActivity.this, result, Toast.LENGTH_SHORT).show();
                            customRuningDialog.hide();
                        }   //加载框还在，才提示
                    }
                });
            }
        });     //改密线程
        threadRe.start();
    }   //改密

}
