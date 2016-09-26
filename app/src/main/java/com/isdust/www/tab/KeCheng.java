 package com.isdust.www.tab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pw.isdust.isdust.function.SchoolDate;

/**
 * Created by zor on 2016/9/25.
 * 获取单日课程信息
 */

public class KeCheng {
    private SQLiteDatabase db;
    private int xingqi;
    private String[] weekDays = {"周一", "周二", "周三", "周四", "周五", "周六","周日"};
    private static int jieshu;
    private int zhoushu;
    public KeCheng(SQLiteDatabase db,Context context){
        this.db=db;
        jieshu=0;
        xingqi= SchoolDate.gei_xingqi();
        try {
            zhoushu = SchoolDate.get_xiaoli(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getCourse() {
        StringBuilder stringBuilder = new StringBuilder();
        String messege;
        String sql = "select * from schedule where zhoushu = ? and xingqi = ?";
        Cursor cursor;
        try {
            cursor = db.rawQuery(sql, new String[]{(String.valueOf(zhoushu)), String.valueOf(xingqi)});
            jieshu = cursor.getCount();
            while (cursor.moveToNext()) {
                messege = cursor.getString(cursor.getColumnIndex("kecheng"));
                String[] list = messege.split("<br>");
                String s = init(list[1]);
                stringBuilder.append(s + "     " + list[3] + "         ").append(list[0]).append("\n");
            }
            cursor.close();
        }catch (Exception e) {
                e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String init(String s)
    {
        String jie = "";
        for(int i = 0 ; i < s.length() ; i++)
        {
            if(s.charAt(i)>='1'  && s.charAt(i) <= '9'  )
            {
                if(s.charAt(i)=='9')
                    jie = "9-10";
                else
                    jie = jie.substring(0,0)+s.charAt(i) +'-' + (char)((int)s.charAt(i)+1);
                break;
            }
        }
        return jie;
    }


/*    public List getCourse_end(){
        List l = new ArrayList();
        //StringBuilder stringBuilder=new StringBuilder();
        String messege;
        String sql="select * from schedule where zhoushu = ? and xingqi = ?";
        Cursor cursor;
        cursor = db.rawQuery(sql,new String[]{(String.valueOf(zhoushu)),String.valueOf(xingqi)});
        jieshu=cursor.getCount();
        while(cursor.moveToNext()){
            messege = cursor.getString(cursor.getColumnIndex("kecheng"));
            String[] list = messege.split("<br>");
            //ArrayList<String> list1 = messege.split("<br>");
            //stringBuilder.append(list[0]).append(":").append(list[3]).append("\n");
            String jie = "";
            for(int i = 0 ; i < list[1].length() ; i++)
            {
                if(list[1].charAt(i)>='1'  && list[1].charAt(i) <= '9'  )
                {
                    if(list[1].charAt(i)=='9')
                        jie = "9-10";
                    else
                       jie = jie.substring(0,0)+list[1].charAt(i) +'-' + (char)((int)list[1].charAt(i)+1);
                    break;
                }
            }
            l.add(jie+list[0]+list[3]);
        }
        cursor.close();
        return l;
    }*/

    public String getKecheng(){
        StringBuilder kecheng_info= new StringBuilder();
        kecheng_info.append("今天").append(weekDays[xingqi-1]).append(" ");
        kecheng_info.append("\n").append(getCourse());
        if(jieshu==0)
            kecheng_info.insert(5,"没有课");
        else
            kecheng_info.insert(5,"共有"+jieshu+"节课");
        return kecheng_info.toString();
    }
}
