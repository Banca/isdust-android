package pw.isdust.isdust.function;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/19.
 */
public class Library {
    Http mHttp;
    String [] mPersonalInformation;
    public Library(){
        mHttp=new Http();
    }
    public String login(String user,String password){
        String msubmit="rdid="+user+"&rdPasswd="+md5(password)+"&returnUrl=";
        String text= mHttp.post_string("http://interlib.sdust.edu.cn/opac/reader/doLogin",msubmit);
        if (text.contains("读者姓名")){
            Pattern mPattern=Pattern.compile("<font class=\"space_font\">([\\S\\s]*?)</font>");
            Matcher mMatcher=mPattern.matcher(text);
            List<String> array_temp=new ArrayList<String>();
            while (mMatcher.find()){
                mMatcher.start();
                array_temp.add(mMatcher.group(1));
                mMatcher.end();
            }
            int len=array_temp.size();
            mPersonalInformation=new String[len];
            for (int i=0;i<len;i++){
                mPersonalInformation[i]=array_temp.get(i).toString();

            }


            return "登录成功";
        }
        return "账号或密码错误";

    }
    public String get_name(){
        return mPersonalInformation[1];
    }
    public String [] [] get_borrwingdetail(){
        String text=mHttp.get_string("http://interlib.sdust.edu.cn/opac/loan/renewList");
        Pattern mPattern=Pattern.compile("<td width=\"40\"><input type=\"checkbox\" name=\"barcodeList\" value=\"([0-9]*?)\" />[\\s\\S]*?target=\"_blank\">([\\S\\s]*?)</a></td>[\\S\\s]*?<td width=\"100\">([0-9]{4}-[0-9]{2}-[0-9]{2})[\\S\\s]*?<td width=\"100\">([0-9]{4}-[0-9]{2}-[0-9]{2})");
        Matcher mMatcher=mPattern.matcher(text);
        List<String []> array_temp=new ArrayList<String []>();
        while(mMatcher.find()){
            String [] temp= new String[4];
            mMatcher.start();
            for (int j=0;j<4;j++){
                temp[j]=mMatcher.group(j+1);
            }
            array_temp.add(temp);

        }
        int len=array_temp.size();
        String [] [] result=new String[len][4];
        for (int i=0;i<len;i++){
            String [] temp=array_temp.get(i);
            result[i]=temp;
        }
        return result;
    }
    public Book [] xml_analyze_jianjie(String text) throws ParserConfigurationException, IOException, SAXException {
//        String text=mHttp.get_string("http://interlib.sdust.edu.cn/opac/book/holdingpreview/248193");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(text));

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);
        NodeList nodes = document.getElementsByTagName("record");
        int len=nodes.getLength();
        Book result []=new Book[len];
        String  []c=new String[6];
        for (int i=0;i<len;i++){
            for (int j=0;j<6;j++){
                c[j]=nodes.item(i).getChildNodes().item(j).getChildNodes().item(0).getNodeValue();
            }
            Book temp=new Book(c[0],c[2],c[4],c[5]);
            result[i]=temp;
        }


        return result;
    }
    public void json_analyze(){
        String text=mHttp.get_string("http://interlib.sdust.edu.cn/opac/api/holding/1900737876");
        try {
            JSONArray mJSONArray=new JSONArray(text);
            int len=mJSONArray.length();
        }catch (Exception e){
            System.out.println(e);

        }

    }
    public String renew_all(){
        String text=mHttp.post_string("http://interlib.sdust.edu.cn/opac/loan/doRenew","furl=%2Fopac%2Floan%2FrenewList&renewAll=all" );
        String content=Networklogin_CMCC.zhongjian(text, "<div style=\"margin:20px auto; width:50%; height:auto!important; min-height:200px; border:2px dashed #ccc;\">", "<input", 0);
        content=content.replace("\t","");
        content=content.replace("\n","");
        content=content.replace("\r", "");
        String [] temp_array=content.split("<br/>");
        int len=temp_array.length-2;
        String result="";
        for(int i=0;i<len;i++){
            result=result+temp_array[i+1]+"\n";
        }
        return result;

    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";}
    public
    class Book{
        String mFindingNumber;//索书号
        String mLibryLocation;//所在馆
        String mSpecificLocation;//所在馆藏地点
        String mCopy;//在馆复本数
        public Book(String FindingNumber, String LibryLocation,String SpecificLocation,String Copy){
            mFindingNumber=FindingNumber;
            mLibryLocation=LibryLocation;
            mSpecificLocation=SpecificLocation;
            mCopy=Copy;
        }
        public String get_FindingNumber(){
            return  mFindingNumber;
        }
        public String get_LibryLocation(){
            return  mLibryLocation;
        }
        public String get_SpecificLocation(){
            return  mSpecificLocation;
        }
        public String get_Copy(){
            return  mCopy;
        }
    }
}
