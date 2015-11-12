package com.isdust.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.isdust.www.view.IsdustDialog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/10/17.
 */
public class CardActivity extends BaseMainActivity_new {
    private String xiancheng_username,xiancheng_password,xiancheng_login_status;
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    final int request_xiaoyuanka=2,request_changgepassword=3;
    Context mContext;
//    EditText mEditText_user;
//    EditText mEditText_password ;
//    CheckBox mCheckBox_keeppwd;
    Button mImageButton_query ;
    Button mImageButton_changepwd ;
    Button mImageButton_loss ;
    Button mImageButton_logout ;
    SharedPreferences preferences_data;
    SharedPreferences.Editor preferences_editor ;
    IsdustDialog customRuningDialog;

    //private ProgressDialog dialog;

    final android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){//登录成功
                customRuningDialog.dismiss();
//                RelativeLayout mRelativeLayout_card=(RelativeLayout)findViewById(R.id.relativeLayout_card);
                //mRelativeLayout_card.setVisibility(View.INVISIBLE);
//                Button btnlogin = (Button) findViewById(R.id.FormCard_button_login);
                Button btnquery = (Button) findViewById(R.id.FormCard_button_query);
                Button btnchangepwd = (Button) findViewById(R.id.FormCard_button_changepwd);
                Button btnloss = (Button) findViewById(R.id.FormCard_button_loss);
                Button btnlogout = (Button) findViewById(R.id.FormCard_button_logout);

                btnquery.setEnabled(true);
                btnchangepwd.setEnabled(true);
                btnloss.setEnabled(true);
                btnlogout.setEnabled(true);
//                btnlogin.setBackgroundColor(getResources().getColor(R.color.color_btn_gray));
//                btnquery.setBackgroundResource(R.drawable.btn_purchhistory);
//                btnchangepwd.setBackgroundResource(R.drawable.btn_changepwd);
//                btnloss.setBackgroundResource(R.drawable.btn_loss);
//                btnlogout.setBackgroundResource(R.drawable.btn_logout);


                TextView textname = (TextView) findViewById(R.id.textView_card_name);
                TextView textnum = (TextView) findViewById(R.id.textView_card_number);
                TextView textclass = (TextView) findViewById(R.id.textView_card_class);
                TextView textbala = (TextView) findViewById(R.id.textView_card_balance);
                textname.setText(isdustapp.getUsercard().getStuName());
                textnum.setText(isdustapp.getUsercard().getStuNumber());
                textclass.setText(isdustapp.getUsercard().getStuClass());
                textbala.setText("￥" + isdustapp.getUsercard().getBalance()); //显示余额
//				Intent intent = new Intent();
//				intent.setClass(mContext,CardListView.class);
//				mContext.startActivity(intent);
            }
            if (msg.what == 1){//显示登录状态
                customRuningDialog.dismiss();
                Toast.makeText(mContext, xiancheng_login_status, Toast.LENGTH_SHORT).show();
                preferences_editor.putString("password", "");
                preferences_editor.commit();
                Intent intent = new Intent();
                intent.setClass(mContext, Card_login.class);
                startActivityForResult(intent, request_xiaoyuanka);
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
//
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
        INIT(R.layout.helper_card, "校园卡", 5);
        mContext=this;
        customRuningDialog = new IsdustDialog(mContext,
                IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框
        getview();

        mImageButton_query.setEnabled(false);
        mImageButton_changepwd.setEnabled(false);
        mImageButton_loss.setEnabled(false);
        mImageButton_logout.setEnabled(false);
        //在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        preferences_data= getSharedPreferences("CardData", Activity.MODE_PRIVATE);
        preferences_editor = preferences_data.edit();

        // 使用getString方法获得value，注意第2个参数是value的默认值
        xiancheng_username =preferences_data.getString("username", "");
        xiancheng_password =preferences_data.getString("password", "");
        if (xiancheng_username.equals("")||xiancheng_password.equals("")){
            Intent intent = new Intent();
            intent.setClass(this, Card_login.class);
            startActivityForResult(intent, request_xiaoyuanka);
        }else {
            customRuningDialog.show();
            customRuningDialog.setMessage("正在登录中");
//            dialog = ProgressDialog.show(
//                    mContext, "提示",
//                    "正在登录中", true, true);
            mExecutorService.execute(mRunnable_xiancheng_login);

        }


    }
    public void getview(){
        mImageButton_query = (Button) findViewById(R.id.FormCard_button_query);
        mImageButton_changepwd = (Button) findViewById(R.id.FormCard_button_changepwd);
        mImageButton_loss = (Button) findViewById(R.id.FormCard_button_loss);
        mImageButton_logout = (Button) findViewById(R.id.FormCard_button_logout);

    }
    public void onFormCardClick(View v) {
//        EditText textuser = (EditText) findViewById(R.id.FormCard_editText_user);
//        EditText textpwd = (EditText) findViewById(R.id.FormCard_editText_pwd);
//        CheckBox checkkeeppwd = (CheckBox) findViewById(R.id.FormCard_checkBox_savepwd);
//        Button btnlogin = (Button) findViewById(R.id.FormCard_button_login);
//        ImageButton btnquery = (ImageButton) findViewById(R.id.FormCard_button_query);
//        ImageButton btnchangepwd = (ImageButton) findViewById(R.id.FormCard_button_changepwd);
//        ImageButton btnloss = (ImageButton) findViewById(R.id.FormCard_button_loss);
//        ImageButton btnlogout = (ImageButton) findViewById(R.id.FormCard_button_logout);
        //设置传递方向
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button_network_public_login:	//登陆按钮
//                //实例化SharedPreferences对象
//                SharedPreferences mySharedPreferences= getSharedPreferences("CardData",	Activity.MODE_PRIVATE);
//                //实例化SharedPreferences.Editor对象
//                SharedPreferences.Editor editor = mySharedPreferences.edit();
//                //用putString的方法保存数据
//                editor.putString("username", textuser.getText().toString());
//                if (checkkeeppwd.isChecked()) {	//记住密码
//                    editor.putBoolean("keeppwd", true);
//                    editor.putString("password", textpwd.getText().toString());
//                }
//                else {	//不记住密码
//                    editor.putBoolean("keeppwd", false);
//                    editor.putString("password", "");
//                }
//                //提交当前数据
//                editor.commit();
//
//                xiancheng_username=textuser.getText().toString();
//                xiancheng_password = textpwd.getText().toString();
//                if(xiancheng_username.equals("")||xiancheng_password.equals("")){
//                    Toast.makeText(mContext,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//                mExecutorService.execute(mRunnable_xiancheng_login);

                break;
            case R.id.FormCard_button_query:	//查询按钮
                intent.setClass(this,CardListView.class);
                //启动activity
                this.startActivity(intent);
                break;
            case R.id.FormCard_button_changepwd:	//改密按钮
                intent.setClass(this, CardChangePwdActivity.class);
                //启动activity
                this.startActivityForResult(intent, request_changgepassword);
                break;
            case R.id.FormCard_button_logout:	//注销按钮
                RelativeLayout mRelativeLayout_card=(RelativeLayout)findViewById(R.id.relativelayout_network_public_card);
                //mRelativeLayout_card.setVisibility(View.VISIBLE);
//                btnlogin.setEnabled(true);
                mImageButton_query.setEnabled(false);
                mImageButton_changepwd.setEnabled(false);
                mImageButton_loss.setEnabled(false);
                mImageButton_logout.setEnabled(false);
//                btnlogin.setBackgroundColor(getResources().getColor(R.color.color_btn_blue));

                intent.setClass(mContext, Card_login.class);
                startActivityForResult(intent, request_xiaoyuanka);
                break;
            case R.id.FormCard_button_loss:	//挂失按钮
                intent.setClass(this, CardLossActivity.class);
                //启动activity
                this.startActivity(intent);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //注意分清楚 requestCode和resultCode，后者是setResult里设置的

        switch (requestCode){
            case request_xiaoyuanka:
                switch (resultCode){
                    case RESULT_OK:
                        Bundle bundle=data.getExtras();
                        xiancheng_username = bundle.getString("username");
                        xiancheng_password = bundle.getString("password");
                        customRuningDialog.show();
                        customRuningDialog.setMessage("正在登录中");
//                        dialog = ProgressDialog.show(
//                                mContext, "提示",
//                                "正在登录中", true, true);
                        mExecutorService.execute(mRunnable_xiancheng_login);
                        break;
                }
                break;
            case request_changgepassword:
                switch (resultCode){
                    case RESULT_OK:
                        Toast.makeText(mContext,"密码修改成功，请重新登录",Toast.LENGTH_SHORT);
                        preferences_editor.putString("password", "");
                        preferences_editor.commit();
                        Intent intent=new Intent();
                        mImageButton_query.setEnabled(false);
                        mImageButton_changepwd.setEnabled(false);
                        mImageButton_loss.setEnabled(false);
                        mImageButton_logout.setEnabled(false);
//                btnlogin.setBackgroundColor(getResources().getColor(R.color.color_btn_blue));
                        mImageButton_query.setBackgroundResource(R.drawable.btn_purchhistory_gray);
                        mImageButton_changepwd.setBackgroundResource(R.drawable.btn_changepwd_gray);
                        mImageButton_loss.setBackgroundResource(R.drawable.btn_loss_gray);
                        mImageButton_logout.setBackgroundResource(R.drawable.btn_logout_gray);
                        intent.setClass(mContext, Card_login.class);
                        startActivityForResult(intent, request_xiaoyuanka);
                        break;
                    case RESULT_CANCELED:

                        break;
                }

        }

    }   //处理子页面返回的数据
}
