package leavesc.hello.weather.core.http

import leavesc.hello.monitor.MonitorInterceptor
import leavesc.hello.weather.core.holder.ContextHolder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * 作者：leavesC
 * 时间：2019/5/31 11:18
 * 描述：
 */
class RetrofitManagement private constructor() {

    companion object {

        private const val READ_TIMEOUT: Long = 10000

        private const val WRITE_TIMEOUT: Long = 10000

        private const val CONNECT_TIMEOUT: Long = 10000

        val instance: RetrofitManagement by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManagement()
        }

    }

    private val serviceMap = ConcurrentHashMap<String, Any>()

    private fun createRetrofit(url: String): Retrofit {
        val builder = OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
        //这是我的另外一个开源库：https://github.com/leavesC/Monitor
        builder.addInterceptor(MonitorInterceptor(ContextHolder.context))
        builder.addInterceptor(FilterInterceptor())
        val client = builder.build()
        return Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun <T : Any> getService(clz: Class<T>, host: String): T {
        if (serviceMap.containsKey(host)) {
            val obj = serviceMap[host] as? T
            obj?.let {
                return obj
            }
        }
        val value = createRetrofit(host).create(clz)
        serviceMap[host] = value
        return value
    }

}