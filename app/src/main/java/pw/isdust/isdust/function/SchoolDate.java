package pw.isdust.isdust.function;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wzq on 15/10/11.
 */
public class SchoolDate {
    public static int get_xiaoli(){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date d1 = (Date) sd.parse("2015-09-07 00:00:00", pos);

        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔

        long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
        long day=time/(1000*3600*24);
        int xiaoli=(int)day%7-1;
        return xiaoli;
    }
}
