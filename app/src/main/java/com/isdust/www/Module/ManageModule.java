package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.ModuleManage;
import com.isdust.www.R;

/**
 * 管理器的模型
 * Created by zor on 2016/9/30
 */

public class ManageModule extends BaseModule {


    private ManageModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final ManageModule INSTANCE = new ManageModule(R.drawable.card,R.string.manage_module,R.string.schoolcard_info,R.string.schoolcard_catgory);
    }

    public static final ManageModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context mContext) {
        mContext.startActivity(new Intent(mContext,ModuleManage.class));
    }
}
