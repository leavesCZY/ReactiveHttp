package leavesc.hello.weather.core.http

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import leavesc.hello.weather.core.http.api.ApiService
import leavesc.hello.weather.core.http.model.BaseResponse
import leavesc.hello.weather.core.http.model.OptionT

/**
 * 作者：leavesC
 * 时间：2019/5/31 11:16
 * 描述：
 */
open class BaseRemoteDataSource(private val baseViewModelEvent: IBaseViewModelEvent?) {

    protected fun getService(): ApiService = getService(
            ApiService::class.java,
            HttpConfig.BASE_URL_MAP
    )

    protected fun <T : Any> getService(clz: Class<T>, host: String): T {
        return RetrofitManagement.instance.getService(clz, host)
    }

    protected fun <T> execute(observable: Observable<BaseResponse<T>>, callback: RequestCallback<T>?, quietly: Boolean = false) {
        execute(observable, BaseSubscriber(callback), quietly)
    }

    protected fun <T> executeQuietly(observable: Observable<BaseResponse<T>>, callback: RequestCallback<T>?) {
        execute(observable, BaseSubscriber(callback), true)
    }

    @SuppressLint("CheckResult")
    private fun <T> execute(observable: Observable<BaseResponse<T>>, observer: DisposableObserver<OptionT<T>>, quietly: Boolean) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!quietly) {
                        showLoading()
                    }
                }.doFinally {
                    if (!quietly) {
                        dismissLoading()
                    }
                }
                .flatMap(object : Function<BaseResponse<T>, ObservableSource<OptionT<T>>> {
                    override fun apply(t: BaseResponse<T>): ObservableSource<OptionT<T>> {
                        when {
                            t.code == HttpConfig.CODE_SUCCESS || t.message == "OK" -> {
                                val optional: OptionT<T> = OptionT(t.data)
                                return createData(optional)
                            }
                            else -> {
                                throw ServerResultException(t.message ?: "未知错误", t.code)
                            }
                        }
                    }
                }).subscribeWith(observer)
    }

    private fun <T> createData(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun showLoading() {
        baseViewModelEvent?.showLoading()
    }

    private fun dismissLoading() {
        baseViewModelEvent?.dismissLoading()
    }

    private fun showToast(msg: String) {
        baseViewModelEvent?.showToast(msg)
    }

}