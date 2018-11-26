package cn.dlc.commonlibrary.okgo.rx;


/**
 * Created by John on 2018/2/1.
 */

public abstract class OkObserver<T>  {//implements Observer<T>

//    private final OkGoWrapper mOkGoWrapper;
//
//    public OkObserver() {
//        mOkGoWrapper = OkGoWrapper.instance();
//    }
//
//    @Override
//    public void onSubscribe(@NonNull Disposable d) {
//
//    }
//
//    @Override
//    public void onNext(@NonNull T t) {
//        onSuccess(t);
//    }
//
//    /**
//     * 成功
//     * @param t
//     */
//    public abstract void onSuccess(T t);
//
//    /**
//     * 失败
//     * @param message
//     * @param tr
//     */
//    public abstract void onFailure(String message, Throwable tr);
//
//    @Override
//    public void onError(@NonNull Throwable e) {
//        String message;
//        ErrorTranslator errorTranslator = mOkGoWrapper.getErrorTranslator();
//        if (errorTranslator == null) {
//            message = e.getMessage();
//        } else {
//            message = errorTranslator.translate(e);
//        }
//        onFailure(message, e);
//    }
//
//    @Override
//    public void onComplete() {
//
//    }
}
