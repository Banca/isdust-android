package com.formal.sdusthelper.datatype;

import android.graphics.Bitmap;

import pw.isdust.isdust.Http;
import pw.isdust.isdust.function.Networklogin_CMCC;

/**
 * Created by wzq on 15/10/28.
 */
public class Book {
    String mname;
    String mwriter;
    String mpublisher;
    String mpublishedday;
    Bitmap mBitmap;
    String mbookrecno;
    String mISBN;
    Http mHttp;
    String mSuoshuhao;
//    Book(String name,String writer,String publisher,String publishday){
//        mname=name;
//        mwriter=writer;
//        mpublisher=publisher;
//        mpublishedday=publishday;
//    }

    public Book(){
        mHttp=new Http();

    }

    public String getName(){
        return  mname;
    }
    public String getbookrecno(){
        return  mbookrecno;
    }
    public String getWriter(){
        return  mwriter;
    }
    public String getPublisher(){
        return  mpublisher;
    }
    public String getPublishedday(){
        return  mpublishedday;
    }
    public String getISBN(){
        return  mISBN;
    }
    public String getSuoshuhao(){
        return  mSuoshuhao;
    }
    public Bitmap getCover(){
        return mBitmap;
    }
    public void setName(String name ){
        mname=name;
    }
    public void setbookrecno(String bookrecno ){
        mbookrecno=bookrecno;
    }
    public void setWriter(String writer){
        mwriter=writer;
    }
    public void setPublisher(String publisher){
        mpublisher=publisher;
    }
    public void setPublishedday(String publishday){
        mpublishedday=publishday;
    }
    public void setISBN(String ISBN){
        mISBN=ISBN;
    }
    public void setSuoshuhao(String Suoshuhao){
        mSuoshuhao=Suoshuhao;
    }
    public void downloadpicture(){
        String json=mHttp.get_string("http://api.interlib.com.cn/interlibopac/websearch/metares?cmdACT=getImages&isbns="+mISBN);
        String coverlink=Networklogin_CMCC.zhongjian(json,"coverlink\":\"","\",\"handleTi",0);
        mBitmap=mHttp.get_image(coverlink);

    }
    public void downloadSuoshuhao(){

    }
}
