package cn.dlc.commonlibrary.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;
import cn.dlc.commonlibrary.R;

/**
 * Created by John on 2017/6/13.
 */

public class WaitingDialog extends Dialog {

    private final TextView mTvMessage;

    public WaitingDialog(@NonNull Context context) {
        super(context, R.style.WaitingDialog);

        setContentView(R.layout.dialog_waiting);

        //int size = (int) (getScreenWidth(context) * 0.33);
        //getWindow().setLayout(size, size);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
    }

    public static WaitingDialog newDialog(Context context) {
        return new WaitingDialog(context);
    }

    /**
     * 设置对话框文本
     *
     * @param resId
     * @return
     */
    public WaitingDialog setMessage(@StringRes int resId) {
        mTvMessage.setText(resId);
        return this;
    }

    /**
     * 设置对话框文本
     *
     * @param message
     * @return
     */
    public WaitingDialog setMessage(CharSequence message) {
        mTvMessage.setText(message);
        return this;
    }

    /**
     * 设置对话框占屏幕宽度的比例
     *
     * @param rate
     * @return
     */
    public WaitingDialog setSizeRate(float rate) {

        if (rate < 0.3f || rate > 0.5f) {
            rate = 0.33f;
        }
        int size = (int) (getScreenWidth(getContext()) * rate);
        getWindow().setLayout(size, size);
        return this;
    }

    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
