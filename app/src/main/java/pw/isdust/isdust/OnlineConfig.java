package pw.isdust.isdust;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

/**
 * Created by wzq on 10/4/16.
 */

public  class OnlineConfig {
    static String config_default="{\n" +
            "\t\"schedule_xuenian\":\"2016-2017\",\n" +
            "\t\"schedule_xueqi\":\"1\",\n" +
            "\t\"EmptyClassroom_publickey\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0VjXgLkmH+BqDryOPCZn\\nmLItfrGbhyk4sLLGYUZkgIprZ6iWQ9WAB+GXhmLcKIlMZKoEXhN7ReA59RTB1iKr\\nA4VuVxu2CU1ZV7MJLwu3yVkymeUjRm/xm55SeteFc/NBxFdmJO/SnFic2VZJpXb7\\n+vFiXsKr5Wc7N3L1YyQS16CNevSjhbTCRVPvX+8tqrFB8UJYUyrW9Y10yZ1fF3wF\\nLqrT8/lKQXpc2PMLsgDgjAb3MEyGWC3i4iklUX/IekjtuYrnk1R0fDg8AWypuMp8\\nN2jHOYE4kJjLmQtUnAFRg/TE3AysD8FVoCQ1fz6fhF08Zj4Lamfv3mBM/XC9hN7J\\ncQIDAQAB\",\n" +
            "\t\"proxy1_address\":\"139.129.133.235\",\n" +
            "\t\"proxy1_port\":\"3000\",\n" +
            "\t\"proxy_kuaitong_wechat\":\"true\",\n" +
            "\t\"proxy_kuaitong_epay\":\"true\",\n" +
            "\t\"news_website_address\":\"http://bbs.isdust.com\",\n" +
            "\t\"install\":\"true\",\n" +
            "\t\"jiaowu_chengji_xuenian\":\"null\\n2015-2016\\n2014-2015\\n2013-2014\\n2012-2013\\n\",\n" +
            "\t\"system_broadcast\":\"1.欢迎学弟，学妹们加入静语计算机协会,qq群号码: 478477348 2.学校快通服务器已出现故障，暂时可能无法使用 3.现课表不准确，请等待发布新版本获取更准确课表再进行使用 ---WZQ\",\n" +
            "\t\"school_date\":\"2016-09-05 00:00:00\",\n" +
            "\t\"tice_publickey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7SgT/IknA0FLT/IFwHuIHNDmQ\\n7Omof/qWkOYIc5Eg9kuClk3UXWUYJeXxbkyglJgdFuZjL3ZQb1YhzP7v0zxVQo1N\\n902yR9fPZPWDhGefd/V1AHCFRwECsAbSMTl/0J//VDlTXVElarHzuE3I6nbM0RCX\\nTMUOvojYGhIHaTXQ+QIDAQAB\",\n" +
            "\t\"address_zhengfang\":\"192.168.109.231\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "}";
    static String config_current="";
    static String config_remote="";
    static String config_local="";
    //static Context mcontext;
    public static void update(Context mcontext) {
        String text="";
        JSONObject mJSONObject;
        Http mhttp=new Http();
        try {
            text = mhttp.get_string("http://app.isdust.com/config_android.php");
            mJSONObject=new JSONObject(text);
            if(mJSONObject.getString("install").equals("true")){
                SharedPreferences.Editor preferences_editor;
                SharedPreferences preferences_data;
                preferences_data = mcontext.getSharedPreferences("OnlineConfig", Activity.MODE_PRIVATE);

                preferences_editor = preferences_data.edit();
                preferences_editor.putString("OnlineConfig",text);

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
            mJSONObject=new JSONObject(config_local);
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
            mJSONObject=new JSONObject(config_current);
            return mJSONObject.get(key).toString();
        }catch (Exception e){

        }
        return "";
    }
}
