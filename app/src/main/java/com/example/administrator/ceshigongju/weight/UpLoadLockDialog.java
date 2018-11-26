package com.example.administrator.ceshigongju.weight;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ceshigongju.App;
import com.example.administrator.ceshigongju.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by liling on 2017/11/4 0004.
 */

public class UpLoadLockDialog extends Dialog {
    private String lockId = "";
    private String lockNum = "IIIN";
    private int num = 1;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.tv_key)
    TextView mTvKey;
    @BindView(R.id.btn_include_left)
    Button mBtnIncludeLeft;
    @BindView(R.id.btn_include_right)
    Button mBtnIncludeRight;
    private Context mContext;
    private OnSureListener mListener;

    public UpLoadLockDialog(Context context) {
        super(context, R.style.CommonDialogStyle);
        this.mContext = context;
        setContentView(R.layout.dialog_upload_lock);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        initEvent();
    }

    public void show(OnSureListener listener, String lockNum) {
        super.show();
        this.mListener = listener;
        if (!TextUtils.isEmpty(lockNum) && lockNum.length() == 4) {
            char[] chars = lockNum.toCharArray();
            String lock = App.getInstance().getLockNum(String.valueOf(chars[0]))
                    + App.getInstance().getLockNum(String.valueOf(chars[1]))
                    + App.getInstance().getLockNum(String.valueOf(chars[2]))
                    + App.getInstance().getLockNum(String.valueOf(chars[3]));
            int numTemp = Integer.parseInt(lock) + 1;
            if (numTemp % 2048 == 0) {
                num = 2048;
            } else {
                num = numTemp % 2048;
            }
            mEtNum.setText(String.valueOf(num));
        } else {
            Toast.makeText(mContext, "机号获取有误，请检查数据", Toast.LENGTH_SHORT).show();
        }
    }

    public void initEvent() {
        mBtnIncludeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSure(lockNum, num);
                }
            }
        });

        mBtnIncludeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    String value = editable.toString();
                    if (value != null && !TextUtils.isEmpty(value)) {
                        int num = Integer.parseInt(value);
                        if (num <= 0) {
                            num = 1;
                            mEtNum.setText(String.valueOf(num));
                        } else if (num > 2048) {
                            num = 2048;
                            mEtNum.setText(String.valueOf(num));
                        }
                        mTvKey.setText(getString(num));
                    } else {
                        mTvKey.setText(getString(1));
                    }
                } else {
                    mTvKey.setText(getString(1));
                }
            }
        });


    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getString(int num) {
        int one = num / 1000;
        int two = (num % 1000) / 100;
        int three = (num % 100) / 10;
        int four = num % 10;

        String strHead = App.getInstance().getLockNumList(one) + App.getInstance().getLockNumList(two);
        String strtail = App.getInstance().getLockNumList(three) + App.getInstance().getLockNumList(four);
        this.num = num;
        lockNum = strHead + strtail;

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strHead).append(":").append(lockId).append(":").append(strtail);
        return stringBuffer.toString();
    }

    public interface OnSureListener {
        public void onSure(String value, int num);
    }

}
