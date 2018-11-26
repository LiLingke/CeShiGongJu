package com.example.administrator.ceshigongju.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.adapter.BleDevicesAdapter;
import com.example.administrator.ceshigongju.base.activity.BaseActivity;
import com.example.administrator.ceshigongju.listener.BleOperatingResultListener;
import com.example.administrator.ceshigongju.util.BleHelper;
import com.licheedev.myutils.LogPlus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import io.reactivex.functions.Consumer;

/**
 * @author James
 * @date 2018/9/30
 * @describe:
 */

public class SearchActivity extends BaseActivity implements BleOperatingResultListener {
    private static final int OPEN_DOOR = 1001;
    private final static int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private static final int LOCATION_PERMISSION_REQUESTCODE = 2;

    @BindView(R.id.titlebar)
    TitleBar mTitlebar;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_stop)
    Button mBtnStop;
    @BindView(R.id.img_renovate)
    ImageView mImgRenovate;
    @BindView(R.id.rv)
    RecyclerView mRv;


    private BleDevicesAdapter mAdapter;
    private Animation operatingAnim;

    @Override
    protected int getLayoutID() {
        return R.layout.activtiy_searcht;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitlebar.leftExit(this);
        initRecycle();

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_revolve_img);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        BleHelper.getInstance(this).setAddressCode("0001");
//        BleHelper.getInstance(this)
//                .setBleStatusListener(this);

        new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            initBle();
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        BleHelper.getInstance(this)
                .setBleStatusListener(this);
    }

    private void initRecycle() {
        mAdapter = new BleDevicesAdapter();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);

        mAdapter.setOnListener(new BleDevicesAdapter.OnDeviceListener() {

            @Override
            public void onClick(int position) {
                BleDevice device = mAdapter.getItem(position);
                startActivity(CheckoutActivity.newIntent(SearchActivity.this, device));
            }

            @Override
            public void onOpen(int position) {

            }


            @Override
            public void onDiscon(int position) {

            }
        });

    }

    @OnClick({R.id.btn_start, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                mAdapter.setNewData(null);
                mAdapter.setIndex(-1);
                mBtnStart.setEnabled(false);
                mBtnStop.setVisibility(View.VISIBLE);
                mImgRenovate.startAnimation(operatingAnim);
                BleManager.getInstance().disconnectAllDevice();
                BleHelper.getInstance(this).searchDevice02();
                break;
            case R.id.btn_stop:
                mBtnStart.setEnabled(true);
                mBtnStop.setVisibility(View.INVISIBLE);
                mImgRenovate.clearAnimation();
                BleManager.getInstance().cancelScan();
                break;
        }
    }

    @Override
    public void onScanDevice(BleDevice device) {
        mAdapter.addData(device);
    }

    @Override
    public void onNotifySuccess() {
    }

    @Override
    public void onResetSuccess() {

    }

    @Override
    public void onOpenDoorSuccess() {
    }

    @Override
    public void onSetNumSuccess() {

    }

    @Override
    public void onResetPassWord(String random) {

    }

    @Override
    public void onNotifyFailure() {
        dismissWaitingDialog();
    }

    @Override
    public void onSendDatasFailure() {
        dismissWaitingDialog();
    }

    @Override
    public void onGetLockNum(String num) {

    }

    @Override
    public void onConnectSuccess() {

    }

    /**
     * 初始化蓝牙
     */
    private void initBle() {
        if (BleManager.getInstance()
                .isSupportBle()) {
            if (!BleManager.getInstance()
                    .isBlueEnable()) {
                //打来蓝牙
                Intent requestBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                this.startActivityForResult(requestBluetoothOn, REQUEST_CODE_BLUETOOTH_ON);
            } else {

            }
        } else {
            Log.e("ble", "不支持");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUESTCODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    LogPlus.e("Location  showTeachDialog");
                    showTeachDialog();
                }
                break;
        }
    }

    /**
     * 引导用户打开相关权限
     */
    private void showTeachDialog() {
        new AlertDialog.Builder(SearchActivity.this).setCancelable(false)
                .setMessage("需要开启权限才能使用相关功能")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户到设置中去进行设置
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
}

