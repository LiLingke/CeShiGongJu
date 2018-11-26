package cn.dlc.commonlibrary.okgo.converter;

/**
 * Created by John on 2018/2/3.
 */

public class Bean01Convert<T> extends MyConverter<T> {

    public Bean01Convert(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T convert(String json) throws Throwable {
        return Convert2.toBean01(json, mClazz);
    }
}
