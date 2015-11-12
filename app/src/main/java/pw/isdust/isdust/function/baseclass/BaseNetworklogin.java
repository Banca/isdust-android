package pw.isdust.isdust.function.baseclass;

import java.io.IOException;

import pw.isdust.isdust.Http;

/**
 * Created by Administrator on 2015/10/31.
 */
public class BaseNetworklogin {
    protected Http mHttp;

    protected BaseNetworklogin() {
        mHttp=new Http();
    }

    public String login(String user1,String password1,String user2,String password2) throws IOException {return "";}

    public boolean logout(){return false;}

    public boolean changepwd(String pwd) {return false;}

    public boolean query() {return false;}

    public boolean isOnline() throws IOException {return false;}


}
