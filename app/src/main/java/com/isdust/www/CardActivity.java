package com.isdust.www;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/10/17.
 */
public class CardActivity extends BaseMainActivity {
    private String xiancheng_username,xiancheng_password,xiancheng_login_status;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private ProgressDialog dialog;

    final android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){//登录成功
                RelativeLayout mRelativeLayout_card=(RelativeLayout)findViewById(R.id.relativeLayout_card);
                mRelativeLayout_card.setVisibility(View.INVISIBLE);
                Button btnlogin = (Button) findViewById(R.id.FormCard_button_login);
                ImageButton btnquery = (ImageButton) findViewById(R.id.FormCard_button_query);
                ImageButton btnchangepwd = (ImageButton) findViewById(R.id.FormCard_button_changepwd);
                ImageButton btnloss = (ImageButton) findViewById(R.id.FormCard_button_loss);
                ImageButton btnlogout = (ImageButton) findViewById(R.id.FormCard_button_logout);

                btnlogin.setEnabled(false);
                btnquery.setEnabled(true);
                btnchangepwd.setEnabled(true);
                btnloss.setEnabled(true);
                btnlogout.setEnabled(true);
                btnlogin.setBackgroundColor(getResources().getColor(R.color.color_btn_gray));
                btnquery.setBackgroundResource(R.drawable.btn_purchhistory);
                btnchangepwd.setBackgroundResource(R.drawable.btn_changepwd);
                btnloss.setBackgroundResource(R.drawable.btn_loss);
                btnlogout.setBackgroundResource(R.drawable.btn_logout);
//				Intent intent = new Intent();
//				intent.setClass(mContext,CardListView.class);
//				mContext.startActivity(intent);
            }
            if (msg.what == 1){//显示登录状态
                Toast.makeText(mContext, xiancheng_login_status, Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 10){//网络超时
                Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
            }


        }
    };
    Runnable mRunnable_xiancheng_login = new Runnable() {
        @Override
        public void run() {
            try {
                xiancheng_login_status = isdustapp.getUsercard().login(xiancheng_username,xiancheng_password);
            } catch (Exception e) {
                e.printStackTrace();
                Message message = new Message();
                message.what = 10;
                handler.sendMessage(message);;
                return;
            }
            dialog.dismiss();
            if (xiancheng_login_status.equals("登录成功")){


                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);;

            }else {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_card,"校园卡");

        EditText textuser = (EditText) findViewById(R.id.FormCard_editText_user);
        EditText textpwd = (EditText) findViewById(R.id.FormCard_editText_pwd);
        CheckBox checkkeeppwd = (CheckBox) findViewById(R.id.FormCard_checkBox_savepwd);
        ImageButton btnquery = (ImageButton) findViewById(R.id.FormCard_button_query);
        ImageButton btnchangepwd = (ImageButton) findViewById(R.id.FormCard_button_changepwd);
        ImageButton btnloss = (ImageButton) findViewById(R.id.FormCard_button_loss);
        ImageButton btnlogout = (ImageButton) findViewById(R.id.FormCard_button_logout);
        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= getSharedPreferences("CardData", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String name =sharedPreferences.getString("username", "");
        String pwd =sharedPreferences.getString("password", "");
        Boolean keeppwd = sharedPreferences.getBoolean("keeppwd", true);
        textuser.setText(name);
        textpwd.setText(pwd);
        checkkeeppwd.setChecked(keeppwd);

        btnquery.setEnabled(false);
        btnchangepwd.setEnabled(false);
        btnloss.setEnabled(false);
        btnlogout.setEnabled(false);
    }
    public void onFormCardClick(View v) {
        EditText textuser = (EditText) findViewById(R.id.FormCard_editText_user);
        EditText textpwd = (EditText) findViewById(R.id.FormCard_editText_pwd);
        CheckBox checkkeeppwd = (CheckBox) findViewById(R.id.FormCard_checkBox_savepwd);
        Button btnlogin = (Button) findViewById(R.id.FormCard_button_login);
        ImageButton btnquery = (ImageButton) findViewById(R.id.FormCard_button_query);
        ImageButton btnchangepwd = (ImageButton) findViewById(R.id.FormCard_button_changepwd);
        ImageButton btnloss = (ImageButton) findViewById(R.id.FormCard_button_loss);
        ImageButton btnlogout = (ImageButton) findViewById(R.id.FormCard_button_logout);
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.FormCard_button_login:	//登陆按钮
                //实例化SharedPreferences对象
                SharedPreferences mySharedPreferences= getSharedPreferences("CardData",	Activity.MODE_PRIVATE);
                //实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                //用putString的方法保存数据
                editor.putString("username", textuser.getText().toString());
                if (checkkeeppwd.isChecked()) {	//记住密码
                    editor.putBoolean("keeppwd", true);
                    editor.putString("password", textpwd.getText().toString());
                }
                else {	//不记住密码
                    editor.putBoolean("keeppwd", false);
                    editor.putString("password", "");
                }
                //提交当前数据
                editor.commit();

                xiancheng_username=textuser.getText().toString();
                xiancheng_password = textpwd.getText().toString();
                if(xiancheng_username.equals("")||xiancheng_password.equals("")){
                    Toast.makeText(mContext,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = ProgressDialog.show(
                        mContext, "提示",
                        "正在登录中", true, true);

                executorService.execute(mRunnable_xiancheng_login);

                break;
            case R.id.FormCard_button_query:	//查询按钮
                intent.setClass(this,CardListView.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormCard_button_changepwd:	//改密按钮
                intent.setClass(this,CardChangePwdActivity.class);
                //启动activity
                this.startActivity(intent);
            case R.id.FormCard_button_logout:	//注销按钮
                RelativeLayout mRelativeLayout_card=(RelativeLayout)findViewById(R.id.relativeLayout_card);
                mRelativeLayout_card.setVisibility(View.VISIBLE);
                btnlogin.setEnabled(true);
                btnquery.setEnabled(false);
                btnchangepwd.setEnabled(false);
                btnloss.setEnabled(false);
                btnlogout.setEnabled(false);
                btnlogin.setBackgroundColor(getResources().getColor(R.color.color_btn_blue));
                btnquery.setBackgroundResource(R.drawable.btn_purchhistory_gray);
                btnchangepwd.setBackgroundResource(R.drawable.btn_changepwd_gray);
                btnloss.setBackgroundResource(R.drawable.btn_loss_gray);
                btnlogout.setBackgroundResource(R.drawable.btn_logout_gray);
                break;
            case R.id.FormCard_button_loss:	//挂失按钮
                intent.setClass(this, CardLossActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
        }
    }
}
