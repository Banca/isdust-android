package pw.isdust.isdust.function.baseclass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pw.isdust.isdust.Http;

/**
 * Created by Administrator on 2015/10/31.
 */
public class BaseNetworklogin {
    protected Http mHttp;

    protected BaseNetworklogin() {
        mHttp=new Http();
    }

    public String login(String user1,String password1,String user2,String password2){return "";}

    public boolean logout(){return false;}

    public boolean changepwd(String pwd) {return false;}

    public boolean query() {return false;}

    public boolean isOnline() {return false;}

    protected String encodepassword(String rawpassword){
        String pid="1";
        String calg="12345678";
        String result=md5(pid+rawpassword+calg);
        result=result+calg+pid;
        return result;
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
