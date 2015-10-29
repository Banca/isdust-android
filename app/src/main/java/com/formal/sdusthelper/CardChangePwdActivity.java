package com.formal.sdusthelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.formal.sdusthelper.baseactivity.BaseSubPageActivity;

/**
 * Created by Administrator on 2015/10/16.
 */
public class CardChangePwdActivity extends BaseSubPageActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_cardchangepwd,"密码修改");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FormCard_button_changeok:
                EditText textid = (EditText) findViewById(R.id.FormCard_editText_IDcard);
                EditText textoldpwd = (EditText) findViewById(R.id.FormCard_editText_oldpwd);
                EditText textnewpwd = (EditText) findViewById(R.id.FormCard_editText_newpwd);
                EditText textrenewpwd = (EditText) findViewById(R.id.FormCard_editText_renewpwd);
                String strid = textid.getText().toString();
                String stroldpwd = textoldpwd.getText().toString();
                String strnewpwd = textnewpwd.getText().toString();
                String strrenewpwd = textrenewpwd.getText().toString();
                if (strnewpwd.equals(strrenewpwd)) {
                    String result;
                    result = isdustapp.getUsercard().changepassword(stroldpwd,strnewpwd,strid);
                    Toast.makeText(this, result, 1000).show();
                    if (result.equals("修改密码成功"))
                        finish();
                }
                else
                    Toast.makeText(this, "新密码前后不一致", 1000).show();
                break;
        }
    }
}
