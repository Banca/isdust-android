package pw.isdust.isdust.function;

import android.telephony.SmsManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pw.isdust.isdust.Http;
import pw.isdust.isdust.function.baseclass.BaseNetworklogin;

/**
 * Created by wzq on 15/9/22.
 */
public class Networklogin_CMCC extends BaseNetworklogin {
    String wlanuserip;
    String wlanacname;
    String CSRFToken_HW;

    String xiaxian;

    public String login(String user,String password,String user2,String password2){
        String submit="DDDDD="+user+"&upass="+encodepassword(password)+"&R1=0&R2=1&para=00&0MKKey=123456";
        String html= mHttp.post_string("http://172.16.0.86/",submit,"gb2312");
        if(html.contains("登录成功窗")){
//            return "登录成功";
            cmcc_init();    //为二层登录准备
            return cmcc_login(user2,password2);    //登录二层
        }
        if(html.contains("Msg=01")&&html.contains("msga=''")){
            return "一层账号密码错误";
        }
        return "";
    }

    public void cmcc_init(){
        String html= mHttp.get_string("http://www.baidu.com/");
        wlanuserip=zhongjian(html, "<input type=\"hidden\" name=\"wlanuserip\" id=\"wlanuserip\" value=\"", "\"/>", 0);
        wlanacname=zhongjian(html,"<input type=\"hidden\" name=\"wlanacname\" id=\"wlanacname\" value=\"","\"/>",0);
        CSRFToken_HW=zhongjian(html,"<input type='hidden' name='CSRFToken_HW' value='","' /></form>",0);
    }

    public boolean changepwd(String pwd){
        SmsManager mSmsManager=SmsManager.getDefault();
        mSmsManager.sendTextMessage("10086",null,"806 "+pwd,null,null);
        return true;
    }

    public boolean query(){
        SmsManager mSmsManager=SmsManager.getDefault();
        mSmsManager.sendTextMessage("10086",null,"3",null,null);
        return true;
    }

    public String cmcc_getyanzheng(String user){
        String submit="username="+user+"&password=&cmccdynapw=cmccdynapw&unreguser=&wlanuserip="+wlanuserip+"&wlanacname="+wlanacname+"&wlanparameter=null&wlanuserfirsturl=http%3A%2F%2Fwww.baidu.com&ssid=cmcc&loginpage=%2Fcmccpc.jsp&indexpage=%2Fcmccpc_index.jsp&CSRFToken_HW="+CSRFToken_HW;
        String html1= mHttp.post_string("https://cmcc.sd.chinamobile.com:8443/mobilelogin.do",submit);
        if (html1.contains("动态密码已经发往手机号码")){
            return "动态密码已经发往手机号码";
        }

        return "cmcc_geyanzheng:未知错误";
    }
    public String cmcc_login(String user, String password){

        String submit="username="+user+"&password="+password+"&cmccdynapw=&unreguser=&wlanuserip="+wlanuserip+"&wlanacname="+wlanacname+"&wlanparameter=null&wlanuserfirsturl=http%3A%2F%2Fwww.baidu.com&ssid=cmcc&loginpage=%2Fcmccpc.jsp&indexpage=%2Fcmccpc_index.jsp&CSRFToken_HW="+CSRFToken_HW;
        String html1= mHttp.post_string("https://cmcc.sd.chinamobile.com:8443/mobilelogin.do",submit);
        if (html1.contains("用户名或密码输入有误，请重新输入！")){
            return "CMCC用户名或密码错误";
        }
        if (html1.contains("下线成功")){
            xiaxian=zhongjian(html1,"var gurl = \"","\";",0);
            xiaxian=xiaxian+zhongjian(html1,"gurl = gurl + \"","\" + removeCookie",0)+"1";
            xiaxian="http://cmcc.sd.chinamobile.com:8001"+xiaxian;

            return "登录成功";        }
        return "未知错误";
    }
    public void xiaxian_cmcc(){
        mHttp.get_string(xiaxian);
    }
    public boolean isOnline(){
        String html= mHttp.get_string("http://www.baidu.com/");
        if (html.contains("百度一下")){
            return true;
        }
        return false;
    }

    public static String zhongjian(String text, String textl, String textr, int start) {

        int left = text.indexOf(textl, start);
        int right = text.indexOf(textr, left + textl.length());
        String zhongjianString = "";
        try{
            zhongjianString = text.substring(left + textl.length(), right);
        }catch (Exception ignore){}
        return zhongjianString;
    }
}
