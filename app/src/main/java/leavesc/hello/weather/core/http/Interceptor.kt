package leavesc.hello.weather.core.http

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 作者：leavesC
 * 时间：2019/11/6 18:20
 * 描述：
 */
class FilterInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val httpBuilder = originalRequest.url.newBuilder()
        httpBuilder.addEncodedQueryParameter(HttpConfig.KEY, HttpConfig.KEY_MAP)
        val requestBuilder = originalRequest.newBuilder()
                .url(httpBuilder.build())
        return chain.proceed(requestBuilder.build())
    }

}