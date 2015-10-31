package pw.isdust.isdust.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pw.isdust.isdust.Http;
import pw.isdust.isdust.function.baseclass.BaseNetworklogin;

/**
 * Created by wzq on 15/10/17.
 */
public class Networklogin_ChinaUnicom extends BaseNetworklogin {
    public Networklogin_ChinaUnicom(){

    }
    String xiaxian;
    Http mHttp;

    public String login(String user,String password,String user2,String password2){
        String submit="DDDDD="+user+"&upass="+encodepassword(password)+"&R1=0&R2=1&para=00&0MKKey=123456";
        String html= mHttp.post_string("http://10.249.255.253/", submit, "gb2312");
        if(html.contains("登录成功窗")){
            return "登录成功";
        }
        if(html.contains("Msg=01")&&html.contains("msga=''")){
            return "密码错误";
        }
        return "";
    }

}
