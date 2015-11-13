package pw.isdust.isdust.function;

import java.io.IOException;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/17.
 */
public class Networklogin_ChinaUnicom {
    public Networklogin_ChinaUnicom(){

    }
    String xiaxian;
    Http mHttp;

    public String login(String user,String password) throws IOException {
        String submit="DDDDD="+user+"&upass="+Networklogin_CMCC.encodepassword(password)+"&R1=0&R2=1&para=00&0MKKey=123456";
        String html= mHttp.post_string("http://10.249.255.253/", submit, "gb2312");
        if(html.contains("登录成功窗")){
            return "登录成功";
        }
        if(html.contains("Msg=01")&&html.contains("msga=''")){
            return "密码错误";
        }
        return "";
    }
    public void logout() throws IOException {
        mHttp.get_string("http://10.249.255.253/F.htm");



    }

}
