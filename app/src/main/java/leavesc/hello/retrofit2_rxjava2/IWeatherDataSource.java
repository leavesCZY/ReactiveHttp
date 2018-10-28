package leavesc.hello.retrofit2_rxjava2;

import leavesc.hello.retrofit2_rxjava2.http.RequestCallback;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:10
 * 描述：
 */
public interface IWeatherDataSource {

    void queryWeather(String cityName, String key, RequestCallback<Weather> responseCallback);

}
