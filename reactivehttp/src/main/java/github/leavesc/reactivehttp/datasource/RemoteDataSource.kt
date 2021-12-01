package github.leavesc.reactivehttp.datasource

import github.leavesc.reactivehttp.callback.RequestCallback
import github.leavesc.reactivehttp.exception.ReactiveHttpException
import github.leavesc.reactivehttp.mode.IHttpWrapMode
import github.leavesc.reactivehttp.viewmodel.IUIAction
import kotlinx.coroutines.*

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:55
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
abstract class RemoteDataSource<Api : Any>(
    uiAction: IUIAction?,
    baseHttpUrl: String,
    apiServiceClass: Class<Api>
) : BaseRemoteDataSource<Api>(
    uiAction = uiAction,
    baseHttpUrl = baseHttpUrl,
    apiServiceClass = apiServiceClass
) {

    /**
     * 异步请求
     * 在 onSuccess 回调中直接拿到 IHttpWrapMode 中的 Data
     */
    fun <Data> enqueueLoading(
        apiFun: suspend Api.() -> IHttpWrapMode<Data>,
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null
    ): Job {
        return enqueue(
            apiFun = apiFun,
            showLoading = true,
            callbackFun = callbackFun
        )
    }

    fun <Data> enqueue(
        apiFun: suspend Api.() -> IHttpWrapMode<Data>,
        showLoading: Boolean = false,
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null
    ): Job {
        return lifecycleSupportedScope.launch(Dispatchers.Main.immediate) {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestCallback<Data>().apply {
                    callbackFun.invoke(this)
                }
            }
            if (showLoading) {
                showLoading()
            }
            callback?.onStart?.invoke()
            try {
                val data = try {
                    ensureActive()
                    executeApi(apiFun)
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
                ensureActive()
                callback?.onSuccess?.invoke(data)
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
            } finally {
                callback?.onFinally?.invoke()
            }
        }
    }

    /**
     * 异步请求
     * 不限定 Api 的返回值类型
     * 在 onSuccess 回调中拿到网络请求的整个返回值
     */
    fun <Data> enqueueOriginLoading(
        apiFun: suspend Api.() -> Data,
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null
    ): Job {
        return enqueueOrigin(
            apiFun = apiFun,
            showLoading = true,
            callbackFun = callbackFun
        )
    }

    fun <Data> enqueueOrigin(
        apiFun: suspend Api.() -> Data,
        showLoading: Boolean = false,
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null
    ): Job {
        return lifecycleSupportedScope.launch(Dispatchers.Main.immediate) {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestCallback<Data>().apply {
                    callbackFun.invoke(this)
                }
            }
            if (showLoading) {
                showLoading()
            }
            callback?.onStart?.invoke()
            try {
                val data = try {
                    ensureActive()
                    apiFun.invoke(apiService)
                } catch (throwable: Throwable) {
                    throw generateException(throwable)
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
                ensureActive()
                callback?.onSuccess?.invoke(data)
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
            } finally {
                callback?.onFinally?.invoke()
            }
        }
    }

    /**
     * 同步请求，可能会抛出异常，外部需做好捕获异常的准备
     * @param apiFun
     */
    @Throws(ReactiveHttpException::class)
    fun <Data> execute(
        apiFun: suspend Api.() -> IHttpWrapMode<Data>
    ): Data {
        return runBlocking {
            return@runBlocking executeApi(apiFun = apiFun)
        }
    }

}