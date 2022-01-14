package github.leavesczy.reactivehttpsamples.core.viewmodel

import androidx.lifecycle.MutableLiveData
import github.leavesczy.reactivehttpsamples.base.BaseViewModel
import github.leavesczy.reactivehttpsamples.core.mode.ForecastsMode

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @Github：https://github.com/leavesCZY
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