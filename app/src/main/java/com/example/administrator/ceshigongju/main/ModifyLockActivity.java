package com.example.administrator.ceshigongju.main;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.ceshigongju.App;
import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.base.activity.BaseActivity;
import com.example.administrator.ceshigongju.util.BinHexOctUtils;
import com.example.administrator.ceshigongju.util.SpFileUtils;
import com.licheedev.myutils.LogPlus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.widget.TitleBar;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ModifyLockActivity extends BaseActivity {
    private final static int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private static final int LOCATION_PERMISSION_REQUESTCODE = 2;
    private static final int CAMERA_PERMISSION_REQUESTCODE = 3;
    private int RequestCode = 1003;
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_canle)
    Button btnCanle;
    @BindView(R.id.btn_scan)
    Button btnScan;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_modify_lock;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlebar.leftExit(this);
        LogPlus.e("LockNum = " + SpFileUtils.getLockNum());
        String lockNum16 = SpFileUtils.getLockNum();
        String lockId = SpFileUtils.getLockId();

        if (!TextUtils.isEmpty(lockNum16)) {
            int lockNum = Integer.parseInt(SpFileUtils.getLockNum(), 16);
            etNum.setText(lockNum + "");
            etAddress.setText(lockId);
        }

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lockNum = etNum.getText().toString();
                String lockId = etAddress.getText().toString();
                if (TextUtils.isEmpty(lockNum) || TextUtils.isEmpty(lockId)) {
                    showToast("请输入锁号和密码");
                } else {
                    int num = Integer.parseInt(lockNum);
                    String str = BinHexOctUtils.decimal2fitHex(num, 2);
                    SpFileUtils.saveLockNum(str);
                    SpFileUtils.saveLockId(lockId);
                    startActivity(MainActivity.class);
                }
            }
        });

        btnCanle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(SpFileUtils.getLockNum()) || TextUtils.isEmpty(SpFileUtils.getLockId())) {
                    showToast("第一次使用请先输入锁id和编号");
                    return;
                }
                startActivity(CheckoutActivity.class);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkScanPermission();
            }
        });
    }

    /**
     * 解析扫描到的数据
     *
     * @param value
     */
    private void analyticData(String value) {
        String[] data = value.split("/");
        //SHGY28/II:96:B3:85:8F:2E:C5:IN
        if (data != null && data.length == 2) {
            String lockName = data[0];
            String str = data[1];
            String num1 = str.substring(0, 2);
            String num2 = str.substring(str.length() - 2, str.length());
            String lockId = str.substring(3, str.length() - 3);
            String code = num1 + num2;
            char[] chars = code.toCharArray();
            String lock = App.getInstance().getLockNum(String.valueOf(chars[0]))
                    + App.getInstance().getLockNum(String.valueOf(chars[1]))
                    + App.getInstance().getLockNum(String.valueOf(chars[2]))
                    + App.getInstance().getLockNum(String.valueOf(chars[3]));
            int num = Integer.parseInt(lock);

            etNum.setText(num + "");
            etAddress.setText(lockId);
        } else {
            showToast("数据异常");
        }
    }


    /**
     * 检查是否有相关权限
     */
    private void checkScanPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //没有授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUESTCODE);
        } else {
            //已经授权
            Intent intent = new Intent();
            intent.setClass(ModifyLockActivity.this, ScanActivity.class);
            startActivityForResult(intent, RequestCode);
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
            case CAMERA_PERMISSION_REQUESTCODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setClass(ModifyLockActivity.this, ScanActivity.class);
                    startActivityForResult(intent, RequestCode);
                } else {
                    LogPlus.e("Camera  showTeachDialog");
                    showTeachDialog();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

        } else if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                case Activity.RESULT_OK: {
                }
                break;
                case Activity.RESULT_CANCELED: {
                }
                break;
            }
        } else if (RequestCode == requestCode) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                String result = data.getExtras()
                        .getString("result");
                Log.e("MainA", "result=" + result);
                if (!TextUtils.isEmpty(result)) {
                    LogPlus.e("二维码: " + result);
                    analyticData(result);
                } else {

                }
            }
        }
    }

    /**
     * 引导用户打开相关权限
     */
    private void showTeachDialog() {
        new AlertDialog.Builder(ModifyLockActivity.this).setCancelable(false)
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
