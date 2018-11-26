package cn.dlc.commonlibrary.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dlc.commonlibrary.R;

/**
 * Created by lizhiyong on 2015/12/28.
 */
public class TitleBar extends FrameLayout {

    public final TextView titleText;
    public final ImageButton leftImage;
    public final ImageButton rightImage;
    public final TextView leftText;
    public final TextView rightText;
    private final View bottomLine;

    //private int mHeight;

    public static final int TITLE_TEXT = R.id.titleText;
    public static final int LEFT_IMAGE = R.id.leftImage;
    public static final int RIGHT_IMAGE = R.id.rightImage;
    public static final int LEFT_TEXT = R.id.leftText;
    public static final int RIGHT_TEXT = R.id.rightText;

    private boolean mFitStatusBar;
    private int mStatusHeight;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs,
        @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        mFitStatusBar = typedArray.getBoolean(R.styleable.TitleBar_fitStatusBar, false);

        if (mFitStatusBar) {
            try {
                Resources resources = context.getResources();
                int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
                mStatusHeight = resources.getDimensionPixelSize(resourceId);
            } catch (Resources.NotFoundException e) {
                //e.printStackTrace();
            }
        }

        setupBehindImage(context, typedArray);

        View view;

        if (mFitStatusBar) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.view_title_bar_fit_status_bar, this, true);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.view_title_bar, this, true);
        }

        titleText = (TextView) view.findViewById(TITLE_TEXT);
        leftImage = (ImageButton) view.findViewById(LEFT_IMAGE);
        rightImage = (ImageButton) view.findViewById(RIGHT_IMAGE);
        leftText = (TextView) view.findViewById(LEFT_TEXT);
        rightText = (TextView) view.findViewById(RIGHT_TEXT);
        bottomLine = view.findViewById(R.id.bottomLine);

        setupTitleBar(context, typedArray);

        typedArray.recycle();
    }

    private void setupBehindImage(Context context, TypedArray typedArray) {

        int behindImage = typedArray.getResourceId(R.styleable.TitleBar_behindImage, 0);

        if (behindImage != 0) {
            ImageView imageView = new ImageView(context);
            addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(behindImage);
            
        }
        
    }

    private void setupTitleBar(Context context, TypedArray typedArray) {

        // 设置高度
        //mHeight = getResources().getDimensionPixelSize(R.dimen.title_bar_height);

        //int backgroundColor = ContextCompat.getColor(getContext(), R.color.title_bar_background);
        int lineColor = ContextCompat.getColor(context, R.color.title_bottom_line_color);
        int textColor = ContextCompat.getColor(context, R.color.title_text_color);
        int buttonBg = R.drawable.title_bar_button_bg;
        int textBg = R.drawable.title_bar_text_bg;

        String titleText = typedArray.getString(R.styleable.TitleBar_titleText);

        this.titleText.setText(titleText);

        // 下方分隔线
        lineColor = typedArray.getColor(R.styleable.TitleBar_lineColor, lineColor);
        bottomLine.setBackgroundColor(lineColor);

        boolean showLine = typedArray.getBoolean(R.styleable.TitleBar_showLine, false);

        bottomLine.setVisibility(showLine ? VISIBLE : GONE);

        // 左侧按钮
        int leftDrawableRes = typedArray.getResourceId(R.styleable.TitleBar_leftDrawable, 0);

        if (leftDrawableRes != 0) {
            leftImage.setImageResource(leftDrawableRes);
        }

        boolean showLeftImage =
            typedArray.getBoolean(R.styleable.TitleBar_showLeftImage, leftDrawableRes != 0);

        leftImage.setVisibility(showLeftImage ? VISIBLE : GONE);

        // 右侧按钮
        int rightDrawableRes = typedArray.getResourceId(R.styleable.TitleBar_rightDrawable, 0);

        if (rightDrawableRes != 0) {
            rightImage.setImageResource(rightDrawableRes);
        }

        boolean showRightImage =
            typedArray.getBoolean(R.styleable.TitleBar_showRightImage, rightDrawableRes != 0);

        rightImage.setVisibility(showRightImage ? VISIBLE : GONE);

        // 左侧文字
        String leftString = typedArray.getString(R.styleable.TitleBar_leftText);
        leftText.setText(leftString);

        boolean showLeftText = typedArray.getBoolean(R.styleable.TitleBar_showLeftText,
            !TextUtils.isEmpty(leftString));
        leftText.setVisibility(showLeftText ? VISIBLE : GONE);

        // 右侧文字
        String rightString = typedArray.getString(R.styleable.TitleBar_rightText);
        rightText.setText(rightString);
        boolean showRightText = typedArray.getBoolean(R.styleable.TitleBar_showRightText,
            !TextUtils.isEmpty(rightString));
        rightText.setVisibility(showRightText ? VISIBLE : GONE);

        //backgroundColor =
        //    typedArray.getColor(R.styleable.TitleBar_backgroundColor, backgroundColor);

        buttonBg =
            typedArray.getResourceId(R.styleable.TitleBar_imageBg, R.drawable.title_bar_button_bg);

        textBg =
            typedArray.getResourceId(R.styleable.TitleBar_textBg, R.drawable.title_bar_text_bg);

        textColor = typedArray.getColor(R.styleable.TitleBar_titleTextColor, textColor);

        //setBackgroundColor(backgroundColor);
        setImageBackground(buttonBg);
        setTextBackground(textBg);
        setTitleTextColor(textColor);
    }

    public void setImageBackground(int resId) {
        leftImage.setBackgroundResource(resId);
        rightImage.setBackgroundResource(resId);
    }

    public void setTextBackground(int resId) {
        leftText.setBackgroundResource(resId);
        rightText.setBackgroundResource(resId);
    }

    public void setTitleTextColor(int color) {
        titleText.setTextColor(color);
        leftText.setTextColor(color);
        rightText.setTextColor(color);
    }

    public void setTitle(@StringRes int resId) {
        titleText.setText(resId);
    }

    public void setTitle(CharSequence title) {
        titleText.setText(title);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        leftImage.setOnClickListener(l);
        rightImage.setOnClickListener(l);
        leftText.setOnClickListener(l);
        rightText.setOnClickListener(l);
    }

    public void setLeftOnClickListener(@Nullable OnClickListener l) {
        leftImage.setOnClickListener(l);
        leftText.setOnClickListener(l);
    }

    public void setRightOnClickListener(@Nullable OnClickListener l) {
        rightImage.setOnClickListener(l);
        rightText.setOnClickListener(l);
    }

    public void leftExit(final Activity activity) {
        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (mFitStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            height = height + mStatusHeight;
        }
        int newHeightSpec =
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec));

        super.onMeasure(widthMeasureSpec, newHeightSpec);
    }
}
