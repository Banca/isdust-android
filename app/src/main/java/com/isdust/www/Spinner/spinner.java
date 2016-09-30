package com.isdust.www.Spinner;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.isdust.www.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class spinner{

    private ViewPager mViewPaper;
    private View v;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    private Activity thisActivity;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.guancang_head2,
            R.drawable.guancang_head2,
            R.drawable.guancang_head2,
            R.drawable.guancang_head2,
            R.drawable.guancang_head2
    };
//    //存放图片的标题
//    private String[]  titles = new String[]{
//            "巩俐不低俗，我就不能低俗",
//            "扑树又回来啦！再唱经典老歌引万人大合唱",
//            "揭秘北京电影如何升级",
//            "乐视网TV版大派送",
//            "热血屌丝的反杀"
//    };
//    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;


    public spinner(Activity activity, View v){
        thisActivity=activity;
        this.v=v;
    }

    public void init() {

        mViewPaper = (ViewPager) v.findViewById(R.id.vp);

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(thisActivity);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(v.findViewById(R.id.dot_0));
        dots.add(v.findViewById(R.id.dot_1));
        dots.add(v.findViewById(R.id.dot_2));
        dots.add(v.findViewById(R.id.dot_3));
        dots.add(v.findViewById(R.id.dot_4));

//        title = (TextView) v.findViewById(R.id.title);
//        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
//                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.k);
                dots.get(oldPosition).setBackgroundResource(R.drawable.k2);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }



    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }


    public void onStart() {
        // TODO Auto-generated method stub
        
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                5,
                5,
                TimeUnit.SECONDS);
    }

    

    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }



    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
   
    public void onStop() {
        // TODO Auto-generated method stub
      
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

}

