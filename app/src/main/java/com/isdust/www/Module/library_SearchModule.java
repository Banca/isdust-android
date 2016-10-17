package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.Library_guancang_main;
import com.isdust.www.R;

/**
 * jiaowu_ScheduleModule的模型
 * Created by zor on 2016/9/27.
 */

public class library_SearchModule extends BaseModule {


    private library_SearchModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final library_SearchModule INSTANCE = new library_SearchModule(R.drawable.icon_library,R.string.library_search_name,R.string.library_search_info,R.string.library_catagory);
    }

    public static final library_SearchModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,Library_guancang_main.class));
    }
}
