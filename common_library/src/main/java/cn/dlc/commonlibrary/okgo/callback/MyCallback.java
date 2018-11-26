package cn.dlc.commonlibrary.okgo.callback;

import android.support.annotation.NonNull;
import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.converter.Convert2;
import cn.dlc.commonlibrary.okgo.interceptor.ErrorInterceptor;
import cn.dlc.commonlibrary.okgo.logger.RequestLogger;
import cn.dlc.commonlibrary.okgo.translator.ErrorTranslator;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * Created by John on 2018/2/1.
 */

public abstract class MyCallback<T> extends AbsCallback<T> {

    private final OkGoWrapper mOkGoWrapper;
    private Request<T, ? extends Request> mRequest;
    protected Class<T> mClazz;
    private String mJson;

    public MyCallback() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    public void setClass(@NonNull Class<T> clazz) {
        mClazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        mRequest = request;
    }

    @Override
    public void onSuccess(Response<T> response) {
        try {
            onSuccess(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {

        mJson = Convert2.toString(response);

        if (mClazz == null) {
            throw new NullPointerException(
                "mClazz为null，必须调用 cn.dlc.commonlibrary.okgo.callback.MyCallback.setClass()");
        }

        T t = convert(mJson);
        // 打印日志
        RequestLogger requestLogger = mOkGoWrapper.getRequestLogger();
        if (requestLogger != null) {
            requestLogger.logRequest(mRequest.getUrl(), mRequest.getHeaders(), mRequest.getParams(),
                mJson, null);
        }
        return t;
    }

    @Override
    public void onError(Response<T> response) {
        Throwable exception = response.getException();
        // 打印日志
        RequestLogger requestLogger = mOkGoWrapper.getRequestLogger();
        if (requestLogger != null) {
            requestLogger.logRequest(mRequest.getUrl(), mRequest.getHeaders(), mRequest.getParams(),
                mJson, exception);
        }

        ErrorInterceptor errorInterceptor = mOkGoWrapper.getErrorInterceptor();
        // 拦截错误
        if (errorInterceptor != null && errorInterceptor.interceptException(exception)) {
            return;
        }

        String message;
        ErrorTranslator errorTranslator = mOkGoWrapper.getErrorTranslator();
        if (errorTranslator == null) {
            message = exception.getMessage();
        } else {
            message = errorTranslator.translate(exception);
        }

        try {
            onFailure(message, exception);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换
     *
     * @param stringResponse
     * @return
     * @throws Throwable
     */
    abstract T convert(String stringResponse) throws Throwable;

    /**
     * 成功
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 失败
     *
     * @param message
     * @param tr
     */
    public abstract void onFailure(String message, Throwable tr);
}
