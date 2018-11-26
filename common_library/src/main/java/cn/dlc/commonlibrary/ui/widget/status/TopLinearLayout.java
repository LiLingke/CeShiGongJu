package cn.dlc.commonlibrary.ui.widget.status;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import cn.dlc.commonlibrary.R;

/**
 * Created by John on 2017/10/9.
 */

public class TopLinearLayout extends LinearLayout {

    private boolean mBehindStatus;
    private int mStatusHeight;

    public TopLinearLayout(@NonNull Context context) {
        this(context, null);
    }

    public TopLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);

        mBehindStatus = true;

        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Toolbar);

            mBehindStatus = ta.getBoolean(R.styleable.TopLinearLayout_behind_status_bar, true);
            ta.recycle();
        }

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

        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (mBehindStatus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            height = height + mStatusHeight;
        }
        int newHeightSpec =
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec));

        super.onMeasure(widthMeasureSpec, newHeightSpec);
    }
}
