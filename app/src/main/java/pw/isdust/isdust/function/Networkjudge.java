package pw.isdust.isdust.function;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

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
        if (ssid.contains("CMCC")){
            return 1;
        }
        if (ssid.contains("Chinaunicom")){
            return 2;
        }
        if (ssid.equals(null)||mobile.equals("DISCONNECTED")){
            return 0;
        }
        if(ssid.equals(null)||mobile.equals("DISCONNECTED")){
            return 3;
        }
        return 0;//0.无网络1.CMCC2.CHINAUNICOME3.纯数据
    }
   public int cmcc_judge(){
      String text= mHttp.get_string("http://172.16.0.86/","gb2312");
       if (text.contains("注销")){
           text= mHttp.get_string("http://www.baidu.com");
           if (text.contains("百度")){
               return 2;
           }
           return 1;
       }
       return 0;//0.没有登录1.登录一层2.登录二层

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
