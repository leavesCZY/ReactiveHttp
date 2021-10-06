package github.leavesc.reactivehttpsamples.core.viewmodel

import androidx.lifecycle.MutableLiveData
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import github.leavesc.reactivehttpsamples.core.mode.ForecastsMode

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class WeatherViewModel : BaseViewModel() {

    val forecastsModeLiveData = MutableLiveData<ForecastsMode>()

    fun getWeather(city: String) {
        remoteDataSource.enqueue({
            getWeather(city)
        }) {
            onSuccess {
                if (it.isNotEmpty()) {
                    forecastsModeLiveData.value = it[0]
                }
            }
        }
    }

}