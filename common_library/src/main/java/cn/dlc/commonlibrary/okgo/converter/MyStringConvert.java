package cn.dlc.commonlibrary.okgo.converter;

/**
 * Created by John on 2018/2/3.
 */

public class MyStringConvert extends MyConverter<String> {

    public MyStringConvert() {
        super(String.class);
    }

    @Override
    public String convert(String stringResponse) throws Throwable {
        return stringResponse;
    }
}
