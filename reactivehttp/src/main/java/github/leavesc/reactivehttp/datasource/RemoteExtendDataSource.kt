package github.leavesc.reactivehttp.datasource

import github.leavesc.reactivehttp.mode.IHttpWrapMode
import github.leavesc.reactivehttp.callback.RequestPairCallback
import github.leavesc.reactivehttp.callback.RequestTripleCallback
import github.leavesc.reactivehttp.exception.ServerCodeBadException
import github.leavesc.reactivehttp.viewmodel.IUIActionEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:55
 * @Desc:
 * 提供了 两个/三个 接口同时并发请求的方法
 * 当所有接口都请求成功时，会通过 onSuccess 方法传出请求结果
 * 当包含的某个接口请求失败时，则会直接回调 onFail 方法
 * @GitHub：https://github.com/leavesC
 */
abstract class RemoteExtendDataSource<Api : Any>(
    iActionEvent: IUIActionEvent?,
    apiServiceClass: Class<Api>
) : RemoteDataSource<Api>(iActionEvent, apiServiceClass) {

    fun <DataA, DataB> enqueue(
        apiFunA: suspend Api.() -> IHttpWrapMode<DataA>,
        apiFunB: suspend Api.() -> IHttpWrapMode<DataB>,
        showLoading: Boolean = false,
        baseUrl: String = httpBaseUrl,
        callbackFun: (RequestPairCallback<DataA, DataB>.() -> Unit)? = null
    ): Job {
        return launchMain {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestPairCallback<DataA, DataB>().apply {
                    callbackFun.invoke(this)
                }
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val responseList =
                    listOf(lifecycleSupportedScope.async { apiFunA.invoke(getApiService(baseUrl)) },
                        lifecycleSupportedScope.async { apiFunB.invoke(getApiService(baseUrl)) }
                    ).awaitAll()
                val failed = responseList.find { it.httpIsFailed }
                if (failed != null) {
                    throw ServerCodeBadException(failed)
                }
                onGetResponse(callback, responseList)
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

    private suspend fun <DataA, DataB> onGetResponse(
        callback: RequestPairCallback<DataA, DataB>?,
        responseList: List<IHttpWrapMode<out Any?>>,
    ) {
        callback ?: return
        withNonCancellable {
            callback.onSuccess?.let {
                withMain {
                    it.invoke(
                        responseList[0].httpData as DataA,
                        responseList[1].httpData as DataB
                    )
                }
            }
            callback.onSuccessIO?.let {
                withIO {
                    it.invoke(
                        responseList[0].httpData as DataA,
                        responseList[1].httpData as DataB
                    )
                }
            }
        }
    }

    fun <DataA, DataB, DataC> enqueue(
        apiFunA: suspend Api.() -> IHttpWrapMode<DataA>,
        apiFunB: suspend Api.() -> IHttpWrapMode<DataB>,
        apiFunC: suspend Api.() -> IHttpWrapMode<DataC>,
        showLoading: Boolean = false,
        baseUrl: String = httpBaseUrl,
        callbackFun: (RequestTripleCallback<DataA, DataB, DataC>.() -> Unit)? = null
    ): Job {
        return launchMain {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestTripleCallback<DataA, DataB, DataC>().apply {
                    callbackFun.invoke(this)
                }
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                val responseList = listOf(
                    lifecycleSupportedScope.async { apiFunA.invoke(getApiService(baseUrl)) },
                    lifecycleSupportedScope.async { apiFunB.invoke(getApiService(baseUrl)) },
                    lifecycleSupportedScope.async { apiFunC.invoke(getApiService(baseUrl)) }
                ).awaitAll()
                val failed = responseList.find { it.httpIsFailed }
                if (failed != null) {
                    throw ServerCodeBadException(failed)
                }
                onGetResponse(callback, responseList)
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
                return@launchMain
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

    private suspend fun <DataA, DataB, DataC> onGetResponse(
        callback: RequestTripleCallback<DataA, DataB, DataC>?,
        responseList: List<IHttpWrapMode<out Any?>>
    ) {
        callback ?: return
        withNonCancellable {
            callback.onSuccess?.let {
                withMain {
                    it.invoke(
                        responseList[0].httpData as DataA,
                        responseList[1].httpData as DataB,
                        responseList[2].httpData as DataC
                    )
                }
            }
            callback.onSuccessIO?.let {
                withIO {
                    it.invoke(
                        responseList[0].httpData as DataA,
                        responseList[1].httpData as DataB,
                        responseList[2].httpData as DataC
                    )
                }
            }
        }
    }

}