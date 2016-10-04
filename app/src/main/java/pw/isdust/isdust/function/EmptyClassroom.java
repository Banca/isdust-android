package pw.isdust.isdust.function;

import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import com.isdust.www.datatype.ScheduleInformation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import pw.isdust.isdust.Http;
import pw.isdust.isdust.OnlineConfig;

/**
 * Created by Wang Ziqiang on 15/10/15.
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
public class EmptyClassroom {
    String publickey="";
    ScheduleInformation[] mScheduleInformations;
    JSONArray mJSONArray;
    Http mHttp;
    Context mContext;
    private static final int MAX_ENCRYPT_BLOCK = 400;
    public static final String KEY_ALGORITHM = "RSA";

    public EmptyClassroom(Context context) throws Exception {
        mHttp=new Http();
        mContext=context;
//        OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
        publickey=OnlineConfig.getConfigParams( "EmptyClassroom_publickey");
        if (publickey==""){
            Exception e=new Exception("OnlineConfigFail");
            throw e;
        }
        publickey=publickey.replace("\r", "");
        publickey=publickey.replace("\n","");
        System.out.println(publickey);
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
        String id= Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        long time = System.currentTimeMillis()/1000;
        String md5=id+"wzq123"+time;
        md5=Networklogin_CMCC.md5(md5);
        String submit_pre="{\"time\":"+time+",\"key\":\""+md5+"\",\"id\":\""+id+"\",\"building\":\""+building+"\",\"location\":\"\",\"zhoushu\":\""+schooldate+"\",\"xingqi\":\""+week+"\",\"jieci\":\""+jieci+"\",\"method\":4}";
        String submit="";
        try {
            submit=RSACryptUtil.encryptByPublicKey_string(submit_pre,publickey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        submit=submit.replace("==","");
        //?method=4&building=&zhoushu=&xingqi=&jieci=
        String text=mHttp.post_string("http://kzxs.isdust.com/chaxun_new.php","data="+submit+"&verification="+Networklogin_CMCC.md5(submit+"dsfwedsdv"+time)+"&time="+time);

//        String text=mHttp.get_string("http://kzxs.isdust.com/chaxun.php?method=4&building="+building+"&zhoushu="+ schooldate +"&xingqi="+week+"&jieci="+jieci);
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
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {

        byte[] keyBytes = Base64.decode(publicKey,1);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
}
