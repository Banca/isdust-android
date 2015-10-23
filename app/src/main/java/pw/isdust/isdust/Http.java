package pw.isdust.isdust;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
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
    private int mconditon;//0为未成功，1为请求成功，2为请求失败，3为未知错误
    private  String mresult_string;

    private CookieManager cookieManager;
    private Bitmap mBitmap;

    public Http(){
        mresult_string="";
        mHTTP=new OkHttpClient();
        newcookie();


        //mHTTP.setProxy(proxy);

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
    public String get_string(String url){
        mconditon=0;
        mresult_string="";
        Request request = new Request.Builder().url(url).build();
        Call call = mHTTP.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mresult_string="";

                mconditon=3;

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    //String a=response.body().string();
                    mresult_string=response.body().string();
                    mconditon=1;
                }else {
                    mresult_string="";
                    mconditon=2;

                }
                //System.out.println(0);

            }
        });

        while(mconditon==0){
            try{
                Thread.sleep(100);}
            catch (Exception e){
                System.out.println("wzq"+e);

            }
        };

        return mresult_string;
    }
    public  String get_string(String url,final String bianma){
        mconditon=0;
        mresult_string="";
        Request request = new Request.Builder().url(url).build();
        Call call = mHTTP.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mresult_string="";

                mconditon=3;

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    //String a=response.body().string();
                    if(bianma=="gb2312"){
                        mresult_string = new String(response.body().bytes(),"gb2312");

                    }
                    mconditon=1;
                }else {
                    mresult_string="";
                    mconditon=2;

                }
                //System.out.println(0);

            }
        });

        while(mconditon==0){
            try{
                Thread.sleep(100);}
            catch (Exception e){
                System.out.println("wzq"+e);

            }
        };

        return mresult_string;
    }
    public  Bitmap get_image(String url){
        mconditon=0;
        Request request = new Request.Builder().url(url).build();
        Call call = mHTTP.newCall(request);

        try {
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Request request, IOException e) {
                    mBitmap=null;
                    mconditon=3;


                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()){

                        InputStream a = response.body().byteStream();
                        mBitmap = BitmapFactory.decodeStream(a);
                        mconditon=1;
                    }else {
                        mBitmap=null;
                        mconditon=2;

                    }
                    System.out.println(0);

                }
            });
        }catch (Exception e){
            System.out.println();
        }


        while(mconditon==0){
            try{
                Thread.sleep(100);}
            catch (Exception e){

            }
        };

        return mBitmap;
    }
    public  String post_string(String url,String postbody){
        mconditon=0;
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


        Call call = mHTTP.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mresult_string="";
                mconditon=3;

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    mresult_string=response.body().string();
                    mconditon=1;

                }else {
                    mresult_string="";
                    mconditon=2;

                }
                System.out.println(0);

            }
        });

        while(mconditon==0){
            try{
                Thread.sleep(100);}
            catch (Exception e){

            }
        };

        return mresult_string;


    }


    public  String post_string_noturlencode(String url,String postbody){
        mconditon=0;
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


        Call call = mHTTP.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mresult_string="";
                mconditon=3;

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    mresult_string=response.body().string();
                    mconditon=1;

                }else {
                    mresult_string="";
                    mconditon=2;

                }
                System.out.println(0);

            }
        });

        while(mconditon==0){
            try{
                Thread.sleep(100);}
            catch (Exception e){

            }
        };

        return mresult_string;


    }

    public String post_string(String url,String postbody, final String bianma){
        mconditon=0;
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

        Call call = mHTTP.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mresult_string="";
                mconditon=3;

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    //mresult_string=response.body().string();
                    if(bianma=="gb2312"){
                        mresult_string = new String(response.body().bytes(),"gb2312");

                    }
                    mconditon=1;

                }else {
                    mresult_string="";
                    mconditon=2;

                }
                System.out.println(0);

            }
        });

        while(mconditon==0){
            try{
                Thread.sleep(100);}
            catch (Exception e){

            }
        };

        return mresult_string;


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