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

/**
 * Created by wzq on 15/9/5.
 */
public class Http {
    private static OkHttpClient mHTTP=new OkHttpClient();
    private static int mconditon;//0为未成功，1为请求成功，2为请求失败，3为未知错误
    private static String mresult_string="";

    private static CookieManager cookieManager = new CookieManager();
    public static Bitmap mBitmap;
    static{

        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mHTTP.setCookieHandler(cookieManager);
    }
    public static String get_string(String url){
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
    public static String get_string(String url,final String bianma){
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
    public static Bitmap get_image(String url){
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
    public static String post_string(String url,String postbody){
        mconditon=0;
        //构造POST包
        String mstring_fenge1 [] =postbody.split("&");
        FormEncodingBuilder mFormEncodingBuilder =new FormEncodingBuilder();
        RequestBody mformBody ;
        for(int i=0;i<mstring_fenge1.length;i++){
            String mstring_fenge2 [] =mstring_fenge1[i].split("=");
            if(mstring_fenge2.length==2){
                mFormEncodingBuilder.add(mstring_fenge2[0],mstring_fenge2[1]);
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
    public static String post_string(String url,String postbody, final String bianma){
        mconditon=0;
        //构造POST包
        String mstring_fenge1 [] =postbody.split("&");
        FormEncodingBuilder mFormEncodingBuilder =new FormEncodingBuilder();
        RequestBody mformBody ;
        for(int i=0;i<mstring_fenge1.length;i++){
            String mstring_fenge2 [] =mstring_fenge1[i].split("=");
            if(mstring_fenge2.length==2){
                mFormEncodingBuilder.add(mstring_fenge2[0],mstring_fenge2[1]);
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
}
