package com.example.administrator.ceshigongju.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.base.activity.BaseActivity;
import com.example.administrator.ceshigongju.bean.NoteEntity;
import com.example.administrator.ceshigongju.bean.NoteEntityDao;
import com.example.administrator.ceshigongju.listener.BleOperatingResultListener;
import com.example.administrator.ceshigongju.util.BinHexOctUtils;
import com.example.administrator.ceshigongju.util.BleHelper;
import com.example.administrator.ceshigongju.util.CRC16Utils;
import com.example.administrator.ceshigongju.util.CalendarUtils;
import com.example.administrator.ceshigongju.util.GreenDaoHelper;
import com.example.administrator.ceshigongju.util.SpFileUtils;
import com.licheedev.myutils.LogPlus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import cn.dlc.commonlibrary.utils.ToastUtil;
import cn.dlc.commonlibrary.utils.glide.transform.BlurTransform;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.administrator.ceshigongju.Constant.ApiConstant.head_set_address;

public class MainActivity extends BaseActivity implements BleOperatingResultListener {

    private static final int OPEN_DOOR = 1001;
    private final static int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private static final int LOCATION_PERMISSION_REQUESTCODE = 2;
    private static final int CAMERA_PERMISSION_REQUESTCODE = 3;
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.btn_switch)
    Button btnSwitch;
    @BindView(R.id.btn_note)
    Button btnNote;

    private NoteEntityDao mNoteDao;
    private boolean isOpen = false;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不让屏幕休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        titlebar.leftExit(this);
        mNoteDao = GreenDaoHelper.getDaoSession().getNoteEntityDao();

        List<NoteEntity> dataList = new ArrayList<>();
        dataList = mNoteDao.loadAll();
        int sCount = 0;
        int fCount = 0;
        for (NoteEntity bean : dataList) {
            sCount = sCount + bean.getSCount();
            fCount = fCount + bean.getFCount();
        }

        tvCount.setText(sCount + fCount + "");

        LogPlus.e("LockNum = " + SpFileUtils.getLockNum());

        Glide.with(getActivity())
                .load("http://src.house.sina.com.cn/imp/imp/deal/d5/0e/2/5967cbfc73096bebc0dff95419d_p1_mk1.jpg")
                .error(R.mipmap.ic_launcher)
                .transform(new BlurTransform(this, 1, 10))
                .into(imgBg);


        String lokcNum = SpFileUtils.getLockNum();
        if (!TextUtils.isEmpty(lokcNum)) {
            BleHelper.getInstance(this)
                    .setAddressCode(lokcNum);
        }

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
        new AlertDialog.Builder(MainActivity.this).setCancelable(false)
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

    public void insetData(boolean isSuccess) {
        String countStr = tvCount.getText().toString();
        int count = Integer.parseInt(countStr);
        String allCount = String.valueOf(count + 1);
        tvCount.setText(allCount);
        String time = CalendarUtils.getCurrentDay();
        NoteEntity bean = mNoteDao.queryBuilder().where(NoteEntityDao.Properties.Time.eq(time)).unique();

        if (bean != null) {
            if (isSuccess) {
                bean.setSCount(bean.getSCount() + 1);
            } else {
                bean.setSCount(bean.getFCount() + 1);
            }

            mNoteDao.update(bean);

        } else {
            if (isSuccess) {
                bean = new NoteEntity();
                bean.setTime(time);
                bean.setSCount(1);
                bean.setFCount(0);
            } else {
                bean = new NoteEntity();
                bean.setTime(time);
                bean.setSCount(0);
                bean.setFCount(1);
            }

            mNoteDao.insert(bean);
        }

    }

    private void initBtnStatus(boolean isOpen) {
        if (isOpen) {
            btnSwitch.setText("取消开门");
        } else {
            btnSwitch.setText("开门");
        }
    }

    @OnClick({R.id.btn_switch, R.id.btn_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_switch:
                isOpen = !isOpen;
                if (isOpen) {
                    initBtnStatus(true);
                    if (BleHelper.getInstance(this)
                            .getBleDevice() == null) {
                        BleHelper.getInstance(this)
                                .searchDevice();
                    } else {
                        BleHelper.getInstance(this)
                                .doAction(OPEN_DOOR);
                    }
                } else {
                    initBtnStatus(false);
                }

                break;
            case R.id.btn_note:
                startActivity(NoteListActivity.class);
                break;
        }
    }

    @Override
    public void onScanDevice(BleDevice device) {

    }

    @Override
    public void onNotifySuccess() {
        BleHelper.getInstance(this)
                .doAction(OPEN_DOOR);
    }

    @Override
    public void onResetSuccess() {

    }

    @Override
    public void onOpenDoorSuccess() {

        insetData(true);

        if (isOpen) {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    e.onNext("0");
                }
            }).delay(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String integer) throws Exception {
                            if (BleHelper.getInstance(MainActivity.this)
                                    .getBleDevice() == null) {
                                BleHelper.getInstance(MainActivity.this)
                                        .searchDevice();
                            } else {
                                BleHelper.getInstance(MainActivity.this)
                                        .doAction(OPEN_DOOR);
                            }
                        }
                    });

        }

    }

    @Override
    public void onSetNumSuccess() {

    }

    @Override
    public void onResetPassWord(String random) {

    }

    @Override
    public void onNotifyFailure() {

    }

    @Override
    public void onSendDatasFailure() {
        ToastUtil.show(this, "开门失败");
        insetData(false);

        isOpen = false;
        initBtnStatus(false);

    }

    @Override
    public void onGetLockNum(String num) {

    }

    @Override
    public void onConnectSuccess() {

    }
}
