package leavesc.hello.retrofit2_rxjava2.http.service;

import io.reactivex.Observable;
import leavesc.hello.retrofit2_rxjava2.http.basis.config.HttpConfig;
import leavesc.hello.retrofit2_rxjava2.http.basis.model.BaseResponseBody;
import leavesc.hello.retrofit2_rxjava2.model.IDCard;
import leavesc.hello.retrofit2_rxjava2.model.QrCode;
import leavesc.hello.retrofit2_rxjava2.model.Weather;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 作者：leavesC
 * 时间：2018/10/28 13:13
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public interface ApiService {

    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_ID_CARD})
    @GET("idcard/index")
    Observable<BaseResponseBody<IDCard>> queryIDCard(@Query("cardno") String cardNo);

    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_WEATHER})
    @GET("onebox/weather/query")
    Observable<BaseResponseBody<Weather>> queryWeather(@Query("cityname") String cityName);

    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_QR_CODE})
    @GET("qrcode/api")
    Observable<BaseResponseBody<QrCode>> createQrCode(@Query("text") String text, @Query("w") int width);

}
