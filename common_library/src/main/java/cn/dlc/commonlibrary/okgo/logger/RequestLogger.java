package cn.dlc.commonlibrary.okgo.logger;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

/**
 * 请求日志打印
 * Created by John on 2018/2/1.
 */

public interface RequestLogger {

    String TAG = "RequestLogger";

    /**
     * 打印响应信息
     *
     * @param url
     * @param headers
     * @param params
     * @param response
     * @param tr
     */
    void logRequest(String url, HttpHeaders headers, HttpParams params, String response,
        Throwable tr);
}
