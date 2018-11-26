package cn.dlc.commonlibrary.okgo.sample_api;

import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import com.lzy.okgo.model.HttpParams;

/**
 * 用来复制粘贴做模板的类，用来参考的
 */
public class SampleApi {

    private final OkGoWrapper mOkGoWrapper;

    private SampleApi() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    private static class InstanceHolder {
        private static final SampleApi sInstance = new SampleApi();
    }

    public static SampleApi get() {
        return InstanceHolder.sInstance;
    }

    //邮箱注册,用来看的，不要用
    public void mailRegist(String user_login, String user_pas, String code,
        Bean01Callback<StringBuilder> callback) {
        HttpParams Params = new HttpParams();
        Params.put("user_login", user_login);
        Params.put("user_pass", user_pas);
        Params.put("code", code);
        mOkGoWrapper.post("MAIL_REGIST_URL", null, Params, StringBuilder.class, callback);
    }

    //rx邮箱注册,用来看的，不要用
//    public Observable<StringBuilder> rxMailRegist(String user_login, String user_pas, String code) {
//        HttpParams Params = new HttpParams();
//        Params.put("user_login", user_login);
//        Params.put("user_pass", user_pas);
//        Params.put("code", code);
//        return mOkGoWrapper.rxPostBean01("MAIL_REGIST_URL", Params, StringBuilder.class);
//    }
}
