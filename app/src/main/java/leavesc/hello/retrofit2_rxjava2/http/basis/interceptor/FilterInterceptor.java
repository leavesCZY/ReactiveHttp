package leavesc.hello.retrofit2_rxjava2.http.basis.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;

import leavesc.hello.retrofit2_rxjava2.http.basis.config.HttpConfig;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：leavesC
 * 时间：2018/10/28 9:31
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class FilterInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl.Builder httpBuilder = originalRequest.url().newBuilder();
        Headers headers = originalRequest.headers();
        if (headers != null && headers.size() > 0) {
            String requestType = headers.get(HttpConfig.HTTP_REQUEST_TYPE_KEY);
            if (!TextUtils.isEmpty(requestType)) {
                switch (requestType) {
                    case HttpConfig.HTTP_REQUEST_WEATHER: {
                        httpBuilder.addQueryParameter(HttpConfig.KEY, HttpConfig.KEY_WEATHER);
                        break;
                    }
                    case HttpConfig.HTTP_REQUEST_ID_CARD: {
                        httpBuilder.addQueryParameter(HttpConfig.KEY, HttpConfig.KEY_ID_CARD);
                        break;
                    }
                    case HttpConfig.HTTP_REQUEST_QR_CODE: {
                        httpBuilder.addQueryParameter(HttpConfig.KEY, HttpConfig.KEY_QR_CODE);
                        break;
                    }
                }
            }
        }
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .removeHeader(HttpConfig.HTTP_REQUEST_TYPE_KEY)
                .url(httpBuilder.build());
        return chain.proceed(requestBuilder.build());
    }

}
