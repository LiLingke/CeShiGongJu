package com.example.administrator.ceshigongju.base.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.administrator.ceshigongju.R;

import java.text.SimpleDateFormat;
import java.util.Set;

import cn.dlc.commonlibrary.ui.activity.BaseCommonActivity;

/**
 * 备用的BaseActivity，用来加友盟统计之类的
 * Created by John on 2017/10/9.
 */

public abstract class BaseActivity extends BaseCommonActivity {

    private Set<Activity> allActivities;

    protected Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mHandler = new Handler();
        super.onCreate(savedInstanceState);
//        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void removeAllActivity() {

        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        if (allActivities != null) {
            allActivities.clear();
        }
    }


    @Override
    protected void beforeSetContentView() {
        //竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setTranslucentStatus(isTranslucentStatus());
    }

    /**
     * 5.0系统以上设置状态栏沉浸和透明
     *
     * @param translucentStatus
     */
    protected void setTranslucentStatus(boolean translucentStatus) {
        if (translucentStatus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView()
                    .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    /**
     * 6.0以上设置白的黑字状态栏
     */
    protected void setDarkTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                .setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.line_color));
        }
    }

    /**
     * 是否让状态栏透明
     *
     * @return
     */
    protected boolean isTranslucentStatus() {
        return true;
    }

    protected String formartTime(String ctime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(Long.parseLong(ctime) * 1000L);
        return time;
    }
    
}
