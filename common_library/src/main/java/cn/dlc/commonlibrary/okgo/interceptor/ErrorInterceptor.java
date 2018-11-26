package cn.dlc.commonlibrary.okgo.interceptor;

/**
 * 异常拦截器
 * Created by John on 2018/1/31.
 */
public interface ErrorInterceptor {

    /**
     * 拦截异常
     * @param tr 异常
     * @return 如果返回true，则表示异常被拦截住，用在拦截token异常之类的
     */
    boolean interceptException(Throwable tr);
}
