package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.Jiaowu_chengjichaxun_main;
import com.isdust.www.R;

/**
 * jiaowu_ScheduleModule的模型
 * Created by zor on 2016/9/27.
 */

public class jiaowu_MarkModule extends BaseModule {


    public jiaowu_MarkModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final jiaowu_MarkModule INSTANCE = new jiaowu_MarkModule(R.drawable.menu_mark,R.string.mark_name,R.string.mark_info,R.string.Jiaowu_catgory);
    }

    public static final jiaowu_MarkModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,Jiaowu_chengjichaxun_main.class));
    }
}
