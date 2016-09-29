package com.isdust.www.frame;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import test.pxy.newsreader.R;
import test.pxy.newsreader.weather.service.HttpUtils;
import test.pxy.newsreader.weather.utils.XmlTools;
import test.pxy.newsreader.weather.weather.WeatherBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    private View v = null;
    private TextView tx;
    private static Scanner in;
    private static InputStream is = null;
    private static WeatherBean weather = null;
    private ProgressDialog pd;

    Handler myhamdle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tx.setText(((WeatherBean) msg.obj).toString());
            }else if(msg.what==0){
                tx.setText("未找到该地区");
            }
            super.handleMessage(msg);
        }
    };

    public Fragment1() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragment1, null);
        tx = (TextView) v.findViewById(R.id.result);
        tx.setMovementMethod(ScrollingMovementMethod.getInstance());

        Button b = (Button) v.findViewById(R.id.button);
        final EditText ed = (EditText)v.findViewById(R.id.edt_lo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loca = ed.getText().toString();
                if(loca.trim().equals("")){
                    Toast.makeText(getActivity(),"地名不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    getWeather(loca);
                    pd = ProgressDialog.show(getActivity(), "请稍后...", "正在加载数据", true, true);
                }
            }
        });
        return v;
    }

    private void getWeather(final String url) {
        final String path = "http://wthrcdn.etouch.cn/WeatherApi?citykey=";
        String[] location = getResources().getString(R.string.locations).split(" ");

        Map<String, String> locations = new HashMap<>();
        for (int i = 0; i < location.length; i++) {
            String[] ky = location[i].split("=");
            locations.put(ky[1], ky[0]);
        }
        final Message msg = new Message();
        if (locations.containsKey(url)) {
            final String urls = locations.get(url);
            System.out.println("yes");
            Log.i("url:", path + urls);
            new Thread() {
                public void run() {
                    try {
                        is = HttpUtils.getInputStream(path + urls);
                        if (is != null) {
                            weather = XmlTools.parseXML(is, "utf-8");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    msg.what=1;
                    msg.obj = weather;
                    myhamdle.sendMessage(msg);
                    pd.dismiss();
                }
            }.start();
        } else {
            myhamdle.sendEmptyMessage(0);
            System.out.println("no place!!!");
            pd.dismiss();
        }
    }
}
