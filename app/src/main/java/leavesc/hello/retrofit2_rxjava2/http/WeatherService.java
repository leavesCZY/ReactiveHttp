package leavesc.hello.retrofit2_rxjava2.http;

import io.reactivex.Observable;
import leavesc.hello.retrofit2_rxjava2.Weather;
import leavesc.hello.retrofit2_rxjava2.http.model.BaseResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：叶应是叶
 * 时间：2018/10/24 23:53
 * 描述：
 */
public interface WeatherService {

    @GET("onebox/weather/query")
    Call<BaseResponseBody<Weather>> queryWeather(@Query("cityname") String cityName, @Query("key") String key);

    @GET("onebox/weather/query")
    Observable<BaseResponseBody<Weather>> queryWeatherWithRxJava(@Query("cityname") String cityName, @Query("key") String key);

}
