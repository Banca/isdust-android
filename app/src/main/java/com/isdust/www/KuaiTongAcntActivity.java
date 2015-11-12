package com.isdust.www;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;

/**
 * 快通账号管理页面
 * Created by Administrator on 2015/10/25.
 */
public class KuaiTongAcntActivity extends BaseSubPageActivity_new {
    private EditText textuser,textpwd;
    private Button btn_ok;
    private CheckBox check_savepwd;
    private SharedPreferences preferences_data;
    private SharedPreferences.Editor preferences_editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_kuaitong_account, "快通有线账号");
        //实例化SharedPreferences对象
        preferences_data = mContext.getSharedPreferences("KuaiTongData", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        preferences_editor = preferences_data.edit();
        findView();
        getLocalData();
    }

    private void findView() {
        textuser = (EditText) findViewById(R.id.edittext_schoolcard_user);
        textpwd = (EditText) findViewById(R.id.edittext_schoolcard_pwd);
        btn_ok = (Button) findViewById(R.id.btn_kuaitong_changepwd);
        check_savepwd = (CheckBox) findViewById(R.id.check_schoolcard_savepwd);
    }   //连接控件

    private void getLocalData() {
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String name =preferences_data.getString("username", "");
        String pwd =preferences_data.getString("password", "");
        Boolean keeppwd;
        textuser.setText(name);
        textpwd.setText(pwd);
        keeppwd = preferences_data.getBoolean("keeppwd", true);
        check_savepwd.setChecked(keeppwd);
    }   //读取本地数据

    public void onFormKuaiTongAcntClick(View v) {
        switch (v.getId()) {
            case R.id.btn_schoolcard_ok:
                String result,str_user,str_pwd;
                str_user = textuser.getText().toString();
                str_pwd = textpwd.getText().toString();

                //用putString的方法保存数据
                preferences_editor.putString("username", str_user);
                if (check_savepwd.isChecked()) {	//记住密码
                    preferences_editor.putBoolean("keeppwd", true);
                    preferences_editor.putString("password", str_pwd);
                }
                else {	//不记住密码
                    preferences_editor.putBoolean("keeppwd", false);
                    preferences_editor.putString("password", "");
                }
                //提交当前数据
                preferences_editor.commit();

                Intent intent=new Intent();
                intent.putExtra("str_user", str_user);
                intent.putExtra("str_pwd", str_pwd);
                setResult(RESULT_OK, intent); //返回结果
                finish();
                break;
        }
    }

}
