package leavesc.hello.retrofit2_rxjava2.http.basis;

import android.accounts.AccountsException;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;
import leavesc.hello.retrofit2_rxjava2.BaseApplication;
import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestWithFailCallback;
import leavesc.hello.retrofit2_rxjava2.http.basis.config.HttpCode;
import leavesc.hello.retrofit2_rxjava2.http.basis.exception.ConnectionException;
import leavesc.hello.retrofit2_rxjava2.http.basis.exception.base.BaseException;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 20:52
 * 描述：
 */
public class BaseSubscriber<T> extends DisposableObserver<T> {

    private BaseViewModel baseViewModel;

    private RequestCallback<T> requestCallback;

    private RequestWithFailCallback<T> requestWithFailCallback;

    public BaseSubscriber(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    public BaseSubscriber(BaseViewModel baseViewModel, RequestCallback<T> requestCallback) {
        this.baseViewModel = baseViewModel;
        this.requestCallback = requestCallback;
    }

    public BaseSubscriber(BaseViewModel baseViewModel, RequestWithFailCallback<T> requestWithFailCallback) {
        this.baseViewModel = baseViewModel;
        this.requestWithFailCallback = requestWithFailCallback;
    }

    @Override
    public void onNext(T t) {
        Log.e("HHH", t.toString());
        if (requestCallback != null) {
            requestCallback.onSuccess(t);
        } else if (requestWithFailCallback != null) {
            requestWithFailCallback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (requestWithFailCallback != null) {
            if (e instanceof BaseException) {
                requestWithFailCallback.onFail((BaseException) e);
            } else {
                requestWithFailCallback.onFail(new BaseException(HttpCode.CODE_UNKNOWN, e.getMessage()));
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