package cn.dlc.commonlibrary.okgo.exception;

/**
 * Created by John on 2018/1/31.
 */

public class ApiException extends Exception {

    private int code;

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
