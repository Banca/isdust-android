package pw.isdust.isdust.function;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/9/18.
 */
public class Xiaoyuanka {
    private Context mContext;
    private Bitmap myzm_biaozhuan[];
    private String yingshe[];
    private String xuegonghao;
    private String mkey;
    private int page_total;
    private int page_current;
    private String day_current;
    private Date mDate;
    private SimpleDateFormat mSimpleDateFormat;
    private String [] personalinformation;

    public void day_minus(){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(mDate);
        rightNow.add(Calendar.DAY_OF_YEAR, -1);//减一天
        mDate=rightNow.getTime();
    }
    public String day_get(){
        String reStr = mSimpleDateFormat.format(mDate);
        return reStr;

    }


    public Xiaoyuanka(Context context) {
        mDate=new Date();//初始化日期
        mSimpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        day_minus();
        mContext = context;
        //导入标准对比库
        myzm_biaozhuan = new Bitmap[10];
        for (int i = 0; i < 10; i++) {

            try {
                myzm_biaozhuan[i] = BitmapFactory.decodeStream(mContext.getResources().getAssets().open("yzm" + Integer.toString(i) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //导入标准对比库
    }

    public void importimage(Bitmap yanzhengma) {
        yingshe = new String[10];
        Bitmap mutableBitmap = yanzhengma.copy(Bitmap.Config.ARGB_8888, true);
        //图像二值化

        int width = mutableBitmap.getWidth();
        int height = mutableBitmap.getHeight();
        int Pixel;
        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {
                Pixel = mutableBitmap.getPixel(i, j);
                if ((Pixel & 0xFF) > 50) {
                    Pixel = 0xFFFFFFFF;
                } else {
                    Pixel = 0xFF000000;
                }

                mutableBitmap.setPixel(i, j, Pixel);

            }

        }
        //图像二值化


        //切割图像start
        yingshe = new String[10];
        int tupian[][];
        tupian = new int[][]{{10, 36, 23, 23}, {46, 36, 23, 23}, {82, 36, 23, 23}, {10, 72, 23, 23}, {46, 72, 23, 23}, {82, 72, 23, 23}, {10, 108, 23, 23}, {46, 108, 23, 23}, {82, 108, 23, 23}, {10, 144, 23, 23}};

        Bitmap mbitmap;
        for (int i = 0; i < 10; i++) {//十张切割数据
            mbitmap = Bitmap.createBitmap(mutableBitmap, tupian[i][0], tupian[i][1], tupian[i][2], tupian[i][3]);


            //识别
            width = mbitmap.getWidth();

            height = mbitmap.getHeight();
            int cuowu[] = new int[10];

            for (int j = 0; j < 10; j++) {//十张标准库


                for (int k = 0; k < width; k++) {

                    for (int l = 0; l < height; l++) {
                        if (mbitmap.getPixel(k, l) != myzm_biaozhuan[j].getPixel(k, l)) {
                            cuowu[j] = cuowu[j] + 1;
                        }
                    }
                }


                width = mbitmap.getWidth();


            }
            int min = 0;
            for (int m = 0; m < 10; m++) {
                if (cuowu[min] > cuowu[m]) {
                    min = m;
                }

            }

            yingshe[i] = Integer.toString(min);


        }
        //切割图像end


    }//验证码识别

    public String zhuanhuan(String rawpassword) {
        String result = "";
        for (int i = 0; i < rawpassword.length(); i++) {
            for (int j = 0; j < 10; j++) {
                if (rawpassword.charAt(i) == yingshe[j].charAt(0)) {
                    result += Integer.toString(j);
                }

            }
        }
        return result;


    }//密码转化
    public String login(String user,String password){

        importimage(Http.get_image("http://192.168.100.126/getpasswdPhoto.action"));
        Http.get_image("http://192.168.100.126/getCheckpic.action?rand=6520.280869641985");
        String mpassword=zhuanhuan(password);
        String result= Http.post_string("http://192.168.100.126/loginstudent.action", "name=" + user + "&userType=1&passwd=" + mpassword + "&loginType=2&rand=6520&imageField.x=39&imageField.y=10");
        if (result.contains("持卡人")){
            result=Http.get_string("http://192.168.100.126/accountcardUser.action");
            Pattern mpattern = Pattern.compile("<div align=\"left\">([\\S\\s]*?)</div>");
            Matcher mmatcher = mpattern.matcher(result);

            personalinformation=new String[22];
            int i=0;
            while (mmatcher.find()){
                mmatcher.start();
                personalinformation[i]= mmatcher.group(1);
                mmatcher.end();
                i=i+1;


            }

//            mmatcher.find();
//            mmatcher.start();
            xuegonghao = personalinformation[1];
//            personalinformation[0]= mmatcher.group(1);
//            mmatcher.end();
//            mmatcher.find();
//            mmatcher.start();
//            personalinformation[1]= mmatcher.group(1);
//            mmatcher.end();
//            mmatcher.find();
//            mmatcher.start();
//            personalinformation[2]= mmatcher.group(1);
//            mmatcher.end();
//            mmatcher.find();
//            mmatcher.start();
            mkey=getkey();
            System.out.println(xuegonghao);

            return "登录成功";
        }else if(result.contains("登陆失败，无此用户名称")){
            return "无此用户名称";
        }else if(result.contains("登陆失败，密码错误")){
            return "密码错误";}
        return "未知错误";
    }//登录
    public String[][] fenxi(String text){
        Pattern mpattern = Pattern.compile("<tr class=\"listbg[\\s\\S]*?\">[\\s\\S]*?<td  align=\"center\">([\\s\\S]*?)</td>[\\s\\S]*?<td   align=\"center\">([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\" >([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\" >([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"right\">([\\s\\S]*?)</td>[\\s\\S]*?<td align=\"right\">([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\">([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\" >([\\s\\S]*?)</td>[\\s\\S]*?</tr>");
        Matcher mmatcher = mpattern.matcher(text);
        List<String[]> result_arraylist;
        result_arraylist=new ArrayList<String[]>();


        while (mmatcher.find()){
            String linshi []=new String[8];
            mmatcher.start();
            for (int j=0;j<8;j++){
                linshi[j]=mmatcher.group(j+1);
            }
            mmatcher.end();
            result_arraylist.add(linshi);

        }
        int len=result_arraylist.size();
        String [][] result_final=new String[len][8];
        for(int i=0;i<len;i++){
            String []a=result_arraylist.get(i);
            result_final[i]=a;
        }
        page_total = Integer.parseInt(Shangwangdenglu.zhongjian(text, "&nbsp;&nbsp;共", "页&nbsp;&nbsp;",0));

        return result_final;

    }//处理查询的文本
    public String[][] fenxi_today(String text){
        Pattern mpattern = Pattern.compile("<tr class=\"listbg[\\s\\S]*?\">[\\s\\S]*?<td  align=\"center\">([\\s\\S]*?)</td>[\\s\\S]*?<td   align=\"center\">([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\" >([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\" >([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"right\">([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"right\">([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\">([\\s\\S]*?)</td>[\\s\\S]*?<td  align=\"center\" >([\\s\\S]*?)</td>[\\s\\S]*?</tr>");
        Matcher mmatcher = mpattern.matcher(text);
        List<String[]> result_arraylist;
        result_arraylist=new ArrayList<String[]>();

        while (mmatcher.find()){
            String linshi []=new String[8];
            mmatcher.start();
            for (int j=0;j<8;j++){
                linshi[j]=mmatcher.group(j+1);
            }
            mmatcher.end();
            result_arraylist.add(linshi);

        }
        int len=result_arraylist.size();
        String [][] result_final=new String[len][8];
        for(int i=0;i<len;i++){
            String []a=result_arraylist.get(i);
            result_final[i]=a;
        }
        page_total = Integer.parseInt(Shangwangdenglu.zhongjian(text, "&nbsp;&nbsp;共", "页&nbsp;&nbsp;",0));

        return result_final;

    }//处理查询的文本
    public String getkey(){
        //get_key_init
        String text=Http.get_string("http://192.168.100.126/accounthisTrjn.action");
        Pattern mpattern = Pattern.compile("\"/accounthisTrjn.action\\?__continue=([\\s\\S]*?)\"");
        Matcher mmatcher = mpattern.matcher(text);
        mmatcher.find();
        mmatcher.start();
        String key_init=mmatcher.group(1);
        //get_key_init


        text=Http.post_string("http://192.168.100.126/accounthisTrjn.action?__continue="+key_init, "account="+xuegonghao+"&inputObject=all&Submit=+%C8%B7+%B6%A8+");
        mmatcher = mpattern.matcher(text);
        mmatcher.find();
        mmatcher.start();
        String result=mmatcher.group(1);

        //account=84734&inputObject=all&Submit=+%C8%B7+%B6%A8+


        return result;
    }//获取会话key
    public String[][]chaxun(String inputStartDate,String inputEndDate,int page){
        mkey=getkey();
        String text= Http.post_string("http://192.168.100.126/accounthisTrjn.action?__continue=" + mkey, "inputStartDate=" + inputStartDate + "&inputEndDate=" + inputEndDate + "&pageNum=" + page);
        page_current=page;
        day_current=inputStartDate;
        Pattern mpattern = Pattern.compile("<form id=\"\\?__continue=([\\S\\s]*?)\" name=\"form1\" ");
        Matcher mmatcher = mpattern.matcher(text);
        mmatcher.find();
        mmatcher.start();
        String msearchkey=mmatcher.group(1);
        String result[][]= fenxi(Http.post_string("http://192.168.100.126/accounthisTrjn.action?__continue=" + msearchkey, ""));
        return result;

    }
    public String[][]chaxun(){
//        mkey=getkey();
//        String text= Http.post_string("http://192.168.100.126/accounttodatTrjnObject.action" , "account="+xuegonghao+"&inputObject=all&Submit=+%C8%B7+%B6%A8+");
//
//        Pattern mpattern = Pattern.compile("<form id=\"\\?__continue=([\\S\\s]*?)\" name=\"form1\" ");
//        Matcher mmatcher = mpattern.matcher(text);
//        mmatcher.find();
//        mmatcher.start();
//        String msearchkey=mmatcher.group(1);
        String result[][]= fenxi_today(Http.post_string("http://192.168.100.126/accounttodatTrjnObject.action", "account=" + xuegonghao + "&inputObject=all&Submit=+%C8%B7+%B6%A8+"));
        return result;

    }
    public String[][]nextpage(){
        page_current=page_current+1;
        if (page_current>page_total){

            page_current=1;
            day_minus();
            day_current=day_get();

            return chaxun(day_current,day_current,page_current);
        }
        return chaxun(day_current,day_current,page_current);



    }
    public String[][]chaxunlishi(){
        page_current=1;
        day_current=day_get();
        return chaxun(day_current,day_current,page_current);

    }



    private boolean storeImage(Bitmap imageData, String filename) {
        //get path to external storage (SD card)
        String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myImages/";
        File sdIconStorageDir = new File(iconsStoragePath);

        //create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();

        try {
            String filePath = sdIconStorageDir.toString() + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            //choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        }

        return true;
    }

    //获取学生姓名
    public String getStuName() {  return personalinformation[0]; }
    //获取学号
    public String getStuNumber() {return personalinformation[3]; }
    //获取学生专业班级
    public String getStuClass() {return personalinformation[13]; }
}
