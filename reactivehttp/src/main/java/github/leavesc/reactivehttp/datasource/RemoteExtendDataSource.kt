package github.leavesc.reactivehttp.datasource

import github.leavesc.reactivehttp.callback.RequestPairCallback
import github.leavesc.reactivehttp.callback.RequestTripleCallback
import github.leavesc.reactivehttp.mode.IHttpWrapMode
import github.leavesc.reactivehttp.viewmodel.IUIAction
import kotlinx.coroutines.*

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
    uiAction: IUIAction?,
    baseHttpUrl: String,
    apiServiceClass: Class<Api>
) : RemoteDataSource<Api>(
    uiAction = uiAction,
    baseHttpUrl = baseHttpUrl,
    apiServiceClass = apiServiceClass
) {

    fun <DataA, DataB> enqueue(
        apiFunA: suspend Api.() -> IHttpWrapMode<DataA>,
        apiFunB: suspend Api.() -> IHttpWrapMode<DataB>,
        showLoading: Boolean = false,
        callbackFun: (RequestPairCallback<DataA, DataB>.() -> Unit)? = null
    ): Job {
        return lifecycleSupportedScope.launch(Dispatchers.Main.immediate) {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestPairCallback<DataA, DataB>().apply {
                    callbackFun.invoke(this)
                }
            }
            if (showLoading) {
                showLoading()
            }
            callback?.onStart?.invoke()
            try {
                val responseList = try {
                    ensureActive()
                    val taskList = coroutineScope {
                        listOf(
                            async { executeApi(apiFunA) },
                            async { executeApi(apiFunB) }
                        )
                    }
                    taskList.awaitAll()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
                ensureActive()
                callback?.onSuccess?.invoke(responseList[0] as DataA, responseList[1] as DataB)
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
            } finally {
                callback?.onFinally?.invoke()
            }
        }
    }

    fun <DataA, DataB, DataC> enqueue(
        apiFunA: suspend Api.() -> IHttpWrapMode<DataA>,
        apiFunB: suspend Api.() -> IHttpWrapMode<DataB>,
        apiFunC: suspend Api.() -> IHttpWrapMode<DataC>,
        showLoading: Boolean = false,
        callbackFun: (RequestTripleCallback<DataA, DataB, DataC>.() -> Unit)? = null
    ): Job {
        return lifecycleSupportedScope.launch(Dispatchers.Main.immediate) {
            val callback = if (callbackFun == null) {
                null
            } else {
                RequestTripleCallback<DataA, DataB, DataC>().apply {
                    callbackFun.invoke(this)
                }
            }
            if (showLoading) {
                showLoading()
            }
            callback?.onStart?.invoke()
            try {
                val responseList = try {
                    ensureActive()
                    val taskList = coroutineScope {
                        listOf(
                            async { executeApi(apiFunA) },
                            async { executeApi(apiFunB) },
                            async { executeApi(apiFunC) }
                        )
                    }
                    taskList.awaitAll()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
                ensureActive()
                callback?.onSuccess?.invoke(
                    responseList[0] as DataA,
                    responseList[1] as DataB,
                    responseList[2] as DataC
                )
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
            } finally {
                callback?.onFinally?.invoke()
            }
        }
    }

}