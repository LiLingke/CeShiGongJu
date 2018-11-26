package cn.dlc.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import java.util.Locale;

/**
 * Created by lizhiyong on 2016/3/21.
 */
public class ResUtil {

    private static Resources sResources;

    public static void init(Context context) {
        sResources = context.getResources();
    }

    public static String getString(int resId) {
        return sResources.getString(resId);
    }

    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return sResources.getString(resId, formatArgs);
    }

    public static String getString(Context context, int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    public static Spanned fromHtml(int resId, Object... formatArgs) {
        String text = sResources.getString(resId, formatArgs);
        return Html.fromHtml(text);
    }

    public static Spanned fromHtml(Context context, int resId, Object... formatArgs) {
        String text = context.getResources().getString(resId, formatArgs);
        return Html.fromHtml(text);
    }

    public static void setHtml(TextView textView, int resId, Object... formatArgs) {
        String string = textView.getResources().getString(resId, formatArgs);
        textView.setText(Html.fromHtml(string));
    }

    public static int getColor(Context context, int resId) {
        return ContextCompat.getColor(context, resId);
    }

    public static Resources getResources() {
        return sResources;
    }

    public static Locale getLocale() {
        return sResources.getConfiguration().locale;
    }

    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }
}
