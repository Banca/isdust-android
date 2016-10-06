package pw.isdust.isdust.function;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.isdust.www.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import pw.isdust.isdust.*;
import pw.isdust.isdust.OnlineConfig;
/**
 * Created by wzq on 10/6/16.
 */

public class Advertisement {
    public String md5;
    public String url;
    public Bitmap image;
    private String image_url;
    public String title;
    Context mContext;
    Runnable downloadimage=new Runnable() {
        @Override
        public void run() {
            Bitmap image_temp;
            Http mhttp=new Http();
            try {

                image_temp=mhttp.get_image(image_url);
            } catch (Exception e) {
                image=BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.ad1);

                e.printStackTrace();
                return;
            }
            if (image_temp!=null){
                image=image_temp;
                saveimage(md5+".jpg",image);
            }


        }
    };
    public Advertisement(){}
    public Advertisement(Context context,String json){
        mContext=context;
        loadjson(json);
        loadordownload();

    }
    private void loadjson(String json){
        JSONObject mJSONObject;
        try {
            mJSONObject=new JSONObject(json);
            md5=mJSONObject.getString("md5");
            url=mJSONObject.getString("url");
            title=mJSONObject.getString("title");
            image_url=mJSONObject.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void loadordownload(){
        Bitmap image_temp=loadimage(md5+".jpg");
        if(image_temp==null){
            downloadimage.run();
        }else{
            image=image_temp;
        }


    }
    private String saveimage(String name,Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(mContext);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("ad", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    private Bitmap loadimage(String name)
    {
        try {
            ContextWrapper cw = new ContextWrapper(mContext);
            File directory = cw.getDir("ad", Context.MODE_PRIVATE);

            File f=new File(directory.getAbsolutePath(), name);
            Bitmap result = BitmapFactory.decodeStream(new FileInputStream(f));
            return result;
//            ImageView img=(ImageView)findViewById(R.id.imgPicker);
//            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            return null;
        }
    }
    public static Advertisement[] loadall(Context context){
        JSONArray mJSONArray;
        Advertisement[] result;
       try{
           mJSONArray=new JSONArray(OnlineConfig.getConfigParams("advertisement"));
           result=new Advertisement[mJSONArray.length()];
           for (int i=0;i< mJSONArray.length();i++){
               JSONObject mJSONObject=mJSONArray.getJSONObject(i);

               result[i]=new Advertisement(context,mJSONObject.toString());

           }
       }catch (JSONException e){
           return null;
       }
        return result;




    }
}
