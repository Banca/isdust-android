package pw.isdust.isdust.update;

import android.content.Context;
import android.util.Log;

public class UpdateChecker {


    public static void checkForDialog(Context context,boolean showhint) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_DIALOG, true,showhint).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Context context,boolean showhint) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_NOTIFICATION, false,showhint).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }

    }


}
