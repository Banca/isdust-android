package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.KuaiTongActivity;
import com.isdust.www.R;

/**
 * 快通的模型
 * Created by zor on 2016/9/27.
 */

public class KuaiTongModule extends BaseModule {


    public KuaiTongModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final KuaiTongModule INSTANCE = new KuaiTongModule(R.drawable.main_kuaitong,R.string.kuaitong_name,R.string.kuaitong_info,R.string.net_catgory);
    }

    public static final KuaiTongModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context activity) {
        activity.startActivity(new Intent(activity,KuaiTongActivity.class));
    }
}
