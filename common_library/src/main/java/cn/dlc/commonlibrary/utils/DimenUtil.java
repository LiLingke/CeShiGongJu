package cn.dlc.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by John on 2017/11/15.
 */

public class DimenUtil {

    private static final Point sRealSize = new Point();

    private static int sStatusHeight = 20;

    public static void init(Context context) {

        Resources resources = context.getResources();

        updateRealSize(context);

        sStatusHeight = getStatusHeight(resources);
    }

    public static int getStatusHeight(Resources resources) {
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = resources.getDimensionPixelSize(resourceId);
        return statusBarHeight;
    }

    private static void updateRealSize(Context context) {
        WindowManager windowManager =
            (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB_MR2) {

            try {

                int width = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                int height = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
                sRealSize.set(width, height);
            } catch (Exception e) {
                display.getSize(sRealSize);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, sRealSize);
            } catch (Exception e) {
                display.getSize(sRealSize);
            }
        } else {
            display.getRealSize(sRealSize);
        }

        if (sRealSize.x > sRealSize.y) {
            int swap = sRealSize.x;
            sRealSize.set(sRealSize.y, swap);
        }
    }

    public static int getStatusHeight() {
        return sStatusHeight;
    }

    public static Point getRealSize() {
        return sRealSize;
    }

    public static int getRealWidth() {
        return sRealSize.x;
    }

    public static int getRealHeight() {
        return sRealSize.y;
    }

    public static float getXYScale() {
        return sRealSize.x / (float) sRealSize.y;
    }

    public static int getScaleWidth(int height) {
        return (int) (height * sRealSize.x / (float) sRealSize.y);
    }

    public static int getScaleHeight(int width) {
        return (int) (width * sRealSize.y / (float) sRealSize.x);
    }
}
