package github.leavesczy.reactivehttpsamples.core.http

import github.leavesczy.monitor.MonitorInterceptor
import github.leavesczy.reactivehttp.datasource.RemoteExtendDataSource
import github.leavesczy.reactivehttp.viewmodel.IUIAction
import github.leavesczy.reactivehttpsamples.MainApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author: leavesCZY
 * @Date: 2020/6/23 0:37
 * @Desc:
 * @Github：https://github.com/leavesCZY
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
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(FilterInterceptor())
                .addInterceptor(MonitorInterceptor(context = MainApplication.context))
            return builder.build()
        }

    }

    /**
     * 在这里来实现构建 Retrofit 的逻辑
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