package com.example.administrator.ceshigongju.util;

import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleIndicateCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.example.administrator.ceshigongju.listener.BleOperatingResultListener;
import com.example.administrator.ceshigongju.weight.WaitDialog;
import com.licheedev.myutils.LogPlus;

import org.reactivestreams.Subscriber;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.administrator.ceshigongju.Constant.ApiConstant.C_READ_UUID;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.C_WRITE_UUID;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.HEAD_CODE;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.OPEN_DOOR_CODE;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.RANDOM_CODE;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.RESET_PW_CODE;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.SERVER_UUID;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.SET_TIME_CODE;
import static com.example.administrator.ceshigongju.Constant.ApiConstant.lock_id;


/**
 * Created by liling on 2017/12/25 0025.
 */

public class BleHelper {
    private static final int OPEN_DOOR = 1001;
    private static final int RESET_PW = 1002;
    private static final int SET_TIME = 1003;
    private static final int GET_TIME = 1004;
    private static final int GET_NUM = 1005;
    private Context mContext;
    private WaitDialog mWaitDialog;
    private static BleHelper mBleHelper;
    private String address = "";
    private String rand_code = "";

    public BleDevice mBleDevice;
    private BleOperatingResultListener mResultListener;

    private int doAction = OPEN_DOOR;
    private final Handler mHandler;

    public BleHelper(Context context) {
        this.mContext = context;
        mWaitDialog = new WaitDialog(context);

        HandlerThread handlerThread = new HandlerThread("ble");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());

        initEvent();
    }

    public void setBleStatusListener(BleOperatingResultListener listener) {
        this.mResultListener = listener;
    }

    public void setAddressCode(String code) {
        this.address = code;
        String str = HEAD_CODE + address + RANDOM_CODE;
        rand_code = str + CRC16Utils.getCRC16(str);
        LogPlus.e("rand_code == " + rand_code);
    }


    public static void close() {
        mBleHelper = null;
    }

    /**
     * 获得单例对象
     *
     * @return
     */
    public static BleHelper getInstance(Context context) {
        if (mBleHelper == null) {
            synchronized (BleHelper.class) {
                if (mBleHelper == null) {
                    mBleHelper = new BleHelper(context);
                }
            }
        }
        return mBleHelper;
    }

    /**
     * 初始化监听事件
     */
    public void initEvent() {
        mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismissWaitDlg();
            }
        });
    }

    /**
     * dismiss等待框
     */
    public void dismissWaitDlg() {
        if (mWaitDialog != null) {
            mWaitDialog.dismiss();
        }
    }

    public BleDevice getBleDevice() {
        return mBleDevice;
    }

    /**
     * 判断是哪种操作
     *
     * @param doWhat
     */
    public void doAction(int doWhat) {
        if (doWhat == SET_TIME) {//设置时间
            String time = HEAD_CODE + address + SET_TIME_CODE + BinHexOctUtils.setDeviceTime();
            String str = time + CRC16Utils.getCRC16(time);
            writeDate(wirteDataToBle(str));
        } else {
            if (doWhat == OPEN_DOOR) {//开门
                doAction = OPEN_DOOR;
                writeDate(wirteDataToBle(rand_code));
            } else if (doWhat == RESET_PW) { //设置密码
                doAction = RESET_PW;
                writeDate(wirteDataToBle(rand_code));
            }else if (doWhat == GET_NUM){
                doAction = GET_NUM;
                writeDate(wirteDataToBle(rand_code));
            }
        }
    }

    /**
     * 设置设备号
     *
     * @param deviceNum
     * @return
     */
    public void setDeviceNum(String deviceNum) {
        writeDate(wirteDataToBle(deviceNum));
    }


    /**
     * 扫描设备
     *
     * @param
     */
    public void searchDevice() {
        mWaitDialog.show();
        BleManager.getInstance()
                .scan(new BleScanCallback() {
                    @Override
                    public void onScanStarted(boolean success) {
                    }

                    @Override
                    public void onLeScan(BleDevice bleDevice) {
                        super.onLeScan(bleDevice);
                    }

                    @Override
                    public void onScanning(final BleDevice result) {
                        LogPlus.e("ble", "devicename:" + result.getDevice()
                                .getName());
                        if (result != null && result.getDevice() != null && !TextUtils.isEmpty(
                                result.getDevice()
                                        .getAddress()) && result.getDevice()
                                .getAddress()
                                .contains(SpFileUtils.getLockId())) {
                            BleManager.getInstance()
                                    .cancelScan();
                            LogPlus.e("ble name = " + result.getDevice()
                                    .getName());
                            LogPlus.e("ble address = " + result.getDevice()
                                    .getAddress());
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    connect(result);
                                }
                            });
                        }
                    }

                    @Override
                    public void onScanFinished(List<BleDevice> scanResultList) {
                    }
                });
    }

    /**
     * 扫描设备
     *
     * @param
     */
    public void searchDevice02() {
        BleManager.getInstance()
                .scan(new BleScanCallback() {
                    @Override
                    public void onScanStarted(boolean success) {
                    }

                    @Override
                    public void onLeScan(BleDevice bleDevice) {
                        super.onLeScan(bleDevice);
                    }

                    @Override
                    public void onScanning(final BleDevice result) {
                        String name = result.getDevice().getName();
                        LogPlus.e("ble", "devicename:" + result.getDevice()
                                .getName());
                        if (!TextUtils.isEmpty(name)){
                            mResultListener.onScanDevice(result);
                        }
                        // && (name.contains("E104") || name.contains("BT"))
                    }

                    @Override
                    public void onScanFinished(List<BleDevice> scanResultList) {
                    }
                });
    }

    /**
     * 连接设备
     *
     * @param bleDevice
     */
    public void connect(BleDevice bleDevice) {
//        mWaitDialog.setTexValue("未连接...");
        BleManager.getInstance()
                .connect(bleDevice, new BleGattCallback() {
                    @Override
                    public void onStartConnect() {
                    }

                    @Override
                    public void onConnectFail(BleException exception) {
                        mWaitDialog.dismiss();
                        mResultListener.onNotifyFailure();
                        Toast.makeText(mContext, "onConnectFail", Toast.LENGTH_SHORT)
                                .show();


                    }

                    @Override
                    public void onConnectSuccess(final BleDevice bleDevice, BluetoothGatt gatt,
                                                 int status) {
                        LogPlus.e("连接状态 == " + "onConnectSuccess");
                        mBleDevice = bleDevice;

                        mResultListener.onConnectSuccess();

//                        Observable.timer(200, TimeUnit.MILLISECONDS)
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Consumer<Long>() {
//                                    @Override
//                                    public void accept(Long aLong) throws Exception {
////                                        mWaitDialog.setTexValue("已连接...");
                                        read(mBleDevice);
//                                    }
//                                });
                    }

                    @Override
                    public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice,
                                               BluetoothGatt gatt, int status) {
                        if (!isActiveDisConnected) {
                            mBleDevice = null;
                        }

//                        setDeviceNum("1");
                        LogPlus.e("onDisConnected == " + isActiveDisConnected);
                    }
                });
    }

    public void reallRead(){
        BleManager.getInstance().read(mBleDevice, SERVER_UUID, C_READ_UUID, new BleReadCallback() {
            @Override
            public void onReadSuccess(byte[] data) {
                final String datas = BinHexOctUtils.bytes2HexString(data);
                LogPlus.e("onReadSuccess == " + datas);
            }

            @Override
            public void onReadFailure(BleException exception) {

            }
        });
    }
    /**
     * 对蓝牙的回调设置监听
     */
    private void read(BleDevice bleDevice) {
        LogPlus.e("是每次都进来了吗");
        BleManager.getInstance()
                .notify(bleDevice, SERVER_UUID, C_READ_UUID, new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        LogPlus.e("notify == " + "onNotifySuccess");

                        mResultListener.onNotifySuccess();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        LogPlus.e("notify == " + "onNotifyFailure");
                        mWaitDialog.dismiss();
                        mResultListener.onNotifyFailure();
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        LogPlus.e("notify == " + BinHexOctUtils.bytes2HexString(data));
                        final String datas = BinHexOctUtils.bytes2HexString(data);
                        Observable.timer(200, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        readClallDate(datas);
                                    }
                                });
                    }
                });
    }

    /**
     * 处理蓝牙回调的数据，做出相应的动作
     *
     * @param str
     */
    private void readClallDate(String str) {
        if (str.length() > 20) {
            String cmdcode = str.substring(20, 24);

            if (cmdcode.equals("0A07")) { //获取随机数
                LogPlus.e("0A07 == " + str);
                openAndPw(str);
            } else if (cmdcode.equals("008D")) { //设置密码成功
                dismissWaitDlg();
                mResultListener.onResetSuccess();
            } else if (cmdcode.equals("0A88")) { //开门成功
                dismissWaitDlg();
                mResultListener.onOpenDoorSuccess();
            } else if (cmdcode.equals("0082")) { //设置时间
                dismissWaitDlg();
                mResultListener.onSetNumSuccess();
            }
        }
    }

    /**
     * 设置密码和蓝牙开门获取的随机数
     *
     * @param str
     */
    private void openAndPw(String str) {
        int dex = str.length() - 4;
        String random = str.substring(24, dex);
        LogPlus.e("BLE", "random_DATA: " + random);
        if (doAction == GET_NUM){//获取机号
            String num = str.substring(12, 16);
            mResultListener.onGetLockNum(num);
        }else if (doAction == RESET_PW) {//设置六位密码
            mResultListener.onResetPassWord(random);
        } else if (doAction == OPEN_DOOR) {  //蓝牙开门
            String random1 = BinHexOctUtils.stringXORInt(random, 2);
            String s4 = HEAD_CODE + address + OPEN_DOOR_CODE + random1 + "0001";
            String oprnDoor = s4 + CRC16Utils.getCRC16(s4);
            writeDate(wirteDataToBle(oprnDoor));
        }
    }

    /**
     * 生成管理员密码
     */
    public void setManagerPw(String random, String endTiem, String strPw) {
        String pw;
        String pwR;
        String pwC;
        String time;
        pwR = BinHexOctUtils.stringXORString(strPw, random);
        if (endTiem != null && !TextUtils.isEmpty(endTiem)) {
            int count = CalendarUtils.getGapCount("2017-01-01", endTiem);
            time = BinHexOctUtils.decimal2fitHex(count, 2);
        } else {
            time = "8E94"; //100年
            LogPlus.e("门禁期限 = " + "100年");
        }
        pwC = HEAD_CODE + address + RESET_PW_CODE + pwR + "0001" + time;
        pw = pwC + CRC16Utils.getCRC16(pwC);
        writeDate(wirteDataToBle(pw));
    }

    /**
     * 分包发生指令
     *
     * @param data
     */
    public List<String> wirteDataToBle(String data) {
        String datas = data;
        List<String> dataList = new ArrayList<>();
        int i = 0;
        while (datas.length() != 0) {
            final String temp = datas.substring(0, datas.length() >= 40 ? 40 : datas.length());
            datas = datas.substring(temp.length(), datas.length());
            dataList.add(temp);
            i++;
        }
        return dataList;
    }


    /**
     * 写操作
     *
     * @param data
     */
    public void writeDate(final List<String> data) {

        Observable.fromIterable(data)
                .concatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        LogPlus.e("有进入这里？");
                        return rxSendData(s);
                    }
                })
                .retry(2)
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String str) {
                        LogPlus.e("onNext == " + str);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogPlus.e("onError=" + System.currentTimeMillis(), e);
                        dismissWaitDlg();
                        mResultListener.onSendDatasFailure();
                    }

                    @Override
                    public void onComplete() {
                        LogPlus.e("onComplete == " + "0k");
                    }
                });

    }

    private Observable<String> rxSendData(final String str) {
        LogPlus.e("data = " + str);

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                BleManager.getInstance()
                        .write(mBleDevice, SERVER_UUID, C_WRITE_UUID,
                                BinHexOctUtils.hexString2Bytes(str.toUpperCase()), new BleWriteCallback() {
                                    @Override
                                    public void onWriteSuccess() {

                                        LogPlus.e("发成功===========" + SystemClock.uptimeMillis());

                                        // 发送数据到设备成功
                                        LogPlus.e("onWriteSuccess" + Thread.currentThread()
                                                .getName());
                                        LogPlus.e("BLE", "Write Success, DATA: " + str.toUpperCase());
                                        e.onNext(str);
                                        e.onComplete();
                                    }

                                    @Override
                                    public void onWriteFailure(BleException exception) {
                                        // 发送数据到设备失败
                                        LogPlus.e("BLE",
                                                "Write Failure, DATA: " + exception.getDescription());
                                        RuntimeException e1 =
                                                new RuntimeException(exception.getDescription());
                                        e.onError(e1);
                                    }
                                });
            }
        }).subscribeOn(AndroidSchedulers.from(mHandler.getLooper()));
    }

    public void write(final String str){
        BleManager.getInstance()
                .write(mBleDevice, SERVER_UUID, C_WRITE_UUID,
                        BinHexOctUtils.hexString2Bytes(str.toUpperCase()), new BleWriteCallback() {
                            @Override
                            public void onWriteSuccess() {
                                LogPlus.e("onWriteSuccess == " + str);
                            }

                            @Override
                            public void onWriteFailure(BleException exception) {
                                LogPlus.e("onWriteFailure == " + str);
                            }
                        });
    }
}
