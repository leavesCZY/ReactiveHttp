package leavesc.hello.retrofit2_rxjava2.http.datasource;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRemoteDataSource;
import leavesc.hello.retrofit2_rxjava2.http.basis.callback.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.datasource.base.IWeatherDataSource;
import leavesc.hello.retrofit2_rxjava2.http.service.ApiService;
import leavesc.hello.retrofit2_rxjava2.model.Weather;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2018/10/27 20:48
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class WeatherDataSource extends BaseRemoteDataSource implements IWeatherDataSource {

    public WeatherDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, RequestCallback<Weather> responseCallback) {
        execute(getService(ApiService.class).queryWeather(cityName), responseCallback);
    }

}
