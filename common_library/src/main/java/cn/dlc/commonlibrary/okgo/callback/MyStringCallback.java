package cn.dlc.commonlibrary.okgo.callback;

/**
 * 直接返回string的回调
 * Created by John on 2018/2/1.
 */

public abstract class MyStringCallback extends MyCallback<String> {

    public MyStringCallback() {
        setClass(String.class);
    }

    @Override
    String convert(String response) throws Throwable {
        return response;
    }
}
