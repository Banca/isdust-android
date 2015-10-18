package pw.isdust.isdust.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/17.
 */
public class Networklogin_ChinaUnicom {
    public Networklogin_ChinaUnicom(){
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
        String html= mHttp.post_string("http://10.249.255.253/", submit, "gb2312");
        if(html.contains("登录成功窗")){
            return "登录成功";
        }
        if(html.contains("Msg=01")&&html.contains("msga=''")){
            return "密码错误";
        }
        return "";
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
