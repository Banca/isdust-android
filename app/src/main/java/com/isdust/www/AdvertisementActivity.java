package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.isdust.www.baseactivity.BaseSubPageActivity;

/**
 * Created by Wang Ziqiang on 2015/10/17.
 * isdust
 * Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

 */
public class AdvertisementActivity extends BaseSubPageActivity {
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=this.getIntent();
        String title= intent.getStringExtra("title");
        String url= intent.getStringExtra("url");
        INIT(R.layout.helper_news, title);
        ////MobclickAgent.onEvent(this, "Advertisement");

        mWebView=(WebView)findViewById(R.id.webView_news);
        WebSettings mwebSettings = mWebView.getSettings();
        mwebSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        mwebSettings.setAllowFileAccess(true);
        //设置支持缩放
        mwebSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
//        OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
        String news_website_address=url;
        //news_website_address="http://isdust.com/tice/chaxun.php?name=%E5%88%98%E4%B8%BD%E5%A8%87&xuehao=201201031216";
        mWebView.loadUrl(news_website_address);
        //设置Web视图
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });    }


}
