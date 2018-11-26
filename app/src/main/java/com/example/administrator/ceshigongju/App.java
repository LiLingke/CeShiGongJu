package com.example.administrator.ceshigongju;

import android.app.Application;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.example.administrator.ceshigongju.net.MyRequestLogger;
import com.example.administrator.ceshigongju.util.GreenDaoHelper;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.exception.ApiException;
import cn.dlc.commonlibrary.okgo.interceptor.ErrorInterceptor;
import cn.dlc.commonlibrary.okgo.translator.DefaultErrorTranslator;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/3/19.
 */

public class App extends Application {
    private static App mIntance;
    public static ExecutorService mPool;
    private HashMap<String, String> numRule = new HashMap<>();
    private List<String> numRuleList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mIntance = this;
        //线程池
        mPool = Executors.newCachedThreadPool();
        GreenDaoHelper.initDatabase();
        BleManager.getInstance().init(this);

        BleManager.getInstance()
                .enableLog(true)
                .setMaxConnectCount(7)
                .setOperateTimeout(5000);

        numRule.put("I", "0");
        numRule.put("N", "1");
        numRule.put("T", "2");
        numRule.put("H", "3");
        numRule.put("E", "4");
        numRule.put("B", "5");
        numRule.put("G", "6");
        numRule.put("O", "7");
        numRule.put("D", "8");
        numRule.put("C", "9");

        numRuleList.add("I");
        numRuleList.add("N");
        numRuleList.add("T");
        numRuleList.add("H");
        numRuleList.add("E");
        numRuleList.add("B");
        numRuleList.add("G");
        numRuleList.add("O");
        numRuleList.add("D");
        numRuleList.add("C");

        // http
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        OkGoWrapper.initOkGo(this, builder.build());
        OkGoWrapper.instance()
                .setErrorTranslator(new DefaultErrorTranslator())
                .setErrorInterceptor(new ErrorInterceptor() {
                    @Override
                    public boolean interceptException(Throwable tr) {

                        if (tr instanceof ApiException) {
                            ApiException ex = (ApiException) tr;
                            if (ex.getCode() == -2) {

                                return true;
                            }
                        }
                        return false;
                    }
                })
                .setRequestLogger(new MyRequestLogger(BuildConfig.DEBUG, 30));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }


    public static App getInstance() {
        if (mIntance == null) {
            mIntance = new App();
        }
        return mIntance;
    }

    public String getLockNum(String key) {
        return numRule.get(key);
    }

    public String getLockNumList(int index) {
        return numRuleList.get(index);
    }
}
