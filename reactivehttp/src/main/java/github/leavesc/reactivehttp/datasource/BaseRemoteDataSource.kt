package github.leavesc.reactivehttp.datasource

import android.util.LruCache
import github.leavesc.reactivehttp.callback.BaseRequestCallback
import github.leavesc.reactivehttp.coroutine.ICoroutineEvent
import github.leavesc.reactivehttp.exception.BaseHttpException
import github.leavesc.reactivehttp.exception.LocalBadException
import github.leavesc.reactivehttp.viewmodel.DismissLoadingEvent
import github.leavesc.reactivehttp.viewmodel.IViewModelActionEvent
import github.leavesc.reactivehttp.viewmodel.ShowLoadingEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * @Author: leavesC
 * @Date: 2020/5/4 0:56
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
abstract class BaseRemoteDataSource<Api : Any>(protected val iViewModelActionEvent: IViewModelActionEvent?, protected val apiServiceClass: Class<Api>) : ICoroutineEvent {

    companion object {

        /**
         * ApiService 缓存
         */
        private val apiServiceCache = LruCache<String, Any>(30)

        /**
         * Retrofit 缓存
         */
        private val retrofitCache = LruCache<String, Retrofit>(3)

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
        private fun createDefaultRetrofit(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                    .client(defaultOkHttpClient)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

    }

    /**
     * 和生命周期绑定的协程作用域
     */
    override val lifecycleSupportedScope = iViewModelActionEvent?.lifecycleSupportedScope ?: GlobalScope

    /**
     * 由子类实现此字段以便获取 baseUrl
     */
    protected abstract val baseUrl: String

    /**
     * 允许子类自己来实现创建 Retrofit 的逻辑
     * 外部无需缓存 Retrofit 实例，ReactiveHttp 内部已做好缓存处理
     * 但外部需要自己判断是否需要对 OKHttpClient 进行缓存
     * @param baseUrl
     */
    protected open fun createRetrofit(baseUrl: String): Retrofit {
        return createDefaultRetrofit(baseUrl)
    }

    protected open fun generateBaseUrl(baseUrl: String): String {
        if (baseUrl.isNotBlank()) {
            return baseUrl
        }
        return this.baseUrl
    }

    fun getApiService(baseUrl: String = ""): Api {
        return getApiService(generateBaseUrl(baseUrl), apiServiceClass)
    }

    private fun getApiService(baseUrl: String, apiServiceClazz: Class<Api>): Api {
        val key = baseUrl + apiServiceClazz.canonicalName
        val get = apiServiceCache.get(key)?.let {
            it as? Api
        }
        if (get != null) {
            return get
        }
        val retrofit = retrofitCache.get(baseUrl) ?: (createRetrofit(baseUrl).apply {
            retrofitCache.put(baseUrl, this)
        })
        val apiService = retrofit.create(apiServiceClazz)
        apiServiceCache.put(key, apiService)
        return apiService
    }

    protected fun handleException(throwable: Throwable, callback: BaseRequestCallback?) {
        if (callback == null) {
            return
        }
        if (throwable is CancellationException) {
            callback.onCancelled?.invoke()
            return
        }
        val exception = generateBaseExceptionReal(throwable)
        if (exceptionHandle(exception)) {
            callback.onFailed?.invoke(exception)
            if (callback.onFailToast()) {
                val error = exceptionFormat(exception)
                if (error.isNotBlank()) {
                    showToast(error)
                }
            }
        }
    }

    internal fun generateBaseExceptionReal(throwable: Throwable): BaseHttpException {
        return generateBaseException(throwable).apply {
            exceptionRecord(this)
        }
    }

    /**
     * 如果外部想要对 Throwable 进行特殊处理，则可以重写此方法，用于改变 Exception 类型
     * 例如，在 token 失效时接口一般是会返回特定一个 httpCode 用于表明移动端需要去更新 token 了
     * 此时外部就可以实现一个 BaseException 的子类 TokenInvalidException 并在此处返回
     * 从而做到接口异常原因强提醒的效果，而不用去纠结 httpCode 到底是多少
     */
    protected open fun generateBaseException(throwable: Throwable): BaseHttpException {
        return if (throwable is BaseHttpException) {
            throwable
        } else {
            LocalBadException(throwable)
        }
    }

    /**
     * 用于由外部中转控制当抛出异常时是否走 onFail 回调，当返回 true 时则回调，否则不回调
     * @param httpException
     */
    protected open fun exceptionHandle(httpException: BaseHttpException): Boolean {
        return true
    }

    /**
     * 用于将网络请求过程中的异常反馈给外部，以便记录
     * @param throwable
     */
    protected open fun exceptionRecord(throwable: Throwable) {

    }

    /**
     * 用于对 BaseException 进行格式化，以便在请求失败时 Toast 提示错误信息
     * @param httpException
     */
    protected open fun exceptionFormat(httpException: BaseHttpException): String {
        return when (httpException.realException) {
            null -> {
                httpException.errorMessage
            }
            is ConnectException, is SocketTimeoutException, is UnknownHostException -> {
                "连接超时，请检查您的网络设置"
            }
            else -> {
                "请求过程抛出异常：" + httpException.errorMessage
            }
        }
    }

    protected fun showLoading(job: Job?) {
        iViewModelActionEvent?.let {
           it.showLoadingEventLD.value = ShowLoadingEvent(job)
        }
    }

    protected fun dismissLoading() {
        iViewModelActionEvent?.let {
            it.dismissLoadingEventLD.value = DismissLoadingEvent
        }
    }

    abstract fun showToast(msg: String)

}