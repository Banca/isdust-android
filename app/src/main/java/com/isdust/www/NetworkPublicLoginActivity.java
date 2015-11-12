package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;

/**
 * cmcc账号管理页面
 * Restructed by WZQ 2015.11.10
 */
public class NetworkPublicLoginActivity extends BaseSubPageActivity_new {//城市热点登录
    final int type_cmcc=1,type_chinaunicom=2,type_default=0;
    int selftype;
    public final static int RESULT_CODE=1;
    EditText mEditText_chengshiredian_user,mEditText_chengshiredian_password;
    Button mButton_ok;
    CheckBox mCheckBox_savepassword;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mSharedPreferences_editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        selftype=intent.getIntExtra("type", 0);

        if (selftype==type_cmcc){
            INIT(R.layout.activity_network_public, "CMCC一层登录账号");
            mSharedPreferences = mContext.getSharedPreferences("NetworkLogin", Activity.MODE_PRIVATE);
            mSharedPreferences_editor=mSharedPreferences.edit();
            getview();
            String name =mSharedPreferences.getString("network_cmcc_chengshiredian_user", "");
            String pwd =mSharedPreferences.getString("network_cmcc_chengshiredian_password", "");
            Boolean keeppwd = mSharedPreferences.getBoolean("network_cmcc_chengshiredian_keeppword", true);;
            mEditText_chengshiredian_user.setText(name);
            mEditText_chengshiredian_password.setText(pwd);
            mCheckBox_savepassword.setChecked(keeppwd);
//            //实例化SharedPreferences对象
//
//            //实例化SharedPreferences.Editor对象
//            preferences_editor = preferences_data.edit();

        }else if (selftype==type_chinaunicom){
            INIT(R.layout.activity_network_public, "ChinaUnicom登录");
            mSharedPreferences = mContext.getSharedPreferences("NetworkLogin", Activity.MODE_PRIVATE);
            mSharedPreferences_editor=mSharedPreferences.edit();
            getview();
            String name =mSharedPreferences.getString("network_ChinaUnicom_user", "");
            String pwd =mSharedPreferences.getString("network_ChinaUnicom_password", "");
            Boolean keeppwd = mSharedPreferences.getBoolean("network_ChinaUnicom_keeppword", true);;
            mEditText_chengshiredian_user.setText(name);
            mEditText_chengshiredian_password.setText(pwd);
            mCheckBox_savepassword.setChecked(keeppwd);

        }



    }

    private void getview() {
        mEditText_chengshiredian_user = (EditText) findViewById(R.id.EditText_network_public_login_user);
        mEditText_chengshiredian_password = (EditText) findViewById(R.id.EditText_network_public_login_password);
        mButton_ok = (Button) findViewById(R.id.button_network_public_login);
        mCheckBox_savepassword = (CheckBox) findViewById(R.id.checkbox_network_public_savepassword);
        mButton_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user,password;
                user=mEditText_chengshiredian_user.getText().toString();
                password=mEditText_chengshiredian_password.getText().toString();
                if (user==""||password==""){
                    Toast.makeText(mContext,"请输入您的账号和密码",Toast.LENGTH_SHORT);
                    return;
                }
                mSharedPreferences_editor.putString("network_cmcc_chengshiredian_user", user);

                if (selftype==type_cmcc){
                    if (mCheckBox_savepassword.isChecked()) {    //记住密码
                        mSharedPreferences_editor.putBoolean("network_cmcc_chengshiredian_keeppword", true);
                        mSharedPreferences_editor.putString("network_cmcc_chengshiredian_password", password);
                    } else {    //不记住密码
                        mSharedPreferences_editor.putBoolean("network_cmcc_chengshiredian_keeppword", false);
                        mSharedPreferences_editor.putString("network_cmcc_chengshiredian_password", "");
                    }
                    //提交当前数据
                    mSharedPreferences_editor.commit();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("cmcc_chengshiredian_user", user);
                    bundle.putString("cmcc_chengshiredian_password", password);
                    intent.putExtras(bundle);
                    NetworkPublicLoginActivity.this.setResult(RESULT_OK, intent);

                    finish();

                }
                if (selftype==type_chinaunicom){
                    mSharedPreferences_editor.putString("network_ChinaUnicom_user", user);

                    if (mCheckBox_savepassword.isChecked()) {    //记住密码
                        mSharedPreferences_editor.putBoolean("network_ChinaUnicom_keeppword", true);
                        mSharedPreferences_editor.putString("network_ChinaUnicom_password", password);
                    } else {    //不记住密码
                        mSharedPreferences_editor.putBoolean("network_ChinaUnicom_keeppword", false);
                        mSharedPreferences_editor.putString("network_ChinaUnicom_password", "");
                    }
                    //提交当前数据
                    mSharedPreferences_editor.commit();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("ChinaUnicom_user", user);
                    bundle.putString("ChinaUnicom_password", password);
                    intent.putExtras(bundle);
                    NetworkPublicLoginActivity.this.setResult(RESULT_OK, intent);

                    finish();

                }
            }
        });
    }   //连接控件




//    public void sendDynamicPwd() throws IOException {
//        String result;
//        Networklogin_CMCC obj_cmcc = new Networklogin_CMCC();
//        result = obj_cmcc.cmcc_getyanzheng(text_cmcc_user_sec.getText().toString());
//        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
//    }   //申请动态密码
    @Override
    public void onTitleBarClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back_btn:
                this.setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
