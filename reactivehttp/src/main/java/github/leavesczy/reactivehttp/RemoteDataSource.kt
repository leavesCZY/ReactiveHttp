package github.leavesczy.reactivehttp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

/**
 * @Author: leavesCZY
 * @Date: 2020/5/4 0:56
 * @Desc:
 * 提供了 一个/两个/三个 接口同时并发请求的方法
 * 当所有接口都请求成功时，会通过 onSuccess 方法传出请求结果
 * 当包含的某个接口请求失败时，则会直接回调 onFail 方法
 * @Github：https://github.com/leavesCZY
 */
abstract class RemoteDataSource<Api : Any>(
    coroutineScope: CoroutineScope,
    baseHttpUrl: String,
    apiServiceClass: Class<Api>,
) : BaseRemoteDataSource<Api>(
    coroutineScope = coroutineScope,
    baseHttpUrl = baseHttpUrl,
    apiServiceClass = apiServiceClass
) {

    /**
     * 异步请求
     * 在 onSuccess 回调中直接拿到 IHttpWrapMode 中的 Data
     */
    fun <Data> enqueue(
        apiFun: suspend Api.() -> IHttpWrapMode<Data>,
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
        }, callback = callback, onSuccess = {
            callback?.onSuccess?.invoke(it)
        })
    }

    fun <DataA, DataB> enqueue(
        apiFunA: suspend Api.() -> IHttpWrapMode<DataA>,
        apiFunB: suspend Api.() -> IHttpWrapMode<DataB>,
        callbackFun: (RequestPairCallback<DataA, DataB>.() -> Unit)? = null
    ): Job {
        val callback = if (callbackFun == null) {
            null
        } else {
            RequestPairCallback<DataA, DataB>().apply {
                callbackFun.invoke(this)
            }
        }
        return enqueueReal(block = {
            val taskList = listOf(
                async { executeApi(apiFunA) },
                async { executeApi(apiFunB) }
            )
            taskList.awaitAll()
        }, callback = callback, onSuccess = { responseList ->
            callback?.onSuccess?.invoke(responseList[0] as DataA, responseList[1] as DataB)
        })
    }

    fun <DataA, DataB, DataC> enqueue(
        apiFunA: suspend Api.() -> IHttpWrapMode<DataA>,
        apiFunB: suspend Api.() -> IHttpWrapMode<DataB>,
        apiFunC: suspend Api.() -> IHttpWrapMode<DataC>,
        callbackFun: (RequestTripleCallback<DataA, DataB, DataC>.() -> Unit)? = null
    ): Job {
        val callback = if (callbackFun == null) {
            null
        } else {
            RequestTripleCallback<DataA, DataB, DataC>().apply {
                callbackFun.invoke(this)
            }
        }
        return enqueueReal(block = {
            val taskList = listOf(
                async { executeApi(apiFunA) },
                async { executeApi(apiFunB) },
                async { executeApi(apiFunC) }
            )
            taskList.awaitAll()
        }, callback = callback, onSuccess = { responseList ->
            callback?.onSuccess?.invoke(
                responseList[0] as DataA,
                responseList[1] as DataB,
                responseList[2] as DataC
            )
        })
    }

    /**
     * 异步请求
     * 不限定 Api 的返回值类型
     * 在 onSuccess 回调中拿到网络请求的整个返回值
     */
    fun <Data> enqueueOrigin(
        apiFun: suspend Api.() -> Data,
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null
    ): Job {
        return enqueue(apiFun = {
            AlwaysSuccessHttpWrap(apiFun.invoke(this))
        }, callbackFun = callbackFun)
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