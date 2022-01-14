package github.leavesczy.reactivehttp.datasource

import github.leavesczy.reactivehttp.callback.RequestCallback
import github.leavesczy.reactivehttp.exception.ReactiveHttpException
import github.leavesczy.reactivehttp.mode.AlwaysSuccessHttpWrap
import github.leavesczy.reactivehttp.mode.IHttpWrapMode
import github.leavesczy.reactivehttp.viewmodel.IUIAction
import kotlinx.coroutines.Job

/**
 * @Author: leavesCZY
 * @Date: 2020/5/4 0:55
 * @Desc:
 * @Github：https://github.com/leavesCZY
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
        val callback = if (callbackFun == null) {
            null
        } else {
            RequestCallback<Data>().apply {
                callbackFun.invoke(this)
            }
        }
        return enqueueReal(block = {
            executeApi(apiFun)
        }, showLoading = showLoading, callback = callback, onSuccess = {
            callback?.onSuccess?.invoke(it)
        })
    }

    /**
     * 异步请求
     * 不限定 Api 的返回值类型
     * 在 onSuccess 回调中拿到网络请求的整个返回值
     */
    fun <Data> enqueueOrigin(
        apiFun: suspend Api.() -> Data,
        showLoading: Boolean = false,
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null
    ): Job {
        return enqueue(apiFun = {
            AlwaysSuccessHttpWrap(apiFun.invoke(this))
        }, showLoading = showLoading, callbackFun = callbackFun)
    }

    /**
     * 同步请求，可能会抛出异常，外部需做好捕获异常的准备
     * 在 onSuccess 回调中直接拿到 IHttpWrapMode 中的 Data
     * @param apiFun
     */
    @Throws(ReactiveHttpException::class)
    suspend fun <Data> execute(
        apiFun: suspend Api.() -> IHttpWrapMode<Data>
    ): Data {
        return executeApi(apiFun = apiFun)
    }

    /**
     * 同步请求，可能会抛出异常，外部需做好捕获异常的准备
     * 不限定 Api 的返回值类型
     * 在 onSuccess 回调中拿到网络请求的整个返回值
     * @param apiFun
     */
    @Throws(ReactiveHttpException::class)
    suspend fun <Data> executeOrigin(
        apiFun: suspend Api.() -> Data
    ): Data {
        return executeApi(apiFun = {
            AlwaysSuccessHttpWrap(apiFun.invoke(this))
        })
    }

}