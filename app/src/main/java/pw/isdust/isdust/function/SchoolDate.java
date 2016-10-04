package pw.isdust.isdust.function;

import android.content.Context;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import pw.isdust.isdust.OnlineConfig;

/**
 * Created by Wang Ziqiang on 15/10/11.
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
public class SchoolDate {
    public static int get_xiaoli(Context mContext) throws Exception {

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition mParsePosition = new ParsePosition(0);
        String schooldate= OnlineConfig.getConfigParams( "school_date");
        if (schooldate==""){
            Exception e=new Exception("OnlineConfigFail");
            throw e;
        }
        Date d1 = (Date) mSimpleDateFormat.parse(schooldate, mParsePosition);

        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔

        long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
        long day=time/(1000*3600*24);
        int xiaoli=(int)day/7+1;
        if (xiaoli<1){
            return 1;
        }
        return xiaoli;
    }
    public static int gei_xingqi(){
        Date mDate=new Date();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(mDate);
        int result=mCalendar.get(Calendar.DAY_OF_WEEK)-1;
        if (result==0){
            return 7;
        }
        return result;


    }
    public static int get_jieci(int hours){
        if (hours>0&&hours<10){
            return 1;
        }
        if (hours>10&&hours<12){
            return 2;
        }
        if (hours>12&&hours<16){
            return 3;
        }
        if (hours>16&&hours<18){
            return 4;
        }
        if (hours>18&&hours<21){
            return 5;
        }
        if (hours>21){
            return 1;
        }
        return 1;
    }
}
