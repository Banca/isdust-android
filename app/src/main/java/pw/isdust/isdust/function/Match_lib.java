package pw.isdust.isdust.function;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Match_lib {
    public static String getlocalMap(String text){//所在馆位置
        JSONObject mJSONObject;
        try {
            mJSONObject=new JSONObject("{\"QBCK\":\"青岛科图版书库\",\"JNJC\":\"济南教材参考库\",\"QXKYL\":\"青岛现刊阅览室\",\"QZKK\":\"青岛自科书库\",\"QWYK\":\"青岛外文书样本库\",\"QWWK\":\"青岛外文书库\",\"TDZKK\":\"泰东自科现刊\",\"QZYK\":\"青岛中文书样本库\",\"TDYB\":\"泰东样本库\",\"KJGK\":\"泰西过刊\",\"JNGK\":\"中文期刊\",\"JNWWK\":\"济南外文刊\",\"TDZT\":\"泰东中图库\",\"TDSKK\":\"泰东社科现刊\",\"JNGP\":\"济南随书光盘库\",\"QSKK\":\"青岛社科书库\",\"JNQK\":\"济南期刊库\",\"JNSK\":\"济南社科借阅区\",\"QJCK\":\"青岛教材样本库\",\"TDKT\":\"泰东科图库\",\"KJZKK\":\"泰西自科现刊\",\"QMJK\":\"青岛密集库\",\"QDZY\":\"青岛电子阅览室\",\"TDGK\":\"泰东过刊库\",\"TDXS\":\"泰东学生阅览室\",\"JNGJ\":\"济南工具书\",\"TDKY\":\"泰东考研库\",\"QGKK\":\"青岛过期期刊库\",\"Q007\":\"青岛未分配流通库\",\"JNXS\":\"济南学生借书处\",\"JNBC\":\"济南保存库\",\"TDWW\":\"泰东外文库\",\"TDZH\":\"泰东综合库\",\"KJTC\":\"特藏图书\",\"JNZK\":\"济南自科借阅区\",\"JNFY\":\"济南复印\",\"TDTC\":\"泰东特藏库\",\"KJZT\":\"泰西中图库\",\"TDZLS\":\"泰文法资料室\",\"JNWW\":\"济南外文借书处\",\"WFFG\":\"文法分馆\",\"QGJK\":\"青岛工具书库\",\"JNLS\":\"济南临时库\",\"QTCK\":\"青岛特藏书库\",\"KJSKK\":\"泰西社科现刊\",\"JNJS\":\"济南教师借书处\",\"JNDZ\":\"济南电子阅览室\",\"TDJS\":\"泰东教师阅览室\"}");//所在馆位置
            return mJSONObject.get(text).toString();
        }catch (Exception e){

        }
        return "";


    }
    public static String getorglib(String text){//文献所属馆
        JSONObject mJSONObject;
        try {
            mJSONObject=new JSONObject("{\"02000\":\"泰安东校区\",\"04000\":\"泰山科技学院\",\"01000\":\"青岛校区\",\"01000 \":null,\"03000\":\"济南校区\",\"999\":\"山东科技大学图书馆\",\"05000\":\"文法分馆\"}");//所在馆位置
            return mJSONObject.get(text).toString();
        }catch (Exception e){

        }
        return "";


    }
    public static String getstate(String text){//馆藏状态
        JSONObject mJSONObject;
        try {
            mJSONObject=new JSONObject("{\"0\":{\"stateType\":0,\"stateName\":\"流通还回上架中\"},\"1\":{\"stateType\":1,\"stateName\":\"编目\"},\"2\":{\"stateType\":2,\"stateName\":\"在馆\"},\"3\":{\"stateType\":3,\"stateName\":\"借出\"},\"4\":{\"stateType\":4,\"stateName\":\"丢失\"},\"5\":{\"stateType\":5,\"stateName\":\"剔除\"},\"6\":{\"stateType\":6,\"stateName\":\"交换\"},\"7\":{\"stateType\":7,\"stateName\":\"赠送\"},\"8\":{\"stateType\":8,\"stateName\":\"装订\"},\"9\":{\"stateType\":9,\"stateName\":\"锁定\"},\"10\":{\"stateType\":10,\"stateName\":\"预借\"},\"12\":{\"stateType\":12,\"stateName\":\"清点\"},\"13\":{\"stateType\":13,\"stateName\":\"闭架\"},\"14\":{\"stateType\":14,\"stateName\":\"修补\"},\"15\":{\"stateType\":15,\"stateName\":\"查找中\"}}");//馆藏状态
            return mJSONObject.getJSONObject(text).getString("stateName");
        }catch (Exception e){

        }
        return "";


    }
    public static void getBook(String text){
        JSONArray mJSONArray;

        String raw_tushuxinxi="[{\""+Networklogin_CMCC.zhongjian(text,"[{\"","}]",0)+"}]";
        String raw_jieyuexinxi=Networklogin_CMCC.zhongjian(text,"{\"loanWorkMap\":",",\"holdingList",0);
        List<String []> mfinal=new ArrayList<String []>();
        try {
            mJSONArray=new JSONArray(raw_tushuxinxi);//馆藏状态
            int len=mJSONArray.length();
            for (int i=0;i<len;i++){
                String result []=new String[6];
                result[0]=mJSONArray.getJSONObject(i).getString("callno");//索书号
                result[1]=mJSONArray.getJSONObject(i).getString("barcode");//条码号
                result[2]=getstate(mJSONArray.getJSONObject(i).getString("state"));//馆藏状态
                if (result[2].equals("借出")){
                    result[3]=TimeStamp2Date(getreturndate(raw_jieyuexinxi,result[1]));
                }else {
                    result[3]="";
                }
                //result[3]=mJSONArray.getJSONObject(i).getString("barcode");//条码号
                result[4] = getorglib(mJSONArray.getJSONObject(i).getString("orglib"));//文献所属馆
                result[5] = getlocalMap(mJSONArray.getJSONObject(i).getString("orglocal"));//所在馆位置
                mfinal.add(result);
            }
        }catch (Exception e){

        }
}
    public static String getreturndate(String raw,String barcode){
        JSONArray mJSONArray;
        JSONObject mJSONObject;

        try {
            mJSONObject=new JSONObject(raw);//馆藏状态
            return mJSONObject.getJSONObject(barcode).getString("returnDate");

        }catch (Exception e){
            System.out.println(e);

        }
        return "";
    }
    public static String TimeStamp2Date(String timestampString){
        Long timestamp = Long.parseLong(timestampString);
        String date = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date(timestamp));
        return date;
    }
}

