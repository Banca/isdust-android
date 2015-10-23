package com.formal.sdusthelper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.comapi.mapcontrol.MapParams;

import java.util.List;

/**
 * Created by Administrator on 2015/10/14.
 */
public class SchoolMapActivity extends Activity {
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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.schoolmap);

        this.context = this;
        initView();//初始化地图
        initLocation();//初始化定位
        initMaker();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
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
                minfoWindow = new InfoWindow(tv,ll,-10);

                mBaiduMap.showInfoWindow(minfoWindow);
                mMarkerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });
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
    }

    private void initMaker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
        mMarkerLy = (RelativeLayout) findViewById(R.id.id_maker_ly);
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
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);//设置地图比例尺100米
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.id_map_location:
                //定位到我的当前位置
                centerToMyLocation();
                break;
            case R.id.id_add_overlay:
                addOverloay(Info.infos);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
            //定位图标
//            //设置定位的自定义图标
//            MyLocationConfiguration config = new//
//                   MyLocationConfiguration(mLocationMode, true, mIconLocation);
//            mBaiduMap.setMyLocationConfigeration(config);

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


