package github.leavesczy.reactivehttpsamples.core.http

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @Author: leavesCZY
 * @Date: 2020/2/25 16:10
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class FilterInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val httpBuilder = originalRequest.url().newBuilder()
        httpBuilder.addEncodedQueryParameter(HttpConfig.KEY, HttpConfig.KEY_MAP)
        val requestBuilder = originalRequest.newBuilder()
            .url(httpBuilder.build())
        return chain.proceed(requestBuilder.build())
    }

}