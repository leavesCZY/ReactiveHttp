package github.leavesc.reactivehttpsamples.core.http

import github.leavesc.monitor.MonitorInterceptor
import github.leavesc.reactivehttp.datasource.RemoteExtendDataSource
import github.leavesc.reactivehttp.viewmodel.IUIAction
import github.leavesc.reactivehttpsamples.MainApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author: leavesC
 * @Date: 2020/6/23 0:37
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class AppRemoteDataSource(iuiAction: IUIAction?) : RemoteExtendDataSource<ApiService>(
    uiAction = iuiAction,
    baseHttpUrl = HttpConfig.BASE_URL_MAP,
    apiServiceClass = ApiService::class.java
) {

    companion object {

        private val okHttpClient: OkHttpClient by lazy {
            createOkHttpClient()
        }

        private fun createOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .readTimeout(1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(1000L, TimeUnit.MILLISECONDS)
                .connectTimeout(1000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(FilterInterceptor())
                .addInterceptor(MonitorInterceptor(MainApplication.context))
            return builder.build()
        }

    }

    /**
     * 外部在这里来实现构建 Retrofit 的逻辑
     * @param baseHttpUrl
     */
    override fun createRetrofit(baseHttpUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseHttpUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}