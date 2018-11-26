package cn.dlc.commonlibrary.okgo.converter;

/**
 * Created by John on 2018/2/3.
 */

public abstract class MyConverter<T> {

    protected Class<T> mClazz;

    public MyConverter(Class<T> clazz) {
        mClazz = clazz;
    }

    public void setClass(Class<T> clazz) {
        mClazz = clazz;
    }

    public abstract T convert(String stringResponse) throws Throwable;
}
