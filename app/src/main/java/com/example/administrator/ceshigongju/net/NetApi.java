package com.example.administrator.ceshigongju.net;

import com.example.administrator.ceshigongju.Constant;
import com.example.administrator.ceshigongju.bean.BaseBean;
import com.example.administrator.ceshigongju.bean.LockBean;
import com.lzy.okgo.model.HttpParams;
import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;

/**
 * 注册登录相关api
 */
public class NetApi {

    private final OkGoWrapper mOkGoWrapper;

    private NetApi() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    private static class InstanceHolder {
        private static final NetApi sInstance = new NetApi();
    }

    public static NetApi get() {
        return InstanceHolder.sInstance;
    }

    /**
     * 录入锁
     * @param lockId
     * @param lockName
     * @param lockNum
     * @param callback
     */
    public void upLoadLock(String lockId, String lockName,String lockNum, Bean01Callback<BaseBean> callback) {
        HttpParams params = new HttpParams();
        params.put("lock_id", lockId);
        params.put("lock_name", lockName);
        params.put("lock_no", lockNum);
        mOkGoWrapper.post(Constant.ApiConstant.API_entry_lock, params, BaseBean.class, callback);
    }

    public void getLockSeriaNumber(String lockId,int status,Bean01Callback<LockBean> callback) {
        HttpParams params = new HttpParams();
        params.put("lock_id", lockId);
        params.put("status", status);
        mOkGoWrapper.post(Constant.ApiConstant.API_lock_message, params, LockBean.class, callback);
    }

}
