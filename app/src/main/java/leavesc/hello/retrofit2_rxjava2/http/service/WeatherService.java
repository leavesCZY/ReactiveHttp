package leavesc.hello.retrofit2_rxjava2.http.service;

import io.reactivex.Observable;
import leavesc.hello.retrofit2_rxjava2.http.basis.HttpConfig;
import leavesc.hello.retrofit2_rxjava2.model.Weather;
import leavesc.hello.retrofit2_rxjava2.http.basis.model.BaseResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 作者：叶应是叶
 * 时间：2018/10/24 23:53
 * 描述：
 */
public interface WeatherService {

    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_WEATHER})
    @GET("onebox/weather/query")
    Observable<BaseResponseBody<Weather>> queryWeather(@Query("cityname") String cityName);

}
