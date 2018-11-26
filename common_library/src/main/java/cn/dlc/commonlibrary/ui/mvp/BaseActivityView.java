package cn.dlc.commonlibrary.ui.mvp;

import android.app.Activity;

/**
 * Created by John on 2017/8/28.
 */

public interface BaseActivityView {

    Activity getActivity();

    void showToast(int resid);

    void showToast(String text);

    void showOneToast(int resid);

    void showOneToast(String text);

    void showWaitingDialog(String text, boolean cancelable);

    void showWaitingDialog(int stringRes, boolean cancelable);

    void dismissWaitingDialog();

    boolean isActivityStarted();

    boolean isActivityResumed();
}
