package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.R;
import com.isdust.www.jiaowu_Schedule_main;

/**
 * jiaowu_ScheduleModule的模型
 * Created by zor on 2016/9/27.
 */

public class jiaowu_ScheduleModule extends BaseModule {


    public jiaowu_ScheduleModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final jiaowu_ScheduleModule INSTANCE = new jiaowu_ScheduleModule(R.drawable.menu_schedule,R.string.schedule_name,R.string.schedule_info,R.string.Jiaowu_catgory);
    }

    public static final jiaowu_ScheduleModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,jiaowu_Schedule_main.class));
    }
}
