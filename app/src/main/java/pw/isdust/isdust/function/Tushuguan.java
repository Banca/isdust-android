package pw.isdust.isdust.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/19.
 */
public class Tushuguan {
    Http mHttp;
    String [] mPersonalInformation;
    public Tushuguan(){
        mHttp=new Http();
    }
    public String login(String user,String password){
        String msubmit="rdid="+user+"&rdPasswd="+md5(password)+"&returnUrl=";
        String text= mHttp.post_string("http://interlib.sdust.edu.cn/opac/reader/doLogin",msubmit);
        if (text.contains("读者姓名")){
            Pattern mPattern=Pattern.compile("<font class=\"space_font\">([\\S\\s]*?)</font>");
            Matcher mMatcher=mPattern.matcher(text);
            List<String> array_temp=new ArrayList<String>();
            while (mMatcher.find()){
                mMatcher.start();
                array_temp.add(mMatcher.group(1));
                mMatcher.end();
            }
            int len=array_temp.size();
            mPersonalInformation=new String[len];
            for (int i=0;i<len;i++){
                mPersonalInformation[i]=array_temp.get(i).toString();

            }


            return "登录成功";
        }
        return "账号或密码错误";

    }
    public String get_name(){
        return mPersonalInformation[1];
    }
    public String [] [] get_borrwingdetail(){
        String text=mHttp.get_string("http://interlib.sdust.edu.cn/opac/loan/renewList");
        Pattern mPattern=Pattern.compile("<td width=\"40\"><input type=\"checkbox\" name=\"barcodeList\" value=\"([0-9]*?)\" />[\\s\\S]*?target=\"_blank\">([\\S\\s]*?)</a></td>[\\S\\s]*?<td width=\"100\">([0-9]{4}-[0-9]{2}-[0-9]{2})[\\S\\s]*?<td width=\"100\">([0-9]{4}-[0-9]{2}-[0-9]{2})");
        Matcher mMatcher=mPattern.matcher(text);
        List<String []> array_temp=new ArrayList<String []>();
        while(mMatcher.find()){
            String [] temp= new String[4];
            mMatcher.start();
            for (int j=0;j<4;j++){
                temp[j]=mMatcher.group(j+1);
            }
            array_temp.add(temp);

        }
        int len=array_temp.size();
        String [] [] result=new String[len][4];
        for (int i=0;i<len;i++){
            String [] temp=array_temp.get(i);
            result[i]=temp;

        }
        return result;
    }
    public String renew_all(){
        String text=mHttp.post_string("http://interlib.sdust.edu.cn/opac/loan/doRenew","furl=%2Fopac%2Floan%2FrenewList&renewAll=all" );
        String content=Networklogin_CMCC.zhongjian(text, "<div style=\"margin:20px auto; width:50%; height:auto!important; min-height:200px; border:2px dashed #ccc;\">", "<input", 0);
        content=content.replace("\t","");
        content=content.replace("\n","");
        content=content.replace("\r", "");
        String [] temp_array=content.split("<br/>");
        int len=temp_array.length-2;
        String result="";
        for(int i=0;i<len;i++){
            result=result+temp_array[i+1]+"\n";
        }
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
        return "";}
}
