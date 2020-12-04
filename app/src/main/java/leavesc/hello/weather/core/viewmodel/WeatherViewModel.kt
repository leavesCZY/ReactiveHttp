package leavesc.hello.weather.core.viewmodel

import androidx.lifecycle.MutableLiveData
import leavesc.hello.weather.core.http.RequestCallback
import leavesc.hello.weather.core.http.datasource.WeatherDataSource
import leavesc.hello.weather.core.http.viewmodel.BaseViewModel
import leavesc.hello.weather.core.model.ForecastsBean

/**
 * 作者：leavesC
 * 时间：2019/6/7 21:13
 * 描述：
 * GitHub：https://github.com/leavesC
 */

class WeatherViewModel : BaseViewModel() {

    private val weatherDataSource = WeatherDataSource(this)

    val forecastsBeanLiveData = MutableLiveData<ForecastsBean>()

    fun getWeather(city: String) {
        weatherDataSource.getWeather(city, object : RequestCallback<List<ForecastsBean>> {
            override fun onSuccess(data: List<ForecastsBean>) {
                if (data.isNotEmpty()) {
                    forecastsBeanLiveData.value = data[0]
                }
            }
        })
    }

}