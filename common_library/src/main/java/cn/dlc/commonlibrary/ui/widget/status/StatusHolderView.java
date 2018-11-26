package cn.dlc.commonlibrary.ui.widget.status;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by John on 2017/12/12.
 */

public class StatusHolderView extends View {

    private int mStatusHeight;

    public StatusHolderView(Context context) {
        super(context);
        init(context);
    }

    public StatusHolderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatusHolderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatusHolderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        try {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            mStatusHeight = resources.getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            //e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int newHeightSpec =
                MeasureSpec.makeMeasureSpec(mStatusHeight, MeasureSpec.getMode(heightMeasureSpec));
            super.onMeasure(widthMeasureSpec, newHeightSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
