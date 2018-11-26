package com.example.administrator.ceshigongju.main;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.base.activity.BaseActivity;
import com.example.administrator.ceshigongju.bean.BaseBean;
import com.example.administrator.ceshigongju.bean.LockBean;
import com.example.administrator.ceshigongju.listener.BleOperatingResultListener;
import com.example.administrator.ceshigongju.net.NetApi;
import com.example.administrator.ceshigongju.util.BinHexOctUtils;
import com.example.administrator.ceshigongju.util.BleHelper;
import com.example.administrator.ceshigongju.util.CRC16Utils;
import com.example.administrator.ceshigongju.weight.ShowDetailDialog;
import com.example.administrator.ceshigongju.weight.UpLoadLockDialog;
import com.licheedev.myutils.LogPlus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import io.reactivex.functions.Consumer;

import static com.example.administrator.ceshigongju.Constant.ApiConstant.head_set_address;

/**
 * @author James
 * @date 2018/6/25
 * @describe:
 */

public class CheckoutActivity extends BaseActivity implements BleOperatingResultListener {

    private static final int OPEN_DOOR = 1001;
    private static final int SET_NUM = 1002;
    private static final int GET_NUM = 1005;
    private final static int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private static final int LOCATION_PERMISSION_REQUESTCODE = 2;
    @BindView(R.id.titlebar)
    TitleBar mTitlebar;
    @BindView(R.id.tv_lock_name)
    TextView mTvLockName;
    @BindView(R.id.tv_lock_id)
    TextView mTvLockId;
    @BindView(R.id.tv_lock_distance)
    TextView mTvLockDistance;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.btn_open)
    Button mBtnOpen;
    @BindView(R.id.btn_upload)
    Button mBtnUpload;
    @BindView(R.id.tv_lock_order)
    TextView mTvLockOrder;
    @BindView(R.id.tv_check)
    TextView mTvCheck;
    @BindView(R.id.btn_check)
    Button mBtnCheck;

    private BleDevice mBleDevice;
    private Animation operatingAnim;
    private UpLoadLockDialog mDialog;
    private ShowDetailDialog mDetailDialog;
    private int mAction = OPEN_DOOR;
    private int lockNum = 1;
    private String lockNumble;

    @Override
    protected int getLayoutID() {
        return R.layout.activtiy_checkout;
    }

    public static Intent newIntent(Activity fromActivity, BleDevice device) {
        Intent intent = new Intent(fromActivity, CheckoutActivity.class);
        intent.putExtra("device", device);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitlebar.leftExit(this);
        mBleDevice = getIntent().getParcelableExtra("device");
        mDialog = new UpLoadLockDialog(this);
        mDetailDialog = new ShowDetailDialog(this);
        mDialog.setLockId(mBleDevice.getMac());
        initView();

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_revolve_img);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        BleHelper.getInstance(this).setAddressCode("0001");
        BleHelper.getInstance(this)
                .setBleStatusListener(this);
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

    private void initView() {
        if (mBleDevice != null) {
            mTvLockName.setText("蓝牙名称：" + mBleDevice.getName());
            mTvLockId.setText("蓝牙地址：" + mBleDevice.getMac());
            mTvLockDistance.setText("信号强度：" + mBleDevice.getRssi() + "");
        }
    }

    @OnClick({R.id.btn_open, R.id.btn_upload, R.id.tv_check, R.id.btn_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
//                openDoor();
                openNewDoor();
                break;
            case R.id.btn_upload:
                getLockSeriaNumber(mBleDevice.getMac());
                break;
            case R.id.tv_check:
                getLockNum();
                break;
            case R.id.btn_check:
                checkLock(mBleDevice.getMac());
                break;

        }
    }

    private void openNewDoor() {
        showWaitingDialog("连接中...", true);
        BleHelper.getInstance(CheckoutActivity.this).connect(mBleDevice);
    }

    private void openDoor() {
        String strNum = mEtNum.getText().toString();
        strNum = (strNum == null || TextUtils.isEmpty(strNum)) ? "0001" : strNum;
        if (Integer.parseInt(strNum) <= 0 || Integer.parseInt(strNum) > 2048) {
            showToast("机号必须在1至2048之间");
            return;
        }
        BleHelper.getInstance(this).setAddressCode(BinHexOctUtils.decimal2fitHex(Integer.parseInt(strNum), 2));
        if (mBleDevice != null) {
            mAction = OPEN_DOOR;
            BleDevice device = BleHelper.getInstance(this).getBleDevice();
            if (device != null && device.getMac().equals(mBleDevice.getMac())) {
                showWaitingDialog("开门中", true);
                BleHelper.getInstance(CheckoutActivity.this).doAction(OPEN_DOOR);
            } else {
                showWaitingDialog("连接中...", true);
                BleHelper.getInstance(CheckoutActivity.this).connect(mBleDevice);
            }
        } else {
            showToast("未获取到设备，请退回到上一个界面");
        }
    }

    private void setLockNum() {
        if (mBleDevice != null) {
            mAction = SET_NUM;
            BleDevice device = BleHelper.getInstance(this).getBleDevice();
            if (device != null && device.getMac().equals(mBleDevice.getMac())) {
                showWaitingDialog("设置机号中", true);
                String str = head_set_address + BinHexOctUtils.decimal2fitHex(lockNum, 2);
                String mSetNumStr = str + CRC16Utils.getCRC16(str);
                BleHelper.getInstance(this)
                        .setDeviceNum(mSetNumStr);
            } else {
                showWaitingDialog("连接中...", true);
                BleHelper.getInstance(CheckoutActivity.this).connect(mBleDevice);
            }
        } else {
            showToast("未获取到设备，请退回到上一个界面");
        }
    }

    private void getLockNum() {
        if (mBleDevice != null) {
            mAction = GET_NUM;
            BleDevice device = BleHelper.getInstance(this).getBleDevice();
            if (device != null && device.getMac().equals(mBleDevice.getMac())) {
                BleHelper.getInstance(CheckoutActivity.this).doAction(GET_NUM);
            } else {
                showWaitingDialog("连接中...", true);
                BleHelper.getInstance(CheckoutActivity.this).connect(mBleDevice);
            }
        } else {
            showToast("未获取到设备，请退回到上一个界面");
        }
    }

    private void upLoadLock() {
        NetApi.get().upLoadLock(mBleDevice.getMac(), mBleDevice.getName(), lockNumble, new Bean01Callback<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                showToast(baseBean.msg);
                mTvLockOrder.setText("序号：" + baseBean.data);
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                showToast(message);
            }
        });
    }

    @Override
    public void onScanDevice(BleDevice device) {

    }

    @Override
    public void onNotifySuccess() {
        LogPlus.e("连接成功");

//        if (mAction == OPEN_DOOR) {
//            showWaitingDialog("开门中", true);
//            BleHelper.getInstance(this)
//                    .doAction(OPEN_DOOR);
//        } else if (mAction == SET_NUM) {
//            String str = head_set_address + BinHexOctUtils.decimal2fitHex(lockNum, 2);
//            String mSetNumStr = str + CRC16Utils.getCRC16(str);
//            BleHelper.getInstance(this)
//                    .setDeviceNum(mSetNumStr);
//        } else if (mAction == GET_NUM) {
//            showWaitingDialog("获取机号中", true);
//            BleHelper.getInstance(this)
//                    .doAction(GET_NUM);
//        }

    }

    @Override
    public void onResetSuccess() {

    }

    @Override
    public void onOpenDoorSuccess() {
        dismissWaitingDialog();
    }

    @Override
    public void onSetNumSuccess() {
        dismissWaitingDialog();
        mDialog.dismiss();
        showToast("机号设置成功");
        upLoadLock();
        mEtNum.setText(String.valueOf(lockNum));
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
        dismissWaitingDialog();
        LogPlus.e("lockNum == " + num);
        int lockNum = Integer.parseInt(num, 16);
        mEtNum.setText(lockNum + "");
    }

    @Override
    public void onConnectSuccess() {
        dismissWaitingDialog();
        String str = mBleDevice.getMac();
        String lockId = str.replace(":", "");
        String value = BinHexOctUtils.exclusiveOrSelf(lockId);
//        String value = "40";
        String strValue = "400001020304050682120B150000120B1E000000";
        LogPlus.e("lockId == " + lockId );
        LogPlus.e("value == " + value);
        BleHelper.getInstance(this).reallRead();


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
        new AlertDialog.Builder(CheckoutActivity.this).setCancelable(false)
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


    private void getLockSeriaNumber(String lockId) {
        showWaitingDialog("加载中...", true);
        NetApi.get().getLockSeriaNumber(lockId, 1, new Bean01Callback<LockBean>() {
            @Override
            public void onSuccess(LockBean lockBean) {
                dismissWaitingDialog();
                LogPlus.i("当前线程 == " + Thread.currentThread().getName());
                if (lockBean.code == 1) {
                    mDialog.show(new UpLoadLockDialog.OnSureListener() {
                        @Override
                        public void onSure(String value, int num) {
                            lockNum = num;
                            lockNumble = value;
                            LogPlus.e("lockNum == " + lockNum + "  lockNumble == " + lockNumble);
                            setLockNum();
                        }
                    }, lockBean.data.lock_no);
                }
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                dismissWaitingDialog();
                showToast(message);
            }
        });
    }

    private void checkLock(String lockId) {
        showWaitingDialog("加载中...", true);
        NetApi.get().getLockSeriaNumber(lockId, 2, new Bean01Callback<LockBean>() {
            @Override
            public void onSuccess(LockBean lockBean) {
                dismissWaitingDialog();
                if (lockBean.code == 1) {
                    mDetailDialog.show();
                    mDetailDialog.initView(lockBean);
                }
            }

            @Override
            public void onFailure(String message, Throwable tr) {
                dismissWaitingDialog();
                showToast(message);
            }
        });
    }
}
