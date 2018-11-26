package cn.dlc.commonlibrary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dlc.commonlibrary.ui.dialog.WaitingDialog;
import cn.dlc.commonlibrary.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by GaoYuan on 2017/5/17.
 */

public abstract class BaseCommonActivity extends RxAppCompatActivity {

    private Unbinder mUnbinder;
    private WaitingDialog mWaitingDialog;

    /**
     * Activity调用过{@link #onStart()}
     */
    protected boolean mActivityStarted;
    /**
     * Activity调用过{@link #onResume()}
     */
    protected boolean mActivityResumed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        if (getLayoutID() != 0) {// 子类设置布局id使用,
            setContentView(getLayoutID());
        }

        if (useButterKnife()) {
            //所有的ButterKnife绑定让父类完成
            mUnbinder = ButterKnife.bind(this);
        }
        afterSetContentView(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityStarted = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityResumed = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityStarted = false;
    }

    @Override
    protected void onDestroy() {
        if (useButterKnife()) {
            mUnbinder.unbind();//在页面销毁时解绑
        }

        dismissWaitingDialog();

        super.onDestroy();
    }

    /**
     * 是否使用ButterKnife
     *
     * @return
     */
    protected boolean useButterKnife() {
        return true;
    }

    /**
     * 获取布局xml的id.子类实现
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutID();

    /**
     * 在设置布局前执行
     */
    protected void beforeSetContentView() {

    }

    /**
     * 在设置布局后执行,一般不用，不要在这里初始化，请直接重写{@link #onCreate(Bundle)}
     *
     * @param savedInstanceState
     */
    protected void afterSetContentView(Bundle savedInstanceState) {
        // 一般不用，不要在这里初始化，请直接重写onCreate()
    }

    protected Activity getActivity() {
        return this;
    }

    /**
     * 跳转到其他的activity,限定参数必须为Activity
     */
    public void startActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    public boolean isActivityStarted() {
        return mActivityStarted;
    }

    public boolean isActivityResumed() {
        return mActivityResumed;
    }

    /**
     * 显示Toast
     *
     * @param resid
     */
    public void showToast(int resid) {
        ToastUtil.show(this, resid);
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    public void showToast(String text) {
        ToastUtil.show(this, text);
    }

    /**
     * 显示同一个Toast
     *
     * @param resid
     */
    public void showOneToast(int resid) {
        ToastUtil.showOne(this, resid);
    }

    /**
     * 显示同一个Toast
     *
     * @param text
     */
    public void showOneToast(String text) {
        ToastUtil.showOne(this, text);
    }

    /**
     * 显示等待对话框
     *
     * @param text
     * @param cancelable
     */
    public void showWaitingDialog(String text, boolean cancelable) {
        if (mWaitingDialog == null) {
            mWaitingDialog = WaitingDialog.newDialog(this).setMessage(text);
        }
        if (mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
        }

        mWaitingDialog.setMessage(text);

        mWaitingDialog.setCancelable(cancelable);
        mWaitingDialog.show();
    }

    public void showWaitingDialog(int stringRes, boolean cancelable) {
        showWaitingDialog(getString(stringRes), cancelable);
    }

    /**
     * 取消等待对话框
     */
    public void dismissWaitingDialog() {
        if (mWaitingDialog != null && mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
        }
    }
}
