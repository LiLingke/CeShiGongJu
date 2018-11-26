package cn.dlc.commonlibrary.okgo;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import cn.dlc.commonlibrary.okgo.callback.MyCallback;
import java.io.IOException;

import cn.dlc.commonlibrary.okgo.interceptor.ErrorInterceptor;
import cn.dlc.commonlibrary.okgo.logger.RequestLogger;
import cn.dlc.commonlibrary.okgo.translator.ErrorTranslator;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by John on 2018/1/31.
 */

public class OkGoWrapper {

    private ErrorInterceptor mErrorInterceptor;
    private ErrorTranslator mErrorTranslator;
    private RequestLogger mRequestLogger;

    private OkGoWrapper() {
    }

    private static class InstanceHolder {
        private static final OkGoWrapper sInstance = new OkGoWrapper();
    }

    public static OkGoWrapper instance() {
        return InstanceHolder.sInstance;
    }

    /**
     * 检查是否已经初始化OKgo
     */
    private void checkOkGo() {
        OkGo.getInstance().getContext();
    }

    // 网络
    //OkHttpClient.Builder builder = new OkHttpClient.Builder();
    //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
    //OkGoWrapper.initOkGo(this, builder.build());

    /**
     * 初始化OKgo
     *
     * @param app
     * @param okHttpClient
     */
    public static void initOkGo(Application app, OkHttpClient okHttpClient) {
        OkGo okGo = OkGo.getInstance().init(app);
        if (okHttpClient != null) {
            okGo.setOkHttpClient(okHttpClient);
        }
    }

    /**
     * 设置错误拦截器
     *
     * @param errorInterceptor
     * @return
     */
    public OkGoWrapper setErrorInterceptor(ErrorInterceptor errorInterceptor) {
        mErrorInterceptor = errorInterceptor;
        return this;
    }

    /**
     * 设置错误格式化器
     *
     * @param errorTranslator
     * @return
     */
    public OkGoWrapper setErrorTranslator(ErrorTranslator errorTranslator) {
        mErrorTranslator = errorTranslator;
        return this;
    }

    /**
     * 设置请求日志工具
     *
     * @param requestLogger
     * @return
     */
    public OkGoWrapper setRequestLogger(RequestLogger requestLogger) {
        mRequestLogger = requestLogger;
        return this;
    }

    public ErrorInterceptor getErrorInterceptor() {
        return mErrorInterceptor;
    }

    public ErrorTranslator getErrorTranslator() {
        return mErrorTranslator;
    }

    public RequestLogger getRequestLogger() {
        return mRequestLogger;
    }

    public <T> void post(String url, HttpHeaders headers, HttpParams params, Class<T> clazz,
                         MyCallback<T> callback) {

        checkOkGo();

        if (clazz != null) {
            callback.setClass(clazz);
        }

        OkGo.<T>post(url).headers(headers).params(params).execute(callback);
    }

    public <T> void post(String url, HttpParams params, Class<T> clazz, MyCallback<T> callback) {
        post(url, null, params, clazz, callback);
    }

    public <T> void get(String url, HttpHeaders headers, HttpParams params, Class<T> clazz,
        MyCallback<T> callback) {

        checkOkGo();

        if (clazz != null) {
            callback.setClass(clazz);
        }

        OkGo.<T>get(url).headers(headers).params(params).execute(callback);
    }

    public <T> void get(String url, HttpParams params, Class<T> clazz, MyCallback<T> callback) {
        get(url, null, params, clazz, callback);
    }

    private Response syncRequest(HttpMethod httpMethod, final String url, final HttpHeaders headers,
                                 final HttpParams params) throws IOException {

        switch (httpMethod) {
            case GET:
                return OkGo.get(url).headers(headers).params(params).execute();
            default:
                return OkGo.post(url).headers(headers).params(params).execute();
        }
    }

    /**
     * rx方式的post请求，并做code=1处理
     *
     * @param url
     * @param headers
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
//    public <T> Observable<T> rxPostBean01(String url, HttpHeaders headers, HttpParams params,
//        final Class<T> clazz) {
//        return rxPost(url, headers, params, new Bean01Convert<>(clazz));
//    }
//
//    /**
//     * rx方式的post请求，并做code=1处理
//     *
//     * @param url
//     * @param params
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> rxPostBean01(String url, HttpParams params, final Class<T> clazz) {
//        return rxPostBean01(url, null, params, clazz);
//    }
//
//    /**
//     * rx方式的get请求，并做code=1处理
//     *
//     * @param url
//     * @param headers
//     * @param params
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> rxGetBean01(String url, HttpHeaders headers, HttpParams params,
//        Class<T> clazz) {
//        return rxGet(url, headers, params, new Bean01Convert<>(clazz));
//    }
//
//    /**
//     * rx方式的get请求，并做code=1处理
//     *
//     * @param url
//     * @param params
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> rxGetBean01(String url, HttpParams params, Class<T> clazz) {
//        return rxGetBean01(url, null, params, clazz);
//    }
//
//    /**
//     * rx方式的post请求
//     *
//     * @param url
//     * @param headers
//     * @param params
//     * @param converter
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> rxPost(String url, HttpHeaders headers, HttpParams params,
//        MyConverter<T> converter) {
//        return rxRequest(HttpMethod.POST, url, headers, params, converter);
//    }
//
//    /**
//     * rx方式的post请求
//     *
//     * @param url
//     * @param params
//     * @param converter
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> rxPost(String url, HttpParams params, MyConverter<T> converter) {
//        return rxRequest(HttpMethod.POST, url, params, converter);
//    }
//
//    /**
//     * rx方式的get请求
//     *
//     * @param url
//     * @param headers
//     * @param params
//     * @param converter
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> rxGet(String url, HttpHeaders headers, HttpParams params,
//        MyConverter<T> converter) {
//        return rxRequest(HttpMethod.GET, url, headers, params, converter);
//    }
//
//    /**
//     * rx方式的get请求
//     *
//     * @param url
//     * @param params
//     * @param converter
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> rxGet(String url, HttpParams params, MyConverter<T> converter) {
//        return rxRequest(HttpMethod.GET, url, params, converter);
//    }
//
//    /**
//     * 通过rx请求
//     *
//     * @param httpMethod http请求方法
//     * @param url
//     * @param params
//     * @param converter 返回类型转换器
//     * @param <T> 返回类型
//     * @return
//     */
//    public <T> Observable<T> rxRequest(final HttpMethod httpMethod, final String url,
//        final HttpParams params,
//        @android.support.annotation.NonNull final MyConverter<T> converter) {
//
//        return rxRequest(httpMethod, url, null, params, converter);
//    }
//
//    /**
//     * 通过rx请求
//     *
//     * @param httpMethod http请求方法
//     * @param url
//     * @param headers
//     * @param params
//     * @param converter 返回类型转换器
//     * @param <T> 返回类型
//     * @return
//     */
//    public <T> Observable<T> rxRequest(final HttpMethod httpMethod, final String url,
//        final HttpHeaders headers, final HttpParams params,
//        @android.support.annotation.NonNull final MyConverter<T> converter) {
//
//        checkOkGo();
//
//        return Observable.create(new ObservableOnSubscribe<T>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
//
//                String json = null;
//
//                try {
//
//                    Response response = syncRequest(httpMethod, url, headers, params);
//
//                    int responseCode = response.code();
//
//                    // 服务器错误
//                    if (responseCode == 404 || responseCode >= 500) {
//                        throw HttpException.NET_ERROR();
//                    }
//
//                    json = Convert2.toString(response);
//
//                    T t = converter.convert(json);
//
//                    // 打印成功的日志
//                    if (mRequestLogger != null) {
//                        mRequestLogger.logRequest(url, headers, params, json, null);
//                    }
//
//                    emitter.onNext(t);
//                } catch (Throwable e) {
//                    // 打印失败时的日志
//                    if (mRequestLogger != null) {
//                        mRequestLogger.logRequest(url, headers, params, json, e);
//                    }
//                    //e.printStackTrace();
//                    if (!emitter.isDisposed()) {
//                        emitter.onError(e);
//                        return;
//                    }
//                }
//                emitter.onComplete();
//            }
//        }).compose(this.<T>interceptError());
//    }
//
//    /**
//     * 拦截错误
//     *
//     * @param <T>
//     * @return
//     */
//    private <T> ObservableTransformer<T, T> interceptError() {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(
//                @io.reactivex.annotations.NonNull Observable<T> upstream) {
//
//                return upstream.onErrorResumeNext(
//                    new Function<Throwable, ObservableSource<? extends T>>() {
//                        @Override
//                        public ObservableSource<? extends T> apply(@NonNull Throwable throwable)
//                            throws Exception {
//                            if (mErrorInterceptor != null && mErrorInterceptor.interceptException(
//                                throwable)) {
//                                return Observable.empty();
//                            } else {
//                                return Observable.error(throwable);
//                            }
//                        }
//                    });
//            }
//        };
//    }
}
