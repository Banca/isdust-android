package com.isdust.www;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.isdust.www.baseactivity.BaseSubPageActivity;

import java.util.List;

/**
 * Created by Administrator on 2015/10/14.
 */
public class SchoolMapActivity extends BaseSubPageActivity implements View.OnClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Context context;

    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;//定位的监听器
    private boolean isFirstIn = true;//判断是否为第一次的定位
    private double mLatitude;//记录经度
    private double mLongtitude;//记录纬度

    //覆盖物相关
    private BitmapDescriptor mMarker;
    private RelativeLayout mMarkerLy;

    //界面按键
    Button mylocation_button;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        INIT(R.layout.schoolmap, "山科地图");

        mylocation_button= (Button) findViewById(R.id.mylocation);
        this.context = this;
        initView();//初始化地图
        initLocation();//初始化定位

        initMaker();//初始化覆盖物

        //监听是否点击覆盖物
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                Info info = (Info) extraInfo.getSerializable("info");
                ImageView iv = (ImageView) mMarkerLy
                        .findViewById(R.id.id_info_img);
                TextView distance = (TextView) mMarkerLy
                        .findViewById(R.id.id_info_distance);
                TextView name = (TextView) mMarkerLy
                        .findViewById(R.id.id_info_name);
                TextView zan = (TextView) mMarkerLy
                        .findViewById(R.id.id_info_zan);
                iv.setImageResource(info.getImgId());
                distance.setText(info.getDistance());
                name.setText(info.getName());
                zan.setText(info.getZan() + "");

                InfoWindow minfoWindow;
                TextView tv = new TextView(context);
                tv.setBackgroundResource(R.drawable.location_tips);
                tv.setPadding(30, 20, 30, 50);
                tv.setText(info.getName());
                tv.setTextColor(Color.parseColor("#ffffff"));

                final LatLng latLng = marker.getPosition();
                Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
                p.y -= 47;
                LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

//                OnInfoWindowClickListener listener= new OnInfoWindowClickListener() {
//                    @Override
//                    public void onInfoWindowClick() {
//                        mBaiduMap.hideInfoWindow();
//                    }
//                };
                //为弹出的InfoWindow添加点击事件
                minfoWindow = new InfoWindow(tv, ll, -10);

                mBaiduMap.showInfoWindow(minfoWindow);
                mMarkerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });
        //监听点击地图非覆盖物使覆盖我消失
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMarkerLy.setVisibility(View.GONE);
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        mylocation_button.setOnClickListener(this);

    }

    private void initMaker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
        mMarkerLy = (RelativeLayout) findViewById(R.id.id_maker_ly);
        //使用Intent获得参数
        Intent intent = getIntent();
        String judge = intent.getStringExtra("judge");
        if(judge.equals("ATMmap"))
            addOverloay(Info.infos1);
        else if(judge.equals("printmap"))
            addOverloay(Info.infos2);
        else if(judge.equals("hospitalmap"))
            addOverloay(Info.infos3);
        else if(judge.equals("expressmap"))
            addOverloay(Info.infos4);
        else if(judge.equals("KTVmap"))
            addOverloay(Info.infos5);
        else if(judge.equals("barbershopmap"))
            addOverloay(Info.infos6);
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);//注册Listener
        //设置Client
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);

        mLocationClient.setLocOption(option);
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);//设置地图比例尺100米
        mBaiduMap.setMapStatus(msu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    //开启定位
    @Override
    protected void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted())
        mLocationClient.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();

    }

    //关闭定位
    @Override
    protected void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    //添加覆盖物
    private void addOverloay(List<Info> infos) {
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for (Info info : infos)
        {
            // 经纬度
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            // 图标
            options = new MarkerOptions().position(latLng).icon(mMarker)
                    .zIndex(5);
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info", info);
            marker.setExtraInfo(arg0);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

    //定位到我的位置
    private void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitude,mLongtitude);//获得经纬度
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);//设置经纬度
        mBaiduMap.animateMapStatus(msu);
    }

    @Override
    public void onClick(View v) {
        centerToMyLocation();//点击按钮定位到我的位置
    }


    private class MyLocationListener implements BDLocationListener
    {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //转化BDLocation
            MyLocationData data = new MyLocationData.Builder()//
            .accuracy(location.getRadius())//
            .latitude(location.getLatitude())//
            .longitude(location.getLongitude())//
            .build();
            mBaiduMap.setMyLocationData(data);


            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();

            if(isFirstIn)
            {
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());//获得经纬度
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);//设置经纬度
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;
                //弹出一个定位位置框
               Toast.makeText(context, location.getAddrStr(),
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

}


