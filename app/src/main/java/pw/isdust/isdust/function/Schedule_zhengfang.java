package pw.isdust.isdust.function;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by wzq on 10/1/16.
 */

public class Schedule_zhengfang {
    public static String data_preprocess(String data){
        Re re_replace_width = Re.compile(" width=\"[0-9]*?%\"");
        Re re_replace_rowspan = Re.compile(" rowspan=\"[0-9]*?\"");
        Re re_replace_class_noprint= Re.compile(" class=\"noprint\"");
        Re re_replace_class_alt= Re.compile(" class=\"alt\"");
        Re re_replace_font = Re.compile("<br><br><font color=\'red\'>([\\s\\S]*?)</font>");

        String result=data;
        result = re_replace_width.sub("", result);
        result = re_replace_rowspan.sub("", result);
        result = re_replace_class_noprint.sub("", result);
        result = re_replace_class_alt.sub("", result);
        result = re_replace_font.sub("", result);
        return result;

    }
    public static String [] []extract_raw_schedule(String data){

        String mdata=data_preprocess(data);
        Re re_jieci = Re.compile("<td>第[1,3,5,7,9]节</td>([\\s\\S]*?)</tr><tr>");
        Re re_xingqi = Re.compile("<td align=\"Center\">([\\s\\S]*?)</td>");
        String [][] jieci = re_jieci.findall(mdata);
        String [][]result=new String[jieci.length][];
        for(int i=0;i<jieci.length;i++){
            String [][]temp=re_xingqi.findall(jieci[i][1]);
            String []result_temp=new String[temp.length];
            for(int j=0;j<temp.length;j++){
                result_temp[j]=temp[j][1];
            }
            result[i]=result_temp;
        }
        return result;

    }
    public static int [] process_zhouci(String time){
        Re re_zhouci=Re.compile("第([\\S\\s]*?)-([\\S\\s]*?)周");
        String [][] temp_zhouci=re_zhouci.findall(time);
        List<Integer> result_temp= new ArrayList<Integer>();
        int start=Integer.parseInt(temp_zhouci[0][1]),end=Integer.parseInt(temp_zhouci[0][2]);
        for(int i=start;i<=end;i++){
            if(time.contains("单")){
                if(i%2==1){
                    result_temp.add(i);
                }
                continue;
            }
            if(time.contains("双")){
                if(i%2==0){
                    result_temp.add(i);
                }
                continue;
            }
            result_temp.add(i);
        }
        int [] result=new int[result_temp.size()];

        for(int i=0;i<result_temp.size();i++){
            result[i]=result_temp.get(i);
        }
        return result;


    }
    public static HashMap<String,Object> process_raw_cell(String cell){
        HashMap<String,Object> result=new HashMap<String,Object>();
        String [] temp_split=cell.split("<br>",-1);
        result.put("class",temp_split[0]);
        Re re_zhouci = Re.compile("(\\{[\\s\\S]*?\\})");
        String [][]temp=re_zhouci.findall(temp_split[1]);
        result.put("teacher",temp_split[2]);
        result.put("location",temp_split[3]);
        result.put("zhoushu",process_zhouci(temp[0][0]));

        return result;


    }
    public static HashMap<String,Object>[] process_raw_schedule(String [] []raw){
        HashMap<String,Object>[] result;
        List< HashMap<String,Object>> result_temp=new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<raw.length;i++){
            for(int j=0;j<raw[i].length;j++){
                if(raw[i][j].equals("&nbsp;"))continue;
                if (raw[i][j].contains("<br><br>")==true){
                    String []temp_split=raw[i][j].split("<br><br>");
                    for(int k=0;k<temp_split.length;k++){
                        HashMap<String,Object> result_child=process_raw_cell(temp_split[k]);
                        result_child.put("jieci",i+1);
                        result_child.put("xingqi",j+1);
                        result_temp.add(result_child);
                    }
                    continue;
                }
                HashMap<String,Object> result_child=process_raw_cell(raw[i][j]);
                result_child.put("jieci",i+1);
                result_child.put("xingqi",j+1);
                result_temp.add(result_child);
            }
        }
        result=result_temp.toArray(new HashMap[0]);
        return result;



    }
    public static String [][]extract_raw_change(String data){
        String mdata=data_preprocess(data);
        Re re_table_all=Re.compile("<td>编号</td><td>课程名称</td><td>原上课时间地点教师</td><td>现上课时间地点教师</td><td>申请时间</td>[\\S\\s]*?</tr>([\\S\\s]*?)</table>");
        String temp_data_all=re_table_all.findall(mdata)[0][1];
        Re re_table_row=Re.compile("<tr>[\\S\\s]*?<td>([\\S\\s]*?)</td><td>([\\S\\s]*?)</td><td>([\\S\\s]*?)</td><td>([\\S\\s]*?)</td><td>([\\S\\s]*?)</td>");
        String [][] result=re_table_row.findall(temp_data_all);
        return result;
    }
    public static HashMap<String,Object>[] process_raw_change(String[][] data){
        List< HashMap<String,Object>> result_temp=new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object>[] result;
        for(int i=0;i<data.length;i++){
            HashMap<String,Object> result_child=new HashMap<String,Object>();
            result_child.put("date",time_convert(data[i][5]));
            if(!data[i][3].equals("&nbsp;"))result_child.put("old",process_raw_change_cell(data[i][2],data[i][3]));
            if(!data[i][4].equals("&nbsp;"))result_child.put("new",process_raw_change_cell(data[i][2],data[i][4]));
            result_temp.add(result_child);

        }
        result=result_temp.toArray(new HashMap[0]);
        for(int i=0;i<result.length;i++){
            HashMap<String,Object> temp;

            for(int j=i;j<result.length;j++){

                if((Long)result[i].get("date")>(Long)result[j].get("date")){
                    temp=result[i];
                    result[i]=result[j];
                    result[j]=temp;

                }
            }

        }
        return result;
    }
    public static HashMap<String,Object> resolve_change_time(String data){
        HashMap<String,Object> result=new HashMap<String,Object>();
        Re re_time=Re.compile("周([\\S\\s]*?)第([\\S\\s]*?)节连续2节(\\{[\\s\\S]*?\\})");
        String [] []temp_time=re_time.findall(data);
        result.put("xingqi",Integer.parseInt(temp_time[0][1]));
        result.put("jieci",(Integer.parseInt(temp_time[0][2])+1)/2);
        result.put("zhoushu",process_zhouci(temp_time[0][3]));
        return result;

    }
    public static HashMap<String,Object> process_raw_change_cell(String name, String data){
        HashMap<String,Object> result=new HashMap<String,Object>();
        String [] temp_split=data.split("/",-1);
        result.put("class",name);
        result.put("teacher",temp_split[2]);
        result.put("location",temp_split[1]);
        result.putAll(resolve_change_time(temp_split[0]));
        return result;

    }
    public static long time_convert(String time){
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        dfm.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        long result=0;

        try{
            result= dfm.parse(time).getTime()/1000;

        }catch (Exception e){

        }
        return result;

    }
    public static HashMap<String,Object>[]  getschedule(String data){
        String  [] [] temp=extract_raw_schedule(data);
        return process_raw_schedule(temp);
    }
    public static HashMap<String,Object>[]  getchange(String data){
        String  [] [] temp=extract_raw_change(data);
        return process_raw_change(temp);
    }


}
