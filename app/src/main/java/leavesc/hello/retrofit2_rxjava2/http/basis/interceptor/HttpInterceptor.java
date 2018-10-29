package leavesc.hello.retrofit2_rxjava2.http.basis.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import leavesc.hello.retrofit2_rxjava2.http.basis.exception.ConnectionException;
import leavesc.hello.retrofit2_rxjava2.http.basis.exception.ForbiddenException;
import leavesc.hello.retrofit2_rxjava2.http.basis.exception.ResultInvalidException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * 作者：leavesC
 * 时间：2018/10/25 21:16
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class HttpInterceptor implements Interceptor {

    public HttpInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            throw new ConnectionException();
        }
        if (originalResponse.code() != 200) {
            if (originalResponse.code() == 404) {
                throw new ForbiddenException();
            }
            throw new ResultInvalidException();
        }
        BufferedSource source = originalResponse.body().source();
        source.request(Integer.MAX_VALUE);
        String byteString = source.buffer().snapshot().utf8();
        ResponseBody responseBody = ResponseBody.create(null, byteString);
        return originalResponse.newBuilder().body(responseBody).build();
    }

}
