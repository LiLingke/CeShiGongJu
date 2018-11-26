package com.example.administrator.ceshigongju.listener;

import com.clj.fastble.data.BleDevice;

/**
 * Created by liling on 2018/2/1 0001.
 */

public interface BleOperatingResultListener {
    public void onScanDevice(BleDevice device);
    public void onNotifySuccess();
    public void onResetSuccess();
    public void onOpenDoorSuccess();
    public void onSetNumSuccess();
    public void onResetPassWord(String random);

    public void onNotifyFailure();
    public void onSendDatasFailure();
    public void onGetLockNum(String num);
    public void onConnectSuccess();
}
