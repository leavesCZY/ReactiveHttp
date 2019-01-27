package leavesc.hello.network.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import leavesc.hello.network.http.datasource.WeatherDataSource;
import leavesc.hello.network.http.repo.WeatherRepo;
import leavesc.hello.network.model.Weather;
import leavesc.hello.network.viewmodel.base.BaseViewModel;

/**
 * 作者：leavesC
 * 时间：2018/10/27 21:14
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class WeatherViewModel extends BaseViewModel {

    private MutableLiveData<Weather> weatherLiveData;

    private WeatherRepo weatherRepo;

    public WeatherViewModel() {
        weatherLiveData = new MutableLiveData<>();
        weatherRepo = new WeatherRepo(new WeatherDataSource(this));
    }

    public void queryWeather(String cityName) {
        weatherRepo.queryWeather(cityName).observe(lifecycleOwner, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                weatherLiveData.setValue(weather);
            }
        });
    }

    public MutableLiveData<Weather> getWeatherLiveData() {
        return weatherLiveData;
    }
}
