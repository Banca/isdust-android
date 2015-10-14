package pw.isdust.isdust.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/9/22.
 */
public class Shangwangdenglu {
    public Shangwangdenglu(){
        mHttp=new Http();

    }
    String xiaxian;
    Http mHttp;
    public String encodepassword(String rawpassword){
        String pid="1";
        String calg="12345678";
        String result=md5(pid+rawpassword+calg);
        result=result+calg+pid;
        return result;

    }
    public String login(String user,String password){
        String submit="DDDDD="+user+"&upass="+encodepassword(password)+"&R1=0&R2=1&para=00&0MKKey=123456";
        String html= mHttp.post_string("http://172.16.0.86/",submit,"gb2312");
        if(html.contains("登录成功窗")){
            return "登录成功";
        }
        if(html.contains("Msg=01")&&html.contains("msga=''")){
            return "密码错误";
        }
        return "";
    }
    public String login_cmcc(String user,String password){

        String html= mHttp.get_string("http://www.baidu.com/");
        String wlanuserip=zhongjian(html, "<input type=\"hidden\" name=\"wlanuserip\" id=\"wlanuserip\" value=\"", "\"/>", 0);

        String wlanacname=zhongjian(html,"<input type=\"hidden\" name=\"wlanacname\" id=\"wlanacname\" value=\"","\"/>",0);
        String CSRFToken_HW=zhongjian(html,"<input type='hidden' name='CSRFToken_HW' value='","' /></form>",0);
        String submit="username="+user+"&password="+password+"&cmccdynapw=&unreguser=&wlanuserip="+wlanuserip+"&wlanacname="+wlanacname+"&wlanparameter=null&wlanuserfirsturl=http%3A%2F%2Fwww.baidu.com&ssid=cmcc&loginpage=%2Fcmccpc.jsp&indexpage=%2Fcmccpc_index.jsp&CSRFToken_HW="+CSRFToken_HW;

        String html1= mHttp.post_string("https://cmcc.sd.chinamobile.com:8443/mobilelogin.do",submit);
        if (html1.contains("用户名或密码输入有误，请重新输入！")){
            return "用户名或密码错误";
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
    public boolean is_login_cmcc(){
        String html= mHttp.get_string("http://www.baidu.com/");
        if (html.contains("百度一下")){
            return true;
        }
        return false;


    }
    public static String zhongjian(String text, String textl, String textr, int start) {
        // ==================================================================
        // 函数名：zhongjian
        // 作者：ypw
        // 功能：取中间文本,比如
        // zhongjian("abc123efg","abc","efg",0)返回123
        // 输入参数：text,textl(左边的text),textr(右边的text),start(起始寻找位置)
        // 返回值：String
        // ==================================================================
        int left = text.indexOf(textl, start);
        int right = text.indexOf(textr, left + textl.length());
        String zhongjianString = "";
        try{
            zhongjianString = text.substring(left + textl.length(), right);
        }catch (Exception ignore){}
        return zhongjianString;
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
