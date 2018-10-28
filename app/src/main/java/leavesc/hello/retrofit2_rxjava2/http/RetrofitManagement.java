package leavesc.hello.retrofit2_rxjava2.http;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import leavesc.hello.retrofit2_rxjava2.BaseApplication;
import leavesc.hello.retrofit2_rxjava2.BuildConfig;
import leavesc.hello.retrofit2_rxjava2.http.exception.AccountInvalidException;
import leavesc.hello.retrofit2_rxjava2.http.exception.ServerResultException;
import leavesc.hello.retrofit2_rxjava2.http.exception.TokenInvalidException;
import leavesc.hello.retrofit2_rxjava2.http.interceptor.HeaderInterceptor;
import leavesc.hello.retrofit2_rxjava2.http.interceptor.HttpInterceptor;
import leavesc.hello.retrofit2_rxjava2.http.model.BaseResponseBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：叶应是叶
 * 时间：2018/10/26 23:11
 * 描述：
 */
public class RetrofitManagement {

    public static final String BASE_URL = "http://op.juhe.cn/";

    private static final long READ_TIMEOUT = 6000;

    private static final long WRITE_TIMEOUT = 6000;

    private static final long CONNECT_TIMEOUT = 6000;

    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private RetrofitManagement() {

    }

    public static RetrofitManagement getInstance() {
        return RetrofitHolder.retrofitManagement;
    }

    private static class RetrofitHolder {
        private static final RetrofitManagement retrofitManagement = new RetrofitManagement();
    }

    private Retrofit createRetrofit(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
            builder.addInterceptor(new ChuckInterceptor(BaseApplication.getAppContext()));
        }
        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> ObservableTransformer<BaseResponseBody<T>, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    switch (result.getCode()) {
                        case HttpCode.CODE_SUCCESS: {
                            return createData(result.getData());
                        }
                        case HttpCode.CODE_TOKEN_INVALID: {
                            throw new TokenInvalidException();
                        }
                        case HttpCode.CODE_ACCOUNT_INVALID: {
                            throw new AccountInvalidException();
                        }
                        default: {
                            throw new ServerResultException(result.getCode(), result.getMsg());
                        }
                    }
                });
    }


    private <T> Observable<T> createData(T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    public <T> T getService(Class<T> clz) {
        return getService(clz, BASE_URL);
    }

    public <T> T getService(Class<T> clz, String host) {
        T value;
        if (serviceMap.containsKey(host)) {
            Object obj = serviceMap.get(host);
            if (obj == null) {
                value = createRetrofit(host).create(clz);
                serviceMap.put(host, value);
            } else {
                value = (T) obj;
            }
        } else {
            value = createRetrofit(host).create(clz);
            serviceMap.put(host, value);
        }
        return value;
    }

}