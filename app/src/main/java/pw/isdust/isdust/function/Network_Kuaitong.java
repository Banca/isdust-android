package pw.isdust.isdust.function;

import android.content.Context;

import com.umeng.onlineconfig.OnlineConfigAgent;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/21.
 */
public class Network_Kuaitong {
    Http mHttp_waiwang;//支持外网内网访问
    Http mHttp_direct;//仅支持外网访问

    private Context mContext;

    public Network_Kuaitong(Context context){
        mHttp_waiwang =new Http();
        mHttp_direct=new Http();
        mHttp_waiwang.setTimeout(3);
        mContext = context;

        Networkjudge mNetworkjudge=new Networkjudge(mContext);
        int status=mNetworkjudge.judgetype();

        OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
        if(status==3||status==4){

            String address = OnlineConfigAgent.getInstance().getConfigParams(mContext, "proxy1_address");
            String port = OnlineConfigAgent.getInstance().getConfigParams(mContext, "proxy1_port");
            mHttp_waiwang.setProxy(address, Integer.valueOf(port));
//            mHttp_waiwang.setProxy("proxy1.isdust.com", 1999);
        }
        String iskuaitong = OnlineConfigAgent.getInstance().getConfigParams(mContext, "proxy_iskuaitong");
        if (iskuaitong.equals("true")){
            String address = OnlineConfigAgent.getInstance().getConfigParams(mContext, "proxy1_address");
            String port = OnlineConfigAgent.getInstance().getConfigParams(mContext, "proxy1_port");
            mHttp_direct.setProxy(address, Integer.valueOf(port));
        }

        mHttp_waiwang.setTimeout(10);
    }
    public String loginKuaitong(String user,String password) throws IOException {
        mHttp_direct.newcookie();
        String text=mHttp_direct.get_string("http://ktcx.sdust.edu.cn/mmsg_kt/content/LoginByPassword.aspx");
        String __VIEWSTATE= (Networklogin_CMCC.zhongjian(text, "<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"", "\" />", 0).replace("=", ""));
        String __EVENTVALIDATION=(Networklogin_CMCC.zhongjian(text, "<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"", "\" />", 0).replace("=",""));

        String submit="sm1=UpdatePanel1|checkpassword&account="+user+"&password="+password+"&__LASTFOCUS=&__VIEWSTATE=/wEPDwUKLTYxOTM4OTQ0NQ9kFgICAw9kFgICAw9kFgJmD2QWAgIHDw8WAh4HVmlzaWJsZWdkFgICAQ8PFgIeBFRleHQFLemqjOivgeeUqOaIt+i1hOaWmeWksei0pe+8jOivt+mHjeaWsOi+k+WFpeOAgmRkZMoDcVF3wbH+p1FlLRDMchSLXNfAtPe42jc5J9hf8/AV&__EVENTTARGET=&__EVENTARGUMENT=&__EVENTVALIDATION=/wEWBAL+2PeNCgLPmcHwCwLyveCRDwKKv7vgAa5bXtnUEDotW8uQGmqBnnORnV8DoZpRR7SCjosBorQc&__ASYNCPOST=true&checkpassword=身份验证";
        //submit="sm1=UpdatePanel1|checkpassword&account=2&password=2&__LASTFOCUS=&__VIEWSTATE=/wEPDwUKLTYxOTM4OTQ0NQ9kFgICAw9kFgICAw9kFgJmD2QWAgIHDw8WAh4HVmlzaWJsZWdkFgICAQ8PFgIeBFRleHQFLemqjOivgeeUqOaIt+i1hOaWmeWksei0pe+8jOivt+mHjeaWsOi+k+WFpeOAgmRkZMoDcVF3wbH+p1FlLRDMchSLXNfAtPe42jc5J9hf8/AV&__EVENTTARGET=&__EVENTARGUMENT=&__EVENTVALIDATION=/wEWBAL+2PeNCgLPmcHwCwLyveCRDwKKv7vgAa5bXtnUEDotW8uQGmqBnnORnV8DoZpRR7SCjosBorQc&__ASYNCPOST=true&checkpassword=身份验证";
        text=mHttp_direct.post_string("http://ktcx.sdust.edu.cn/mmsg_kt/content/LoginByPassword.aspx",submit);
        if (text.contains("验证用户资料失败，请重新输入")){
            return "验证用户资料失败，请重新输入";//1|#||4|32|pageRedirect||%2fmmsg_kt%2fcontent%2fmain.aspx|
        }
        if (text.contains("pageRedirect")){
            return "登录成功";
        }
        //return "登录成功";
        return "kuaitong_login：未知错误";
    }

    public String [] getKuaitongInfo() throws IOException {
        String text=mHttp_direct.get_string("http://ktcx.sdust.edu.cn/mmsg_kt/content/account.aspx");
        Pattern mPattern=Pattern.compile("：([\\s\\S]*?)</td>");
        Matcher mMatcher=mPattern.matcher(text);
        List<String> temp=new ArrayList<String>();
        while (mMatcher.find()){
            mMatcher.start();
            temp.add(mMatcher.group(1));
            mMatcher.end();
        }
        int len=temp.size();
        String [] result=new String[len];
        for (int i=0;i<len;i++){
            result[i]=temp.get(i);
        }
        return result;
    }
    public String loginSmartCard(String user,String password) throws IOException {
        mHttp_waiwang.getUnsafeOkHttpClient();
        String text= mHttp_waiwang.get_string("https://epay.sdust.edu.cn/Account/Login");
        String __RequestVerificationToken= URLEncoder.encode(Networklogin_CMCC.zhongjian(text, "<form action=\"/Account/Login\" method=\"post\"><input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"", "\" />", 0));
        String submit="__RequestVerificationToken="+__RequestVerificationToken+"&UserName="+user+"&Password="+password;
        text= mHttp_waiwang.post_string("https://epay.sdust.edu.cn/Account/Login?ReturnUrl=%2F",submit);
        if (text.contains("你好")){
            return "登录成功";
        }
        if (text.contains("提供的用户名或密码不正确。")){
            return "提供的用户名或密码不正确。";
        }
        if (text.contains("您的校园卡密码过于简单")){
            return "您的校园卡密码过于简单,请进入本软件的校园卡功能修改功能";
        }
        if (text.contains("密码请输入校园卡查询密码，即圈存机登录密码")){
            return "密码请输入校园卡查询密码，即圈存机登录密码";
        }
    return "kuaitong_chongzhi_login：error";
    }
    public String pay(String money) throws IOException {
        String text= null;

            text = mHttp_waiwang.post_string("https://epay.sdust.edu.cn/StudNet/Charge","money="+money);

        if (text.contains("充值成功")){
            return "充值成功";
        }
        return "kuaitong_chongzhierror";

    }
    public void tingwang() throws IOException {
        String text= mHttp_waiwang.post_string_noturlencode("https://epay.sdust.edu.cn/StudNet/ControlNet?MercID=%E5%BF%AB%E9%80%9A", "netStatus=%E5%81%9C%E6%9C%BA");
    }
    public void kaiwang() throws IOException {
        String text= mHttp_waiwang.post_string_noturlencode("https://epay.sdust.edu.cn/StudNet/ControlNet?MercID=%E5%BF%AB%E9%80%9A", "netStatus=%E5%BC%80%E5%90%AF" );
    }
    public String gaitaocan(String id) throws IOException {
        /*
        id      套餐
        5      5元包5G
        6      15元包22G
        7      30元包50G
        11     停用

         */
        //
        String text= mHttp_waiwang.post_string_noturlencode("https://epay.sdust.edu.cn/StudNet/ChangeNetMode?MercID=%E5%BF%AB%E9%80%9A", "GroupIDList="+id );
        if (text.contains("修改成功")){
            return "修改成功";
        }
        if (text.contains("您已更改本月套餐类型!")){
            return "修改成功";

        }
        return "gaitaocanerr";
    }
    public String gaimima(String OldPassword,String NewPassword,String ConfirmPassword) throws IOException {
        String text= mHttp_waiwang.post_string_noturlencode("https://epay.sdust.edu.cn/StudNet/ChangeNetPwd?MercID=%E5%BF%AB%E9%80%9A", "OldPassword="+OldPassword+"&NewPassword="+NewPassword+"&ConfirmPassword="+ConfirmPassword );
        if (text.contains("修改密码成功")){
            return "修改密码成功";
        }
        if (text.contains("原密码错误")){
        return "原密码错误";
    }
     if (text.contains("新密码和确认密码不匹配")){
         return "新密码和确认密码不匹配";
     }
      if (text.contains("新密码 必须至少包含 6 个字符")){
            return "新密码 必须至少包含 6 个字符";
        }


        return "gaimimaerror";
    }

}
