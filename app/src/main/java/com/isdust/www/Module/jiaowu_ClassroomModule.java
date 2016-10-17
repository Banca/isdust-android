package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.Jiaowu_EmptyRoom;
import com.isdust.www.R;

/**
 * jiaowu_ScheduleModule的模型
 * Created by zor on 2016/9/27.
 */

public class jiaowu_ClassroomModule extends BaseModule {


    public jiaowu_ClassroomModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final jiaowu_ClassroomModule INSTANCE = new jiaowu_ClassroomModule(R.drawable.icon_classroom,R.string.classroom_name,R.string.classroom_info,R.string.Jiaowu_catgory);
    }

    public static final jiaowu_ClassroomModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,Jiaowu_EmptyRoom.class));
    }
}
