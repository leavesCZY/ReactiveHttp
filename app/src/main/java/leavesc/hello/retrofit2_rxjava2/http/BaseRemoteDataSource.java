package leavesc.hello.retrofit2_rxjava2.http;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import leavesc.hello.retrofit2_rxjava2.http.model.BaseResponseBody;
import leavesc.hello.retrofit2_rxjava2.viewmodel.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 7:42
 * 描述：
 */
public class BaseRemoteDataSource {

    private CompositeDisposable compositeDisposable;

    protected BaseViewModel baseViewModel;

    public BaseRemoteDataSource(BaseViewModel baseViewModel) {
        this.compositeDisposable = new CompositeDisposable();
        this.baseViewModel = baseViewModel;
    }

    protected <T> T getService(Class<T> clz) {
        return RetrofitManagement.getInstance().getService(clz);
    }

    protected <T> T getService(Class<T> clz, String host) {
        return RetrofitManagement.getInstance().getService(clz, host);
    }

    protected <T> ObservableTransformer<BaseResponseBody<T>, T> applySchedulers() {
        return RetrofitManagement.getInstance().applySchedulers();
    }

    public String getToken() {
        return "token";
    }

    public void execute(Observable observable, Observer observer) {
        execute(observable, observer, true);
    }

    public void executeWithoutDismiss(Observable observable, Observer observer) {
        execute(observable, observer, false);
    }

    public void execute(Observable observable, Observer observer, boolean isDismiss) {
        Disposable disposable = (Disposable) observable
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(applySchedulers())
                .compose(isDismiss ? loadingTransformer() : loadingTransformerWithoutDismiss())
                .subscribeWith(observer);
        addDisposable(disposable);
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected void startLoading() {
        if (baseViewModel != null) {
            baseViewModel.startLoading();
        }
    }

    protected void dismissLoading() {
        if (baseViewModel != null) {
            baseViewModel.dismissLoading();
        }
    }

    protected <T> ObservableTransformer<T, T> loadingTransformer() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> startLoading())
                .doFinally(this::dismissLoading);
    }

    protected <T> ObservableTransformer<T, T> loadingTransformerWithoutDismiss() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> startLoading());
    }

}
