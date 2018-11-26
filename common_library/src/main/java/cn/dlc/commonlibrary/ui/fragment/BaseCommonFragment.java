package cn.dlc.commonlibrary.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dlc.commonlibrary.ui.activity.BaseCommonActivity;

public abstract class BaseCommonFragment extends RxFragment {

    protected Activity mActivity;
    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = null;
        if (getLayoutId() != 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            mUnBinder = ButterKnife.bind(this, view);
        }
        return view;
    }

    /**
     * 设置视图资源id
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    /**
     * 显示等待对话框
     *
     * @param text
     * @param cancelable
     */
    public void showWaitingDialog(String text, boolean cancelable) {
        ((BaseCommonActivity) getActivity()).showWaitingDialog(text, cancelable);
    }

    public void showWaitingDialog(int stringRes, boolean cancelable) {
        ((BaseCommonActivity) getActivity()).showWaitingDialog(stringRes, cancelable);
    }

    /**
     * 取消等待对话框
     */
    public void dismissWaitingDialog() {
        ((BaseCommonActivity) getActivity()).dismissWaitingDialog();
    }

    /**
     * 显示Toast
     *
     * @param resid
     */
    public void showToast(int resid) {
        ((BaseCommonActivity) getActivity()).showToast(resid);
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    public void showToast(String text) {
        ((BaseCommonActivity) getActivity()).showToast(text);
    }

    /**
     * 显示同一个Toast
     *
     * @param resid
     */
    public void showOneToast(int resid) {
        ((BaseCommonActivity) getActivity()).showOneToast(resid);
    }

    /**
     * 显示同一个Toast
     *
     * @param text
     */
    public void showOneToast(String text) {
        ((BaseCommonActivity) getActivity()).showOneToast(text);
    }
}
