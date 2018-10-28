package leavesc.hello.retrofit2_rxjava2;

import leavesc.hello.retrofit2_rxjava2.http.BaseRemoteDataSource;
import leavesc.hello.retrofit2_rxjava2.http.BaseSubscriber;
import leavesc.hello.retrofit2_rxjava2.http.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.WeatherService;
import leavesc.hello.retrofit2_rxjava2.viewmodel.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 20:48
 * 描述：
 */
public class WeatherDataSource extends BaseRemoteDataSource implements IWeatherDataSource {

    public WeatherDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, String key, RequestCallback<Weather> responseCallback) {
        execute(getService(WeatherService.class).queryWeatherWithRxJava(cityName, key), new BaseSubscriber<>(baseViewModel, responseCallback));
    }

}
