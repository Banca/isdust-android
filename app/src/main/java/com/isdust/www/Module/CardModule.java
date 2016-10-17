package com.isdust.www.Module;

import android.content.Context;
import android.content.Intent;

import com.isdust.www.CardActivity;
import com.isdust.www.R;

/**
 * Card的模型
 * Created by zor on 2016/9/27.
 */

public class CardModule extends BaseModule {


    private CardModule(int image_id, int name, int desc, int catagory) {
        super(image_id, name, desc, catagory);
    }

    private static class Holder {
        private static final CardModule INSTANCE = new CardModule(R.drawable.icon_card,R.string.schoolcard_name,R.string.schoolcard_info,R.string.schoolcard_catgory);
    }

    public static final CardModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void lunchActivity(Context mContext) {
        mContext.startActivity(new Intent(mContext,CardActivity.class));
    }
}
