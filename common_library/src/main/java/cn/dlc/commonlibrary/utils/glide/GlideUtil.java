package cn.dlc.commonlibrary.utils.glide;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import cn.dlc.commonlibrary.utils.glide.transform.BlurTransform;
import cn.dlc.commonlibrary.utils.glide.transform.CircleTransform;
import cn.dlc.commonlibrary.utils.glide.transform.RoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.io.File;

/**
 * 作者：create by zzy on 2017/3/23 08:46
 * 邮箱：kevinchung0769@gmail.com
 * Glide工具类
 */

/**
 * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
 * load assets资源：load("file:///android_asset/f003.gif")
 * load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
 * load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
 * load ContentProvider资源：load("content://media/external/images/media/139469")
 * load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
 * load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
 */
public class GlideUtil {
    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImg(Context context, String url, ImageView imageView) {
        getRequestManager(context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView);
    }

    /**
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadImg(Context context, File file, ImageView imageView) {
        getRequestManager(context).load(file)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircleImg(Context context, String url, ImageView imageView) {
        getRequestManager(context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(new CircleTransform(context))
            .into(imageView);
    }

    /**
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadCircleImg(Context context, File file, ImageView imageView) {
        getRequestManager(context).load(file)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(new CircleTransform(context))
            .into(imageView);
    }

    /**
     * 圆角图形
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRoundImg(Context context, String url, ImageView imageView) {
        getRequestManager(context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(new RoundTransform(context))
            .into(imageView);
    }

    /**
     * 高斯模糊
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadBlur(Context context, String url, ImageView imageView) {
        getRequestManager(context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(new BlurTransform(context))
            .into(imageView);
    }

    /**
     * 这样可以是Glide充分利用生命周期，有效地进行资源回收
     *
     * @param object
     * @return
     */
    public static RequestManager getRequestManager(Object object) {

        if (object instanceof FragmentActivity) {
            return Glide.with((FragmentActivity) object);
        } else if (object instanceof Fragment) {
            return Glide.with((Fragment) object);
        } else if (object instanceof android.support.v4.app.Fragment) {
            return Glide.with((android.support.v4.app.Fragment) object);
        } else if (object instanceof Activity) {
            return Glide.with((Activity) object);
        } else if (object instanceof Context) {
            return Glide.with((Context) object);
        } else {
            return null;
        }
    }
}
