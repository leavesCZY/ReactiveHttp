package leavesc.hello.network.http.basis;

import android.accounts.AccountsException;
import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;
import leavesc.hello.network.BaseApplication;
import leavesc.hello.network.http.basis.callback.RequestCallback;
import leavesc.hello.network.http.basis.callback.RequestMultiplyCallback;
import leavesc.hello.network.http.basis.config.HttpCode;
import leavesc.hello.network.http.basis.exception.ConnectionException;
import leavesc.hello.network.http.basis.exception.base.BaseException;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2018/10/27 20:52
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class BaseSubscriber<T> extends DisposableObserver<T> {

    private BaseViewModel baseViewModel;

    private RequestCallback<T> requestCallback;

    private RequestMultiplyCallback<T> requestMultiplyCallback;

    public BaseSubscriber(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    public BaseSubscriber(BaseViewModel baseViewModel, RequestCallback<T> requestCallback) {
        this.baseViewModel = baseViewModel;
        this.requestCallback = requestCallback;
    }

    public BaseSubscriber(BaseViewModel baseViewModel, RequestMultiplyCallback<T> requestMultiplyCallback) {
        this.baseViewModel = baseViewModel;
        this.requestMultiplyCallback = requestMultiplyCallback;
    }

    @Override
    public void onNext(T t) {
        if (requestCallback != null) {
            requestCallback.onSuccess(t);
        } else if (requestMultiplyCallback != null) {
            requestMultiplyCallback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (requestMultiplyCallback != null) {
            if (e instanceof BaseException) {
                requestMultiplyCallback.onFail((BaseException) e);
            } else {
                requestMultiplyCallback.onFail(new BaseException(HttpCode.CODE_UNKNOWN, e.getMessage()));
            }
        } else {
            if (e instanceof AccountsException) {

            } else if (e instanceof ConnectionException) {
                if (baseViewModel == null) {
                    Toast.makeText(BaseApplication.getAppContext(), "网络连接失败，请稍候再试", Toast.LENGTH_SHORT).show();
                } else {
                    baseViewModel.showToast("网络连接失败，请稍候再试");
                }
            } else {
                if (baseViewModel == null) {
                    Toast.makeText(BaseApplication.getAppContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    baseViewModel.showToast(e.getMessage());
                }
            }
        }
    }

    @Override
    public void onComplete() {

    }

}