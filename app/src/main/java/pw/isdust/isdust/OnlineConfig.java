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
    static String config_default="PxpKV19QIEZeIB5ANycrUFJdZnhmAAkIAxQLdXR2EWlmS1pcUlExX1caOU0nMywbCRF1YGgQSUtaQUAaJCVXNyFLShYNF3UAC2twCntsdAoAHXZxcRAVG0VLVj08HkMqNkwbDhUGdAMCZ20aKyw2TVJfKGB+EE1LQFwbaWcySjYwXVRrVUcrUlYmIEs2YH8bAh0YN3JQCwtpTAEjJiRvMHFaDwJrQHFVAyMdTSQkdVpvRnEgcgRlTAAAW3wZNAcgIVtlQQIHJQNuMHQJdHcZTAoEcXsYRwFbUF1lMH0jUnQYTQ5WDgIYRgRyclkeN3AKB1UYN3BUCFgZSEgZMHZVJHBkTAEEU3NvR3J5CHN4ZQ0EC3B1cwENAWlXC2sZNAYncg5lQQENdgJuMHReJyAZTAoDdSMYRw8OBV1lMHBzUnQYTQwCAQ0YRgchJwoeN3AIVVIYN3MBWwlpTA9wcXRvMH0OAFdrQCJVAiYdTXR0fQtvRnJ3IgRlTAAKXCMZNAt1IlxlQQEAIQNuMHdbJncZTAdVcyQYRw4MBwFlK3ZvbzBzC1sEa0B8UVQgHU16enMBb0ZwJ3RWZUwACFpzGTQEfXJdZUFRU3RQbjB5WiR1GUwEUXB7GEcMXw0MZTBwcld0GE0MUQcGGEYEcCMIHjdyCwcLGDdyBQtaaUwBdid2bzBxC10Ca0ByBVRxHU13cyYPb0ZzenJXZUwNW18gGTQLfXIAZUECBHxXbjB5XiYgGUwLC3AhGEcNXwJfZTBydAF9GFYZFBcVZBMSZWEVb28SY2IRaGA0R1tVXFpSIDweVig0TEBXW1Q3QEAqLlVgeGd0enoGCy5zd3tSUkgtLih0fDMIe3VmcAJycwoCeRN6BHR6egYBI3l6eGR8eHUTK2siCFNUfBx3NXdAPA5oARgrZV1eCAswVEt+V1FALnEyfwkDYWxuXFINQ0Afd1EVE3xucnFvBRxaVHVWcnApCBt4KgFgUXoAZyFyB3wTbABzLHJBbyoDcGRMb01MCwYQcGkTc3VzeEBAd0pkLjhVJxcva15vazopBwxqUE1cAyYdHAsGQH9QWn8Lbx0WL34rIXdvaXk0GiYFZVceT38sHTJ4N3FvWgN5BggCazwQa3N0BndWRRcoLFBtemdvaTMdagsxNUp/dg9gDmpnPDNvext0CUppdSQCAU5/aVd1NDcVCxlrVHJlb0UnAWIIDUslBiJTclF3DwFLfm52ClBxLCpfEBxkFn1SXi5HRxwzVilzFwlVdyN6BWVASUB0SX0ZL313LnB2bXIBL3lYCSxpNhcreHVhIx5rZnwKdEBKAX0HZSoHaQhSTQMiW3R1eWIodglYXlUycSlwdGUaYXp8LQ8EDxhWWmV+cQVicwdjFGAyMFtfWicpIUtmSkVWSzExJEAxZgIbeX5yIn5zdQZ7ETMCanpRdwYVd3t4ZGx4BHEGfQQAe3tdZn4GVGMGdmslFhkWelgqA3R0dW1pFnADMglGDAx2fVlmaSoEfSguXh5tNG5YfB0LJwd8XgxSTAYpKgAQHG9sbX1QHEtQLjhfLggiXXVGHigIAWNoVwhgLT8RBDN0QkFiZlp1fW4reAhwOxcAVWMeEhN2UX5QX10ZahcCBAx7f2ZAcAdAcycSdRYuGRYDeRhtGB1vfVltYRMALVI3DEJMcQR8cl1QCHFqARoZV2d+EQ0yXVNgclFwDSQVaxRvaXBwdmQFcRBpY1kmJjdcQEAbOCxXV15TWFciZ3sRdH0KFwUBDWoCAnxvCnF2GVcCCnZsdQQBFwQJAGt3cgAZKgkABhkEcgscdHEBbHB2CG9ddXt2HAgPDRcIdXxvAXdzZFcFDgdqAgR9bwlye2sLAQUYLHULCxcEDwFrdHEKa3YKDGhZBH0BHHR3AGxzdQAdAXZ2Zh4bWFFPXDcxKEAgKV1XQBUPH0gQMShMLidnAxFvMXRxUAllQA4MdCMdRnB1DgxoQgN8AQNnbRorLyReVhF+YCxGTUkPZRYZaiBDNWpRSlBCRjAdUSosZG0jIWUcUiBzalhJXhcVGzA3LRF/ZlBNQEcPGBxuaiUWLyMuWB1aKR5rQlpPXFxOIDcdHBAKdgl4B2RwER5nLFx3YH8bUgJ3J3QCWFsCCl99IHgHJiBbD1JSBiIGUXZ3XHshIAwRThluZkFaUVpWVRohIEcgZgIbBgcEch4CfGwId2J1CQkDdHh0AhsVF1NQJCo2RhonUFxaUF8tbEowJFYrIysbCREqNyheZVcHCQhwaHMDdHJkVwYHBHAeAHVwDR4sdwkCAGlwdAMNZVsLCXR3bAF1dQtlWhUZZkNAKjlBHSkwWFpHKywjbU5cVlFYMWd7ETE2TVwWGxc0QV09OGcpNyRQR1wqJRtXSVhMGwNnMTNGIGYUG0FHUSVHV2d7GjkeZ0xBXxhgfm4bUUFNSTZ/HW8Za2RlaBhHJUQcIihMKjcnTEBWNiErXE1cW00XJiosbxkYF19RXlYtVlwZHWRtIytdQVwtJmlTTE1aFEw1ISBHIBhkZRtTUDJWXioxZB4ealxLRzYjN25lZRpYVyE3LlohaVlMQFgYMUNWJDVdbzR0FwIdJTIvbhsVaRtPIDcyWioqe1ZQUmlmCQBpHRo3MiFYR1YJJzdBWF5QZRt/GWMCa2RkZUEBACYDbhk0DXp7IGFrbxg3cQAAX2llTH11J1d+eFpLaGtpaw0Aa2FkHjdxX1ZWGB4xBwAJUWVlMHEkC3MGTV4PC1c2b24ZbgZxbGVlb0ZwJHUKZWVADAp0cx1vMHBdAQJraTEFAnd2ZB43fQlVVxgeMQEJCQdlGzhnPA==";
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
