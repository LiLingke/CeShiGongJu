package com.example.administrator.ceshigongju.base.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;

import com.example.administrator.ceshigongju.R;

/**
 * Created by John on 2018/2/2.
 */

public abstract class BaseExitActivity extends BaseActivity {

    /**
     * 强制登录
     */
    private boolean mForceExit;
    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForceExit = false;
    }

    /**
     * 强制退出
     */
    protected void forceExit() {
        if (mForceExit) {
            System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            tryExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void tryExit() {
        if ((SystemClock.elapsedRealtime() - mExitTime) > 2000) {
            showToast(getString(R.string.zainanyicituichu));
            mExitTime = SystemClock.elapsedRealtime();
        } else {
            mForceExit = true;
            onExit();
            finish();
        }
    }

    protected void onExit() {

    }
}
