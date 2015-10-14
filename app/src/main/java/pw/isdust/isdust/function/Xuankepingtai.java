package pw.isdust.isdust.function;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/10.
 */
public class Xuankepingtai {
    Http mHttp;
    public Xuankepingtai(){
        mHttp=new Http();
        mHttp.newcookie();
    }
    public String losin(String user,String pwd){
        String text_web;
        text_web= mHttp.get_string("http://192.168.109.142/Account/Login?ReturnUrl=%2F");
        String __RequestVerificationToken=Shangwangdenglu.zhongjian(text_web, "<input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"", "\" />", 0);
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
    public String[][] chaxun(String zhou,String xn,String xq){
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
                    mkebiao_linshi.kecheng=string_linshi;
                    mkebiao.add(mkebiao_linshi);
                }


            }
            mmatcher.end();
            i++;

        }

        int len=mkebiao.size();
        String [][] result=new String[len][3];
        for (i=0;i<len;i++){
            result[i][0]=mkebiao.get(i).xingqi;
            result[i][1]=mkebiao.get(i).jieci;
            result[i][2]=mkebiao.get(i).kecheng;


        }


        return result;//星期，节次，课程
    }
    public class Kebiao{
        public String xingqi;
        public String jieci;
        public String kecheng;


    }
}
