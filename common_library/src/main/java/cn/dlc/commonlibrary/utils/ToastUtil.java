package cn.dlc.commonlibrary.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.ref.WeakReference;

/**
 * Created by lzy on 2017/4/18 0018.
 */

public class ToastUtil {

    private static WeakReference<Toast> mToastRef = null;

    /**
     * 自定义Toast
     */
    public static void showOne(Context context, String text) {
        Toast toast;
        if (mToastRef != null && (toast = mToastRef.get()) != null) {
            toast.setDuration(Toast.LENGTH_SHORT);
            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            tv.setText(text);
        } else {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            mToastRef = new WeakReference<>(toast);
        }
        toast.show();
    }

    /**
     * 自定义Toast
     */
    public static void showOne(Context context, int resid) {
        showOne(context, context.getResources().getString(resid));
    }

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
