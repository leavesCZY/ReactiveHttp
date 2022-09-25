package github.leavesczy.reactivehttpsamples.core.http

import android.widget.Toast
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import github.leavesczy.reactivehttp.RemoteDataSource
import github.leavesczy.reactivehttpsamples.MainApplication
import kotlinx.coroutines.CoroutineScope
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
class AppRemoteDataSource(coroutineScope: CoroutineScope) : RemoteDataSource<ApiService>(
    coroutineScope = coroutineScope,
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
                .addInterceptor(
                    ChuckerInterceptor.Builder(MainApplication.context)
                        .collector(ChuckerCollector(MainApplication.context))
                        .maxContentLength(250000L)
                        .build()
                )
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

    override fun showToast(msg: String) {
        Toast.makeText(MainApplication.context, msg, Toast.LENGTH_SHORT).show()
    }

}