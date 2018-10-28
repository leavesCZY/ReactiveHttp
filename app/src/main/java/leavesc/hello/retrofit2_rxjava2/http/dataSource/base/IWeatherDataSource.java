package leavesc.hello.retrofit2_rxjava2.http.dataSource.base;

import leavesc.hello.retrofit2_rxjava2.http.basis.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.model.Weather;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:10
 * 描述：
 */
public interface IWeatherDataSource {

    void queryWeather(String cityName, RequestCallback<Weather> callback);

}
