package pw.isdust.isdust.function;

import java.net.URLEncoder;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/10.
 */
public class Zhengfang {
    public Zhengfang(){}
    public String losin(String user,String pwd){
        String text_web;
        text_web= Http.get_string("http://192.168.100.136/default_ysdx.aspx");
        String VIEWSTATE=Shangwangdenglu.zhongjian(text_web, "name=\"__VIEWSTATE\" value=\"", "\" />", 0);
        try {
            VIEWSTATE= URLEncoder.encode(VIEWSTATE, "utf-8");

        }catch (Exception e){
            System.out.println(e);
        }
        String post_submit="__VIEWSTATE="+VIEWSTATE+"&TextBox1="+user+"&TextBox2="+pwd+"&RadioButtonList1=%D1%A7%C9%FA&Button1=++%B5%C7%C2%BC++";
        text_web=Http.post_string("http://192.168.100.136/default_ysdx.aspx", post_submit, "gb2312");

        return "";
    }
}
