package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.umeng.onlineconfig.OnlineConfigAgent;

import pw.isdust.isdust.function.Networklogin_CMCC;
import pw.isdust.isdust.function.RSACryptUtil;

/**
 * Created by wzq on 16/1/18.
 */
public class Jiaowu_tice_search extends BaseSubPageActivity_new {
    Button mButton_search;
    EditText mEditText_name;
    EditText mEditText_xuehao;
    String publickey="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_jiaowu_tice_search, "体质健康测试成绩查询");
        mEditText_name=(EditText)findViewById(R.id.edittext_tice_name);
        mEditText_xuehao=(EditText)findViewById(R.id.edittext_tice_xuehao);

        mButton_search=(Button)findViewById(R.id.Jiaowu_tice_search);
        publickey= OnlineConfigAgent.getInstance().getConfigParams(mContext, "tice_publickey");
        publickey=publickey.replace("\r", "");
        publickey=publickey.replace("\n","");
        System.out.println(publickey);

        mButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mEditText_name.getText().toString();
                String xuehao=mEditText_xuehao.getText().toString();
                String address=addressgenerate(name,xuehao);
                Intent intent = new Intent();
                intent.putExtra("address",address);
                intent.setClass(mContext,Jiaowu_tice_view.class);
                startActivity(intent);
                System.out.println(address);

            }
        });
    }
    protected String addressgenerate(String name,String xuehao){
        long time = System.currentTimeMillis()/1000;
        String result=name+","+xuehao+","+time;
        String submit="";
        try {
            submit= RSACryptUtil.encryptByPublicKey_string(result, publickey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        submit=submit.replace("==","");
        String address="http://tice.isdust.com/chaxun.php?data="+submit.replace("+","%2B")+"&verification="+Networklogin_CMCC.md5(submit+"qsfdwewc"+time)+"&time="+time;
        return address;
    }

}
