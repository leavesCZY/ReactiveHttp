package github.leavesc.reactivehttp.datasource

import github.leavesc.reactivehttp.callback.RequestCallback
import github.leavesc.reactivehttp.exception.BaseHttpException
import github.leavesc.reactivehttp.exception.ServerCodeBadException
import github.leavesc.reactivehttp.mode.IHttpWrapMode
import github.leavesc.reactivehttp.viewmodel.IUIActionEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:55
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
abstract class RemoteDataSource<Api : Any>(
    iUiActionEvent: IUIActionEvent?,
    httpBaseUrl: String,
    apiServiceClass: Class<Api>
) : BaseRemoteDataSource<Api>(iUiActionEvent, httpBaseUrl, apiServiceClass) {

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
        return launchMain {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestCallback<Data>().apply {
                    callbackFun.invoke(this)
                }
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val response = apiFun.invoke(apiService)
                if (!response.httpIsSuccess) {
                    throw ServerCodeBadException(response)
                }
                onGetResponse(callback, response.httpData)
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
            } finally {
                try {
                    callback?.onFinally?.invoke()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
            }
        }
    }

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
        return launchMain {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestCallback<Data>().apply {
                    callbackFun.invoke(this)
                }
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val response = apiFun.invoke(apiService)
                onGetResponse(callback, response)
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
            } finally {
                try {
                    callback?.onFinally?.invoke()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
            }
        }
    }

    private suspend fun <Data> onGetResponse(callback: RequestCallback<Data>?, httpData: Data) {
        callback ?: return
        withNonCancellable {
            callback.onSuccess?.let {
                withMain {
                    it.invoke(httpData)
                }
            }
            callback.onSuccessIO?.let {
                withIO {
                    it.invoke(httpData)
                }
            }
        }
    }

    /**
     * 同步请求，可能会抛出异常，外部需做好捕获异常的准备
     * @param apiFun
     */
    @Throws(BaseHttpException::class)
    fun <Data> execute(
        apiFun: suspend Api.() -> IHttpWrapMode<Data>
    ): Data {
        return runBlocking {
            try {
                val asyncIO = asyncIO {
                    apiFun.invoke(apiService)
                }
                val response = asyncIO.await()
                if (response.httpIsSuccess) {
                    return@runBlocking response.httpData
                }
                throw ServerCodeBadException(response)
            } catch (throwable: Throwable) {
                throw generateBaseException(throwable)
            }
        }
    }

}