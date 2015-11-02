package com.formal.sdusthelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;

/**
 * chinaunicom账号管理页面
 * Created by Administrator on 2015/10/25.
 */
public class GoNetChinaUnicomAcntActivity extends BaseSubPageActivity {
    public final static int RESULT_CODE=2;
    private EditText text_cmcc_user_first,text_cmcc_user_sec,
            text_cmcc_pwd_first,text_cmcc_pwd_sec;
    private Button btn_ok;
    private CheckBox check_savepwd;
    SharedPreferences preferences_data;
    SharedPreferences.Editor preferences_editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_chinaunicom_account, "ChinaUnicom");
        //实例化SharedPreferences对象
        preferences_data = mContext.getSharedPreferences("ChinaUnicomData", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        preferences_editor = preferences_data.edit();
        findView();
        getLocalData();
    }

    private void findView() {
        text_cmcc_user_first = (EditText) findViewById(R.id.cmcc_first_editText_user);

        text_cmcc_pwd_first = (EditText) findViewById(R.id.cmcc_first_editText_pwd);

        btn_ok = (Button) findViewById(R.id.btn_cmccacnt_ok);
        check_savepwd = (CheckBox) findViewById(R.id.checkBox_cmcc_savepwd);
    }   //连接控件

    private void getLocalData() {
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String name =preferences_data.getString("username_first", "");
        String pwd =preferences_data.getString("password_first", "");
        Boolean keeppwd;
        text_cmcc_user_first.setText(name);
        text_cmcc_pwd_first.setText(pwd);
        keeppwd = preferences_data.getBoolean("keeppwd", true);
        check_savepwd.setChecked(keeppwd);


    }   //读取本地数据

    public void onFormCMCCAcntlick(View v) {
        switch (v.getId()) {
            case R.id.btn_cmccacnt_ok:
                String result,str_user1,str_pwd1,str_user2,str_pwd2;
                str_user1 = text_cmcc_user_first.getText().toString();
                str_pwd1 = text_cmcc_pwd_first.getText().toString();
                str_user2 = "";
                str_pwd2 = "";

                //用putString的方法保存数据
                preferences_editor.putString("username_first", str_user1);
                if (check_savepwd.isChecked()) {	//记住密码
                    preferences_editor.putBoolean("keeppwd", true);
                    preferences_editor.putString("password_first", str_pwd1);
                }
                else {	//不记住密码
                    preferences_editor.putBoolean("keeppwd", false);
                    preferences_editor.putString("password_first", "");
                }
                //提交当前数据
                preferences_editor.commit();

                Intent intent=new Intent();
                intent.putExtra("str_user1", str_user1);
                intent.putExtra("str_pwd1", str_pwd1);
                intent.putExtra("str_user2", str_user2);
                intent.putExtra("str_pwd2", str_pwd2);
                setResult(RESULT_CODE, intent); //返回结果
                finish();
                break;
        }
    }
}
