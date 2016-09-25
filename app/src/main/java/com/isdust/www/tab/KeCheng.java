 package com.isdust.www.tab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    private String getCourse(){
        StringBuilder stringBuilder=new StringBuilder();
        String messege;
        String sql="select * from schedule where zhoushu = ? and xingqi = ?";
        Cursor cursor;
        cursor = db.rawQuery(sql,new String[]{(String.valueOf(zhoushu)),String.valueOf(xingqi)});
        jieshu=cursor.getCount();
        while(cursor.moveToNext()){
            messege = cursor.getString(cursor.getColumnIndex("kecheng"));
            String[] list = messege.split("<br>");
            stringBuilder.append(list[0]).append(":").append(list[3]).append("\n");
        }
        cursor.close();
        return stringBuilder.toString();
    }
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
