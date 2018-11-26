package com.example.administrator.ceshigongju.weight;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ceshigongju.App;
import com.example.administrator.ceshigongju.Constant;
import com.example.administrator.ceshigongju.R;
import com.example.administrator.ceshigongju.bean.LockBean;
import com.licheedev.myutils.LogPlus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dlc.commonlibrary.utils.glide.GlideUtil;


/**
 * Created by liling on 2017/11/4 0004.
 */

public class ShowDetailDialog extends Dialog {
    @BindView(R.id.tv_order)
    TextView mTvOrder;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.img_show)
    ImageView mImgShow;
    private Context mContext;

    public ShowDetailDialog(Context context) {
        super(context, R.style.CommonDialogStyle);
        this.mContext = context;
        setContentView(R.layout.dialog_show_lock);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);

    }

    public void initView(LockBean lockBean) {
        if (lockBean != null) {
            if (lockBean.data.lock_img != null && !TextUtils.isEmpty(lockBean.data.lock_img)) {
                if (lockBean.data.lock_img.startsWith(".")) {
                    String urlStr = lockBean.data.lock_img;
                    String url = urlStr.substring(1, urlStr.length());
                    LogPlus.e("urlImg = " + Constant.ApiConstant.HTTP_URL + url);
                    GlideUtil.loadImg(mContext, Constant.ApiConstant.HTTP_URL + url, mImgShow);
                } else {
                    GlideUtil.loadImg(mContext, Constant.ApiConstant.HTTP_URL + lockBean.data.lock_img, mImgShow);
                }
            }

            mTvOrder.setText(lockBean.data.id + "");
            String lockNum = lockBean.data.lock_no;
            if (!TextUtils.isEmpty(lockNum) && lockNum.length() == 4) {
                char[] chars = lockNum.toCharArray();
                String lock = App.getInstance().getLockNum(String.valueOf(chars[0]))
                        + App.getInstance().getLockNum(String.valueOf(chars[1]))
                        + App.getInstance().getLockNum(String.valueOf(chars[2]))
                        + App.getInstance().getLockNum(String.valueOf(chars[3]));

                mTvNum.setText(lock);
            }
        }
    }
}
