package pw.isdust.isdust;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by wzq on 15/9/5.
 */
public class Http {

    private OkHttpClient mHTTP;


    private CookieManager cookieManager;

    public Http(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);//允许主线程执行http
        mHTTP=new OkHttpClient();
        newcookie();
    }
    public void setProxy(String ip,int port){
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("121.201.13.50", 1999));
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));

        mHTTP.setProxy(proxy);
    }
    public void newcookie(){
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mHTTP.setCookieHandler(cookieManager);

    }
    public String get_string(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        Response response_1;
        response_1=mHTTP.newCall(request).execute();
        return response_1.body().string();




    }
    public  String get_string(String url,final String bianma) throws IOException {

        Request request = new Request.Builder().url(url).build();

        Response response_1;
        if (bianma=="gb2312"){
            response_1=mHTTP.newCall(request).execute();
            return new String(response_1.body().bytes(),"gb2312");}

        return null;
    }
    public  Bitmap get_image(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response_1;
            response_1=mHTTP.newCall(request).execute();
            InputStream a = response_1.body().byteStream();
            return BitmapFactory.decodeStream(a);


    }
    public  String post_string(String url,String postbody) throws IOException {
        //构造POST包
        String mstring_fenge1 [] =postbody.split("&");

        FormEncodingBuilder mFormEncodingBuilder =new FormEncodingBuilder();
        Request.Builder a =new Request.Builder();

        RequestBody mformBody ;

        for(int i=0;i<mstring_fenge1.length;i++){
            String mstring_fenge2 [] =mstring_fenge1[i].split("=");
            if(mstring_fenge2.length==2){
                mFormEncodingBuilder.add(mstring_fenge2[0], mstring_fenge2[1]);
            }if(mstring_fenge2.length==1){
                mFormEncodingBuilder.add(mstring_fenge1[i].replace("=",""),"");
            }
        }
        mformBody=mFormEncodingBuilder.build();
        //String b=mformBody.toString();
        //构造POST包
        System.out.println(mformBody.toString());



        //开始提交
        Request request = new Request.Builder().url(url).post(mformBody).header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36").build();

        Response response_1;
        response_1=mHTTP.newCall(request).execute();
        return response_1.body().string();






    }


    public  String post_string_noturlencode(String url,String postbody) throws IOException {
        //构造POST包
        String mstring_fenge1 [] =postbody.split("&");

        FormEncodingBuilder mFormEncodingBuilder =new FormEncodingBuilder();
        Request.Builder a =new Request.Builder();

        RequestBody mformBody ;

        for(int i=0;i<mstring_fenge1.length;i++){
            String mstring_fenge2 [] =mstring_fenge1[i].split("=");
            if(mstring_fenge2.length==2){
                mFormEncodingBuilder.addEncoded(mstring_fenge2[0], mstring_fenge2[1]);
            }if(mstring_fenge2.length==1){
                mFormEncodingBuilder.addEncoded(mstring_fenge1[i].replace("=", ""), "");
            }
        }
        mformBody=mFormEncodingBuilder.build();
        //String b=mformBody.toString();
        //构造POST包
        System.out.println(mformBody.toString());



        //开始提交
        Request request = new Request.Builder().url(url).post(mformBody).header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36").build();


        Response response_1;
        response_1=mHTTP.newCall(request).execute();
        return response_1.body().string();




    }

    public String post_string(String url,String postbody, final String bianma) throws IOException {
        //构造POST包
        String mstring_fenge1 [] =postbody.split("&");
        FormEncodingBuilder mFormEncodingBuilder =new FormEncodingBuilder();
        RequestBody mformBody ;
        for(int i=0;i<mstring_fenge1.length;i++){
            String mstring_fenge2 [] =mstring_fenge1[i].split("=");
            if(mstring_fenge2.length==2){
                mFormEncodingBuilder.addEncoded(mstring_fenge2[0], mstring_fenge2[1]);
            }
        }
        mformBody=mFormEncodingBuilder.build();
        //构造POST包
        System.out.println(mformBody.toString());



        //开始提交
        Request request = new Request.Builder().url(url).post(mformBody).build();


        Response response_1;
            if (bianma=="gb2312"){
                response_1=mHTTP.newCall(request).execute();
                return new String(response_1.body().bytes(),"gb2312");}

        return null;



    }
    public void  getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            mHTTP.setSslSocketFactory(sslSocketFactory);
            mHTTP.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}