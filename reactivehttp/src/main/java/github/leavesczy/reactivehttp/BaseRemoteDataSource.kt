package github.leavesczy.reactivehttp

import android.util.LruCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLHandshakeException
import kotlin.coroutines.cancellation.CancellationException

/**
 * @Author: leavesCZY
 * @Date: 2022/9/25 15:29
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
abstract class BaseRemoteDataSource<Api : Any>(
    protected val coroutineScope: CoroutineScope,
    protected val baseHttpUrl: String,
    protected val apiServiceClass: Class<Api>,
) {

    companion object {

        /**
         * Retrofit 缓存
         */
        private val retrofitCache = LruCache<String, Retrofit>(3)

        /**
         * ApiService 缓存
         */
        private val apiServiceCache = LruCache<String, Any>(10)

        /**
         * 默认的 OKHttpClient
         */
        private val defaultOkHttpClient by lazy {
            OkHttpClient.Builder()
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true).build()
        }

        /**
         * 构建默认的 Retrofit
         */
        private fun createDefaultRetrofit(baseHttpUrl: String): Retrofit {
            return Retrofit.Builder()
                .client(defaultOkHttpClient)
                .baseUrl(baseHttpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

    /**
     * 构建 ApiService
     */
    val apiService: Api by lazy {
        val key = baseHttpUrl + apiServiceClass.name
        val service = apiServiceCache.get(key) as? Api
        if (service != null) {
            service
        } else {
            val retrofit = retrofitCache.get(baseHttpUrl) ?: (createRetrofit(baseHttpUrl).apply {
                retrofitCache.put(baseHttpUrl, this)
            })
            val apiService = retrofit.create(apiServiceClass)
            apiServiceCache.put(key, apiService)
            apiService
        }
    }

    /**
     * 允许子类自己来实现创建 Retrofit 的逻辑
     * 外部无需缓存 Retrofit 实例，ReactiveHttp 内部已做好缓存处理
     * 但外部需要自己判断是否需要对 OkHttpClient 进行缓存
     * @param baseHttpUrl
     */
    protected open fun createRetrofit(baseHttpUrl: String): Retrofit {
        return createDefaultRetrofit(baseHttpUrl)
    }

    internal fun <Data> enqueueReal(
        block: suspend CoroutineScope.() -> Data,
        callback: BaseRequestCallback? = null,
        onSuccess: (suspend (Data) -> Unit)
    ): Job {
        return coroutineScope.launch(Dispatchers.Main.immediate) {
            callback?.onStart?.invoke()
            try {
                ensureActive()
                val data = coroutineScope {
                    block()
                }
                ensureActive()
                onSuccess(data)
            } catch (throwable: Throwable) {
                handleException(throwable, callback)
            } finally {
                callback?.onFinally?.invoke()
            }
        }
    }

    @Throws(ReactiveHttpException::class)
    internal suspend fun <Data> executeApi(apiFun: suspend Api.() -> IHttpWrapMode<Data>): Data {
        try {
            val response = apiFun.invoke(apiService)
            if (response.httpIsSuccess) {
                return response.httpData
            }
            throw ServerCodeBadException(response)
        } catch (throwable: Throwable) {
            throw generateException(throwable)
        }
    }

    /**
     * 如果外部想要对 Throwable 进行特殊处理，则可以重写此方法，用于改变 Exception 类型
     * 例如，在 token 失效时接口一般是会返回特定一个 httpCode 用于表明移动端需要去更新 token 了
     * 此时外部就可以实现一个 ReactiveHttpException 的子类 TokenInvalidException 并在此处返回
     * 从而做到接口异常原因强提醒的效果
     */
    protected open fun generateException(throwable: Throwable): ReactiveHttpException {
        return when (throwable) {
            is ReactiveHttpException -> {
                throwable
            }

            else -> {
                LocalBadException(cause = throwable)
            }
        }
    }

    private fun handleException(throwable: Throwable, callback: BaseRequestCallback?) {
        when (throwable) {
            is ReactiveHttpException -> {
                if (throwable.cause is CancellationException) {
                    callback?.onCancelled?.invoke()
                } else {
                    if (exceptionHandle(throwable)) {
                        callback?.onFailed?.invoke(throwable)
                        if (callback?.onFailToast?.invoke() == true) {
                            val error = exceptionFormat(throwable)
                            if (error.isNotBlank()) {
                                showToast(error)
                            }
                        }
                    }
                }
            }

            else -> {
                //非网络请求造成的异常需要抛给外部自己处理，不能静默处理
                throw throwable
            }
        }
    }

    /**
     * 用于由外部中转控制当抛出异常时是否走 onFailed 回调，当返回 true 时则回调，否则不回调
     * 同时在这里将网络请求过程中发生的异常反馈给外部，以便外部上报异常
     * @param httpException
     */
    protected open fun exceptionHandle(httpException: ReactiveHttpException): Boolean {
        return true
    }

    /**
     * 用于对 ReactiveHttpException 进行格式化，以便在请求失败时 Toast 提示错误信息
     * @param httpException
     */
    protected open fun exceptionFormat(httpException: ReactiveHttpException): String {
        return when (httpException.cause) {
            null -> {
                httpException.errorMessage
            }

            is SocketException, is SocketTimeoutException, is UnknownHostException, is SSLHandshakeException -> {
                "连接超时，请检查您的网络设置"
            }

            else -> {
                "请求过程抛出异常：" + httpException.errorMessage
            }
        }
    }

    /**
     * 通过重写此方法以便能够 showToast
     */
    abstract fun showToast(msg: String)

}