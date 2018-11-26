package cn.dlc.commonlibrary.utils.glide.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import cn.dlc.commonlibrary.utils.FastBlur;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class BlurTransform extends BitmapTransformation {

    private static int DEFAULT_RADIUS = 3;
    private static int DEFAULT_DOWN_SAMPLING = 3;

    private int mRadius = DEFAULT_RADIUS;
    private int mDownSampling = DEFAULT_DOWN_SAMPLING;

    public BlurTransform(Context context) {
        super(context);
    }

    /**
     * 
     * @param context
     * @param radius 模糊半径，越大约卡
     * @param downSample 原图缩小倍数，缩小后才进行模糊计算，越小约卡
     */
    public BlurTransform(Context context,int radius,int downSample) {
        super(context);
        mRadius = radius;
        mDownSampling = downSample;
    }
    
    
    

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return blur(pool, toTransform);
    }

    private Bitmap blur(BitmapPool pool, Bitmap toTransform) {
        if (toTransform == null) {
            return null;
        }

        int sampling = mDownSampling;

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;

        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);
        bitmap = FastBlur.doBlur(bitmap, mRadius, true);
        return bitmap;
    }

    @Override
    public String getId() {
        return getClass().getName() + mRadius;
    }
}
