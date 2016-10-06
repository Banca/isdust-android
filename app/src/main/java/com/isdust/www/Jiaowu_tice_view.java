package com.isdust.www;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.isdust.www.baseactivity.BaseSubPageActivity;

/**
 * Created by wzq on 16/1/18.
 */
public class Jiaowu_tice_view extends BaseSubPageActivity {
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.activity_jiaowu_tice_view, "体质健康测试成绩查询");
        mWebView=(WebView)findViewById(R.id.webView_tice);
        WebSettings mwebSettings = mWebView.getSettings();
        mwebSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        mwebSettings.setAllowFileAccess(true);
        //设置支持缩放
        mwebSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
//        OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
        Intent intent=this.getIntent();
        String address=intent.getExtras().getString("address");
        mWebView.getSettings().setDefaultTextEncodingName("gbk");
        //String news_website_address= OnlineConfigAgent.getInstance().getConfigParams(mContext, "news_website_address");
        //news_website_address="http://isdust.com/tice/chaxun.php?name=%E5%88%98%E4%B8%BD%E5%A8%87&xuehao=201201031216";
        mWebView.loadUrl(address);
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

