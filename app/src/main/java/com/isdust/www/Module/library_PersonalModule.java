package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.Library_personal_main;
import com.isdust.www.R;

/**
 * jiaowu_ScheduleModule的模型
 * Created by zor on 2016/9/27.
 */

public class library_PersonalModule extends BaseModule {


    public library_PersonalModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final library_PersonalModule INSTANCE = new library_PersonalModule(R.drawable.menu_person,R.string.library_personal_name,R.string.library_personal_info,R.string.Jiaowu_catgory);
    }

    public static final library_PersonalModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,Library_personal_main.class));
    }
}
