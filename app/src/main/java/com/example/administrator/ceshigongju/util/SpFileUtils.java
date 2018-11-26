package com.example.administrator.ceshigongju.util;

import android.text.TextUtils;

import com.example.administrator.ceshigongju.App;

/**
 * Created by Administrator on 2017/7/12.
 */

public class SpFileUtils {

    public final static String LOCK_NUM = "LOCK_NUM";
    public final static String LOCK_ID = "LOCK_ID";


    public static void saveLockNum(String lockNum){
        SPUtils.put(App.getInstance(),LOCK_NUM,lockNum);
    }

    public static String getLockNum(){
        String lockNum = (String) SPUtils.get(App.getInstance(),LOCK_NUM,"");
        return lockNum;
    }

    public static void saveLockId(String lockId){
        SPUtils.put(App.getInstance(),LOCK_ID,lockId);
    }

    public static String getLockId(){
        String lockNum = (String) SPUtils.get(App.getInstance(),LOCK_ID,"");
        return lockNum;
    }
}
