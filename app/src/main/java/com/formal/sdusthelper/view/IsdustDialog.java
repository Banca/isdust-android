package com.formal.sdusthelper.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.formal.sdusthelper.R;

/**
 * Created by Administrator on 2015/10/26.
 */
public class IsdustDialog extends Dialog {
    public final static int RUNING_DIALOG = 0;
    public final static int PROGRESSBAR_DIALOG = 1;
    public final static int OK_DIALOG = 2;
    public final static int YES_NO_DIALOG = 3;
    public final static int OK_CANCEL_DIALOG = 4;

    private static int default_width = 160; // 默认宽度
    private static int default_height = 120;// 默认高度

    private int layout_id;  //布局文件ID

    private TextView textTitle,textMsg;
    private ProgressBar progressBar;

    public IsdustDialog(Context context) {
        super(context);
    }

    public IsdustDialog(Context context, int layout, int style) {
        this(context, default_width, default_height, layout, style);

    }

    public IsdustDialog(Context context, int width, int height, int layout, int style) {
        super(context, style);
        // 设置内容
        selectView(layout);
        layout_id = layout;

    }

    private void selectView(int id) {
        switch(id) {
            case RUNING_DIALOG:
                setContentView(R.layout.dialog_runing);
                textMsg = (TextView) findViewById(R.id.textView_dialog_message);
                break;
//            case PROGRESSBAR_DIALOG:
//                setContentView(R.layout.dialog_progressbar);
//                break;
//            case OK_DIALOG:
//                setContentView(layout);
//                break;
//            case YES_NO_DIALOG:
//                setContentView(layout);
//                break;
//            case OK_CANCEL_DIALOG:
//                setContentView(layout);
//                break;

        }
    }

//    public boolean setProgress(int n) {
//        if (layout_id != PROGRESSBAR_DIALOG)    //非进度条类型禁止设置
//            return false;
//        ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar_dialog);
//        pb.setMax(100);
//        pb.setProgress(n);
//        return true;
//    }

    public boolean setMessage(String msg) {
        textMsg.setText(msg);
        return true;
    }
    /**
     * 获取显示密度
     *
     * @param context
     * @return
     */
//    public float getDensity(Context context) {
//        Resources res = context.getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        return dm.density;
//    }
}
