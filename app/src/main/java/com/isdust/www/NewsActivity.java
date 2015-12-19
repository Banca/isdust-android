package com.isdust.www;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.umeng.onlineconfig.OnlineConfigAgent;

/**
 * Created by Wang Ziqiang on 2015/10/17.
 * isdust
 * Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class NewsActivity extends BaseMainActivity_new {
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INIT(R.layout.helper_news, "校园资讯",6);
        mWebView=(WebView)findViewById(R.id.webView_news);
        WebSettings mwebSettings = mWebView.getSettings();
        mwebSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        mwebSettings.setAllowFileAccess(true);
        //设置支持缩放
        mwebSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
//        OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
        String news_website_address= OnlineConfigAgent.getInstance().getConfigParams(mContext, "news_website_address");
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
