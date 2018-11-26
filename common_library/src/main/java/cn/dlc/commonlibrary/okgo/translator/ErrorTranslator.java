package cn.dlc.commonlibrary.okgo.translator;

/**
 * 异常信息格式化器
 * Created by John on 2018/2/1.
 */
public interface ErrorTranslator {

    /**
     * 格式化异常信息
     * @param throwable
     * @return
     */
    String translate(Throwable throwable);
}
