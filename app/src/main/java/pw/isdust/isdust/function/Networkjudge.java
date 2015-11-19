package pw.isdust.isdust.function;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.io.IOException;

import pw.isdust.isdust.Http;

/**
 * Created by wzq on 15/10/14.
 */
public class Networkjudge {
    Context mContext;
    Http mHttp;
    public Networkjudge(Context context){
        mContext=context;
        mHttp=new Http();
    }
    public int judgetype(){
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        String ssid=getCurrentSsid(mContext);

        if (ssid==null&&mobile.toString().equals("DISCONNECTED")){
            return 0;
        }
        else if(ssid==null&&mobile.toString().equals("CONNECTED")){
            return 3;
        }
        else if (ssid.contains("CMCC")){
            return 1;
        }
        else if (ssid.contains("ChinaUnicom")){
            return 2;
        }

        return 4;//0.无网络1.CMCC2.CHINAUNICOME3.纯数据4.其它WIFI
    }
   public int cmcc_judge() throws IOException {
       String text= null;

           text = mHttp.get_string("http://172.16.0.86/","gb2312");

       if (text.contains("已使用时间")){
           try {
               text= mHttp.get_string("http://baidu.com");
           } catch (IOException e) {
               e.printStackTrace();
           }
           if (text.contains("<meta http-equiv=\"refresh\" content=\"0;url=http://www.baidu.com/\">")){
               return 2;
           }
           return 1;
       }
       return 0;//0.没有登录1.登录一层2.登录二层
   }

    public boolean isOnline() {
        String text= null;
        try {
            text = mHttp.get_string("http://baidu.com");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (text.contains("<meta http-equiv=\"refresh\" content=\"0;url=http://www.baidu.com/\">")){
            return true;
        }
        return false;
    }

    public int chinaunicom_judge() throws IOException {
        String text= null;

            text = mHttp.get_string("http://10.249.255.253/","gb2312");

        if (text.contains("已使用时间")){
            return 1;
        }
        return 0;//0.没有登录1.登录一层
    }
    public int neiwaiwang_judge(){//对于连接的不是cmcc的wifi判定
        String html= null;
        try {
            html = mHttp.get_string("http://192.168.109.62/");
        } catch (IOException e) {
            e.printStackTrace();
            return 1;//外网
        }
        if (html.contains("RadioButtonList1_0")){
            return 0;//内网
        }
        return 0;
    }
    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
        }
        return ssid;
    }
}
