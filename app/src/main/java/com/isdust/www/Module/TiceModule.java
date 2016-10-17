package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.Jiaowu_tice_search;
import com.isdust.www.R;


/**
 * jiaowu_ScheduleModule的模型
 * Created by zor on 2016/9/27.
 */

public class TiceModule extends BaseModule {


    private TiceModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final TiceModule INSTANCE = new TiceModule(R.drawable.icon_sporttest,R.string.tice_name,R.string.tice_info,R.string.Jiaowu_catgory);
    }

    public static final TiceModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,Jiaowu_tice_search.class));
    }
}
