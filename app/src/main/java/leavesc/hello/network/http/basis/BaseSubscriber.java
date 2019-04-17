package leavesc.hello.network.http.basis;

import io.reactivex.observers.DisposableObserver;
import leavesc.hello.network.holder.ToastHolder;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.basis.callback.RequestMultiplyCallback;
import leavesc.hello.network.http.basis.config.HttpCode;
import leavesc.hello.network.http.basis.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/27 20:52
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class BaseSubscriber<T> extends DisposableObserver<T> {

    private RequestCallback<T> requestCallback;

    BaseSubscriber(RequestCallback<T> requestCallback) {
        this.requestCallback = requestCallback;
    }

    @Override
    public void onNext(T t) {
        if (requestCallback != null) {
            requestCallback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (requestCallback instanceof RequestMultiplyCallback) {
            RequestMultiplyCallback callback = (RequestMultiplyCallback) requestCallback;
            if (e instanceof BaseException) {
                callback.onFail((BaseException) e);
            } else {
                callback.onFail(new BaseException(HttpCode.CODE_UNKNOWN, e.getMessage()));
            }
        } else {
            ToastHolder.showToast(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

}