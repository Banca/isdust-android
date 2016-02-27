package pw.isdust.isdust.function;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.isdust.www.datatype.Kebiao;
import com.umeng.onlineconfig.OnlineConfigAgent;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.isdust.isdust.Http;

/**
 * Created by Wang Ziqiang on 15/10/10.
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
public class SelectCoursePlatform {
    SQLiteDatabase db;
    Http mHttp;
    Context mContext;
    String mtext_zhengfang;
    String url_chengji;
    String url_xuanke;
    String address_zhengfang;
    String method;
    public SelectCoursePlatform(Context context) throws Exception {
        mContext=context;
        mHttp=new Http();
        mHttp.newcookie();
        String address_config [];
        Networkjudge mNetworkjudge=new Networkjudge(context);
        address_config=OnlineConfigAgent.getInstance().getConfigParams(mContext, "address_zhengfang").split("\n");
        method=OnlineConfigAgent.getInstance().getConfigParams(mContext, "jiaowu_chengji_method");
        Random random = new Random();
        int s = random.nextInt(address_config.length)%(address_config.length+1);
        address_zhengfang=address_config[s];
//        Toast.makeText(context,address_zhengfang,Toast.LENGTH_SHORT).show();
        int status=mNetworkjudge.judgetype();
        if(status==3||status==4){
            String address = OnlineConfigAgent.getInstance().getConfigParams(mContext, "proxy1_address");
            String port = OnlineConfigAgent.getInstance().getConfigParams(mContext, "proxy1_port");
            if (address==""){
                Exception e=new Exception("OnlineConfigFail");
                throw e;}
//            address="139.129.133.235";port="2010";
            mHttp.setProxy(address, Integer.valueOf(port));
        }

//        if(mNetworkjudge.judgetype()==3){
//            mHttp.setProxy("proxy1.isdust.com", 1999);
//        }else if(mNetworkjudge.judgetype()==4){
//            if (mNetworkjudge.neiwaiwang_judge()==1){
//                mHttp.setProxy("proxy1.isdust.com", 1999);
//            }
//        }
        //mHttp.setProxy("219.146.243.3", 2000);

    }
    public void zhengfang_tiaozhuan_xuankepingtai() throws IOException {

        mtext_zhengfang=mHttp.get_string(url_xuanke, "gb2312");
        url_xuanke=Networklogin_CMCC.zhongjian(mtext_zhengfang, "<a target=\"_top\" href=\"", "\">如果您的浏览器没有跳转，请点这里</a>", 0);
        mtext_zhengfang=mHttp.get_string(url_xuanke);
    }
    public String login_zhengfang(String user, String pwd) throws IOException {
        String text_web;
        text_web= mHttp.get_string("http://"+address_zhengfang+"/default_ysdx.aspx", "gb2312");
        String __VIEWSTATE= Networklogin_CMCC.zhongjian(text_web, "<input type=\"hidden\" name=\"__VIEWSTATE\" value=\"", "\" />", 0);
        __VIEWSTATE=URLEncoder.encode(__VIEWSTATE);
        String submit="__VIEWSTATE="+__VIEWSTATE+"&TextBox1="+user+"&TextBox2="+URLEncoder.encode(pwd)+"&RadioButtonList1=%D1%A7%C9%FA&Button1=++%B5%C7%C2%BC++" ;
        mtext_zhengfang=mHttp.post_string_noturlencode("http://" + address_zhengfang + "/default_ysdx.aspx", submit);
        if (mtext_zhengfang.contains("<script>window.open('xs_main.aspx?xh=2")){
            String url_login_zhengfang=Networklogin_CMCC.zhongjian(mtext_zhengfang,"<script>window.open('","','_parent');</script>",0);
            url_login_zhengfang="http://"+address_zhengfang+"/"+url_login_zhengfang;
            mtext_zhengfang=mHttp.get_string(url_login_zhengfang, "gb2312");
            url_xuanke=Networklogin_CMCC.zhongjian(mtext_zhengfang, "信息员意见反馈</a></li><li><a href=\"", "\" target='zhuti' onclick=\"GetMc('激活选课平台帐户');", 0);
            url_xuanke="http://"+address_zhengfang+"/"+url_xuanke;
            url_chengji=Networklogin_CMCC.zhongjian(mtext_zhengfang,"学生个人课表</a></li><li><a href=\"","\" target='zhuti' onclick=\"GetMc('个人成绩查询');\">",0);
            url_chengji="http://"+address_zhengfang+"/"+url_chengji;


            return "登录成功";
        }if (mtext_zhengfang.contains("密码错误")){
            return "密码错误";

        }if (mtext_zhengfang.contains("用户名不存在")){
            return "用户名不存在";

        }

        return "未知错误login_zhengfang";
    }
    public String login_xuankepingtai(String user, String pwd) throws IOException {
        String text_web;
        text_web= mHttp.get_string("http://192.168.109.142/Account/Login?ReturnUrl=%2F");
        String __RequestVerificationToken= Networklogin_CMCC.zhongjian(text_web, "<input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"", "\" />", 0);
        try {
            __RequestVerificationToken= URLEncoder.encode(__RequestVerificationToken, "utf-8");

        }catch (Exception e){
            System.out.println(e);
        }
        String post_submit="UserName="+user+"&Password="+pwd+"&RememberMe=false&__RequestVerificationToken="+__RequestVerificationToken;
        text_web=mHttp.post_string("http://192.168.109.142/Account/Login?ReturnUrl=%2F", post_submit);
        if(text_web.contains("你好")){
            return "登录成功";
        }

        return "";
    }
    public List<String []> chengji_chaxun(String xuenian,String xueqi) throws IOException {
        mHttp.setTimeout(600);
        if (method.equals("zhengfang")){
        String text=mHttp.get_string(url_chengji);
        String __VIEWSTATE= Networklogin_CMCC.zhongjian(text, "<input type=\"hidden\" name=\"__VIEWSTATE\" value=\"", "\" />", 0);
        __VIEWSTATE=URLEncoder.encode(__VIEWSTATE);
        String submit="__VIEWSTATE="+__VIEWSTATE+"&ddlXN="+xuenian+"&ddlXQ="+xueqi+"&btn_xq=%D1%A7%C6%DA%B3%C9%BC%A8" ;
        text=mHttp.post_string_noturlencode(url_chengji, submit);
        return chengji_chaxun_fenxi_zhengfang(text);}
        if (method.equals("xuanke")){
            zhengfang_tiaozhuan_xuankepingtai();
            String text=mHttp.get_string("http://192.168.109.142/Home/About");
            text=text.replace("class=\"selected\"","");
//            String __VIEWSTATE= Networklogin_CMCC.zhongjian(text, "<input type=\"hidden\" name=\"__VIEWSTATE\" value=\"", "\" />", 0);
//            __VIEWSTATE=URLEncoder.encode(__VIEWSTATE);
//            String submit="__VIEWSTATE="+__VIEWSTATE+"&ddlXN="+xuenian+"&ddlXQ="+xueqi+"&btn_xq=%D1%A7%C6%DA%B3%C9%BC%A8" ;
//            text=mHttp.post_string_noturlencode(url_chengji, submit);
            return chengji_chaxun_fenxi_xuanke(text);}
        return null;




    }
    public List<String []> chengji_chaxun_fenxi_zhengfang(String text){
        Pattern mpattern = Pattern.compile("<tr[\\s\\S]*?>[\\s\\S]*?<td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td><td>([\\s\\S]*?)</td>[\\S\\s]*?</tr>");
        Matcher mmatcher = mpattern.matcher(text);
        List<String []> mchengji =new ArrayList<String []>();
        mmatcher.find();//过滤第一组无用数据
        String [] temp;
        while (mmatcher.find()){
            temp=new String[15];
            for (int i=0;i<15;i++){
                temp[i]=mmatcher.group(i+1);
                temp[i]=temp[i].replace(" ", "");
                temp[i]=temp[i].replace("&nbsp;","");
            }
            mchengji.add(temp);
        }
        return mchengji;

    }
    public List<String []> chengji_chaxun_fenxi_xuanke(String text){
        Pattern mpattern = Pattern.compile("<tr>[\\S\\s]*?<td>([\\S\\s]*?)</td>[\\S\\s]*?<td>([\\S\\s]*?)</td>[\\S\\s]*?<td>([\\S\\s]*?)</td>[\\S\\s]*?<td>([\\S\\s]*?)</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>[\\S\\s]*?</tr>");
        Matcher mmatcher = mpattern.matcher(text);
        List<String []> mchengji =new ArrayList<String []>();
        //mmatcher.find();//过滤第一组无用数据
        String [] temp;
        while (mmatcher.find()){
            temp=new String[15];
            temp[3]=mmatcher.group(3);
            temp[1]=mmatcher.group(2);
            temp[6]=mmatcher.group(4);
            temp[8]=mmatcher.group(5);
//            for (int i=0;i<15;i++){
//                temp[i]=mmatcher.group(i+1);
//                temp[i]=temp[i].replace(" ", "");
//                temp[i]=temp[i].replace("&nbsp;","");
//            }
            mchengji.add(temp);
        }
        return mchengji;

    }
    public Kebiao[] kebiao_chaxun(String zhou, String xn, String xq) throws IOException {
        String text_web;
        text_web= mHttp.get_string("http://192.168.109.142/?zhou="+zhou+"&xn="+xn+"&xq="+xq);
        text_web=text_web.replace(" rowspan=\"2\" ","");
        Pattern mpattern = Pattern.compile("<td  class=\"leftheader\">第[1,3,5,7,9]节</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>[\\S\\s]*?<td >([\\S\\s]*?)</td>");
        Matcher mmatcher = mpattern.matcher(text_web);
        String tiqu1 [] []=new String[5][7];
        //Kebiao  =new Kebiao();
        List<Kebiao> mkebiao =new ArrayList<Kebiao>();
        String string_linshi;
        int i=0;
        while (mmatcher.find()){
            mmatcher.start();
            for (int j=0;j<7;j++){



                string_linshi=mmatcher.group(j+1);
                if (string_linshi.equals("&nbsp;")==false){
                    Kebiao mkebiao_linshi=new Kebiao();
                    mkebiao_linshi.jieci= (i+1)+"";
                    mkebiao_linshi.xingqi= (j+1)+"";
                    string_linshi=string_linshi.replace("<b class=\"newCourse\">","").replace("</b>","");
                    mkebiao_linshi.kecheng=string_linshi;
                    mkebiao.add(mkebiao_linshi);
                }


            }
            mmatcher.end();
            i++;

        }

        int len=mkebiao.size();
        Kebiao [] result1=new Kebiao[len];
//        String [][] result=new String[len][3];
        for (i=0;i<len;i++){
            result1[i]=new Kebiao();
            result1[i].zhoushu=zhou;
            result1[i].xingqi=mkebiao.get(i).xingqi;
            result1[i].jieci=mkebiao.get(i).jieci;
            result1[i].kecheng=mkebiao.get(i).kecheng;


//            result[i][0]=mkebiao.get(i).xingqi;
//            result[i][1]=mkebiao.get(i).jieci;
//            result[i][2]=mkebiao.get(i).kecheng;


        }


        return result1;//星期，节次，课程
    }


//    public String scheduletojson(Kebiao [] kebiao){
//        JSONObject mJSONObject_each;
//        JSONArray mJSONArray=new JSONArray();
//        int len=kebiao.length;
//        for (int i=0;i<len;i++){
//            try {
//                mJSONObject_each=new JSONObject();
//                mJSONObject_each.put("zhoushu",kebiao[i].zhoushu);
//                mJSONObject_each.put("xingqi",kebiao[i].xingqi);
//                mJSONObject_each.put("jieci",kebiao[i].jieci);
//                mJSONObject_each.put("kecheng",kebiao[i].kecheng);
//                mJSONArray.put(mJSONObject_each);
//                ;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//        String result=mJSONArray.toString();
//        return result;
//        //System.out.println(result);
//
//    }

}
