package com.example.administrator.ceshigongju.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.ceshigongju.R;

import cn.dlc.commonlibrary.utils.DialogUtil;

/**
 * Created by liling on 2017/11/4 0004.
 */

public class WaitDialog extends Dialog {
    private Context mContext;
    private CancelListener mListener;
    private TextView tvCancle;
    private TextView tvValue;
    
    public WaitDialog(Context context) {
        super(context, R.style.CommonDialogStyle);
        this.mContext = context;

        DialogUtil.adjustDialogLayout(this,true,true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_dlg_new);

        tvCancle = (TextView) findViewById(R.id.tv_cancle);
        tvValue = (TextView) findViewById(R.id.tv_wait_dlg);
        setCanceledOnTouchOutside(false);

        initEvent();
    }
    
    public void initEvent(){
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                
                if (mListener != null){
                    mListener.onCancel();
                }
            }
        });
    }
    
   
    
    public void setTexValue(int rid){
        setTexValue(mContext.getResources().getString(rid));
        
    }

    public void setTexValue(String value){
        tvValue.setText(value);

    }
    
    
    public void setOnCancelListener(CancelListener listener){
        this.mListener = listener;
    }
    
    public interface CancelListener{
        public void onCancel();
    }
    
}
