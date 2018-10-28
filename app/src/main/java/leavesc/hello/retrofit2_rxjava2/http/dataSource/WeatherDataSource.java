package leavesc.hello.retrofit2_rxjava2.http.dataSource;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRemoteDataSource;
import leavesc.hello.retrofit2_rxjava2.http.basis.BaseSubscriber;
import leavesc.hello.retrofit2_rxjava2.http.basis.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.dataSource.base.IWeatherDataSource;
import leavesc.hello.retrofit2_rxjava2.http.service.WeatherService;
import leavesc.hello.retrofit2_rxjava2.model.Weather;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

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
    public void queryWeather(String cityName, RequestCallback<Weather> responseCallback) {
        execute(getService(WeatherService.class).queryWeather(cityName),
                new BaseSubscriber<>(baseViewModel, responseCallback));
    }

}
