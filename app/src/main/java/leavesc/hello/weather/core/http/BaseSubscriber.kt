package leavesc.hello.weather.core.http

import io.reactivex.observers.DisposableObserver
import leavesc.hello.weather.core.holder.ToastHolder
import leavesc.hello.weather.core.http.model.OptionT

/**
 * 作者：leavesC
 * 时间：2019/5/31 11:03
 * 描述：
 */
class BaseSubscriber<T> constructor(private val requestCallback: RequestCallback<T>?) :
        DisposableObserver<OptionT<T>>() {

    override fun onNext(t: OptionT<T>) {
        requestCallback?.onSuccess(t.value)
    }

    override fun onError(e: Throwable) {
        if (requestCallback == null) {
            return
        }
        val message = e.message
        val msg = if (message.isNullOrBlank()) "未知错误" else message
        when (requestCallback) {
            is RequestMultiplyToastCallback -> {
                ToastHolder.showToast(msg = msg)
                if (e is BaseException) {
                    requestCallback.onFail(e)
                } else {
                    requestCallback.onFail(ServerResultException(message = msg))
                }
            }
            is RequestMultiplyCallback -> {
                if (e is BaseException) {
                    requestCallback.onFail(e)
                } else {
                    requestCallback.onFail(ServerResultException(message = msg))
                }
            }
            else -> {
                ToastHolder.showToast(msg = msg)
            }
        }
    }

    override fun onComplete() {

    }

}