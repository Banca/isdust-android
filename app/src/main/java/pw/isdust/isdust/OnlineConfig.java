package pw.isdust.isdust;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import pw.isdust.isdust.function.Base64Utils;
import pw.isdust.isdust.update.AppUtils;

/**
 * Created by wzq on 10/4/16.
 */

public  class OnlineConfig {
    static String config_default="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";
    static String config_current="";
    static String config_remote="";
    static String config_local="";
    static String key="D89475D32EA8BBE933DBD299599EEA3E";
    public static String decrypt(String content){
        byte[] content_byte = Base64Utils.decode(content);
        byte [] key_byte=key.getBytes();
        int content_len=content_byte.length;
        int key_len=key.length();
        for(int i=0;i<content_len;i++){
            content_byte[i]^=key_byte[i%key_len];

        }
        String result=new String(content_byte);
        return result;
    }
    public static void update(Context mcontext) {
        String text="";
        JSONObject mJSONObject;
        Http mhttp=new Http();
        try {
            text = mhttp.get_string("http://app.isdust.com/sysconfig.php?platform=android&version="+ AppUtils.getVersionCode(mcontext));
            mJSONObject=new JSONObject(decrypt(text));
            if(mJSONObject.getString("install").equals("true")){
                SharedPreferences.Editor preferences_editor;
                SharedPreferences preferences_data;
                preferences_data = mcontext.getSharedPreferences("OnlineConfig", Activity.MODE_PRIVATE);

                preferences_editor = preferences_data.edit();
                preferences_editor.putString("OnlineConfig",text);
                preferences_editor.commit();
            }
        }catch (Exception e){


        }
    }
    public static void load(Context mcontext){
        SharedPreferences preferences_data=mcontext.getSharedPreferences("OnlineConfig", Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferences_editor=preferences_data.edit();;
        JSONObject mJSONObject;


        preferences_editor = preferences_data.edit();

        config_local=preferences_data.getString("OnlineConfig","");
        if(config_local.equals("")){
            config_current=config_default;
            preferences_editor.putString("OnlineConfig",config_default);
            return;
        }
        try{
            mJSONObject=new JSONObject(decrypt(config_local));
            if(mJSONObject.getString("install").equals("true")){
                config_current=config_local;
                return;
              }
        }catch(Exception e){
            config_current=config_default;
        }
    }
    public static void updateandload(final Context mcontext){
        load(mcontext);
        Runnable mRunnable_update=new Runnable() {
            @Override
            public void run() {
                update(mcontext);
                load(mcontext);
            }
        };
        mRunnable_update.run();
    }

    public static String getConfigParams(String key){
        JSONObject mJSONObject;
        try {
            mJSONObject=new JSONObject(decrypt(config_current));
            return mJSONObject.get(key).toString();
        }catch (Exception e){

        }
        return "";
    }
}
