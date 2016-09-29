package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.GoNetActivity;
import com.isdust.www.R;

/**
 * jiaowu_ScheduleModule的模型
 * Created by zor on 2016/9/27.
 */

public class WlanModule extends BaseModule {


    public WlanModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final WlanModule INSTANCE = new WlanModule(R.drawable.gonet_tabsbar_background,R.string.wifi_name,R.string.wifi_info,R.string.net_catgory);
    }

    public static final WlanModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,GoNetActivity.class));
    }
}
