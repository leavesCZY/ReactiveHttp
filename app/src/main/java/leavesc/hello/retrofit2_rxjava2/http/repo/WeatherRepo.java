package leavesc.hello.retrofit2_rxjava2.http.repo;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.retrofit2_rxjava2.http.basis.BaseRepo;
import leavesc.hello.retrofit2_rxjava2.http.basis.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.http.dataSource.base.IWeatherDataSource;
import leavesc.hello.retrofit2_rxjava2.model.Weather;
import leavesc.hello.retrofit2_rxjava2.viewmodel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:12
 * 描述：
 */
public class WeatherRepo extends BaseRepo<IWeatherDataSource> {

    public WeatherRepo(IWeatherDataSource remoteDataSource, BaseViewModel baseViewModel) {
        super(remoteDataSource, baseViewModel);
    }

    public MutableLiveData<Weather> queryWeather(String cityName) {
        MutableLiveData<Weather> weatherMutableLiveData = new MutableLiveData<>();
        remoteDataSource.queryWeather(cityName, new RequestCallback<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                weatherMutableLiveData.setValue(weather);
            }
        });
        return weatherMutableLiveData;
    }

}
