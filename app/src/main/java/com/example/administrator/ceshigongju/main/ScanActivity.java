package com.example.administrator.ceshigongju.main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.base.activity.BaseActivity;
import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import cn.dlc.commonlibrary.ui.widget.TitleBar;


public class ScanActivity extends BaseActivity implements QRCodeView.Delegate, View.OnClickListener {


    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.zxingView)
    ZXingView mZxingView;
    @BindView(R.id.tv_switchFlash)
    TextView mTvSwitchFlash;
    @BindView(R.id.activity_scan)
    RelativeLayout activityScan;
    private boolean isOpen;//闪光灯是否打开
    private final static int ScanCODE = 2;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_scan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlebar.leftExit(this);
        mZxingView.setDelegate(this);
        initBus();
    }


    public void initBus() {
        mTvSwitchFlash.setOnClickListener(this);

    }


    /**
     * 扫描成功
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        scanSuccess(result);

        Log.i("MainA", "扫描的数据返回==" + result);
        MediaPlayer player = MediaPlayer.create(this, R.raw.beep);//播放音效
        player.setVolume(0.5f, 0.5f);//左声道,右声道
        player.start();
    }

    private void scanSuccess(String result) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);//震动
        vibrator.vibrate(200);

        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(ScanCODE, intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mZxingView.startCamera();//打开摄像头
        mZxingView.showScanRect();//显示扫描框
        mZxingView.startSpot();//1.5s后开始识别
    }

    @Override
    protected void onStop() {
        mZxingView.stopCamera();
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        mZxingView.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_switchFlash:// 打开/关闭闪光灯
                isOpen = !isOpen;
                switchLight(isOpen);
                break;
            default:
                break;
        }
    }

    /**
     * 切换灯
     *
     * @param b
     */
    private void switchLight(boolean b) {
        if (b) {
            mZxingView.openFlashlight();
            mTvSwitchFlash.setText("关闭闪光灯");
        } else {
            mZxingView.closeFlashlight();
            mZxingView.closeFlashlight();
            mTvSwitchFlash.setText("打开闪光灯");
        }
    }

}
