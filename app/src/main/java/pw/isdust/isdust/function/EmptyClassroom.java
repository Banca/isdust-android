package pw.isdust.isdust.function;

import com.formal.sdusthelper.datatype.ScheduleInformation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/15.
 */
public class EmptyClassroom {
    ScheduleInformation[] mScheduleInformations;
    JSONArray mJSONArray;
    Http mHttp;
    public EmptyClassroom(){
        mHttp=new Http();
    }
    public ScheduleInformation[] jiexi(String text){//将PHP返回的信息处理
        String zhuanhuan=convert(text);
        try {
            mJSONArray=new JSONArray(zhuanhuan);
            int len=mJSONArray.length();
            mScheduleInformations =new ScheduleInformation[len];
            for (int i=0;i<len;i++){
                JSONObject obj = mJSONArray.getJSONObject(i);
                mScheduleInformations[i]=new ScheduleInformation(obj.getString("location"),obj.getInt("zhoushu"),obj.getInt("xingqi"),obj.getInt("jieci"));
            }
        }catch (Exception e){
            System.out.println(e);
        }




        return mScheduleInformations;



    }
    public ScheduleInformation[] getEmptyClassroom(String building, int schooldate, int week, int jieci) throws IOException {//按教室获取整周课程表
        //?method=4&building=&zhoushu=&xingqi=&jieci=
        String text=mHttp.get_string("http://kzxs.isdust.com/chaxun.php?method=4&building="+building+"&zhoushu="+ schooldate +"&xingqi="+week+"&jieci="+jieci);
        ScheduleInformation[]result=jiexi(text);

        return result;
    }

    //public String a

    public String convert(String utfString){
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while((i=utfString.indexOf("\\u", pos)) != -1){
            sb.append(utfString.substring(pos, i));
            if(i+5 < utfString.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
            }
        }
        int len=utfString.length();
        sb.append(utfString.substring(pos, len));

        return sb.toString();
    }

}
