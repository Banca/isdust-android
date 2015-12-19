package pw.isdust.isdust.function;

import java.io.IOException;

import pw.isdust.isdust.Http;

/**
 * Created by Wang Ziqiang on 15/10/17.
 * isdust
 Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Networklogin_ChinaUnicom {
    public Networklogin_ChinaUnicom(){
        mHttp=new Http();

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
