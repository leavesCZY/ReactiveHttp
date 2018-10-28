package leavesc.hello.retrofit2_rxjava2;

import android.arch.lifecycle.MutableLiveData;

import leavesc.hello.retrofit2_rxjava2.http.BaseRepo;
import leavesc.hello.retrofit2_rxjava2.http.RequestCallback;
import leavesc.hello.retrofit2_rxjava2.viewmodel.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/27 21:12
 * 描述：
 */
public class WeatherRepo extends BaseRepo<IWeatherDataSource> {

    public WeatherRepo(IWeatherDataSource remoteDataSource, BaseViewModel baseViewModel) {
        super(remoteDataSource, baseViewModel);
    }

    public MutableLiveData<Weather> queryWeather(String cityName, String key) {
        MutableLiveData<Weather> weatherMutableLiveData = new MutableLiveData<>();
        remoteDataSource.queryWeather(cityName, key, new RequestCallback<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                weatherMutableLiveData.setValue(weather);
            }
        });
        return weatherMutableLiveData;
    }

}
