package pw.isdust.isdust.function;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wzq on 15/10/11.
 */
public class SchoolDate {
    public static int get_xiaoli(){
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition mParsePosition = new ParsePosition(0);
        Date d1 = (Date) mSimpleDateFormat.parse("2015-09-07 00:00:00", mParsePosition);

        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔

        long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
        long day=time/(1000*3600*24);
        int xiaoli=(int)day/7+1;
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
