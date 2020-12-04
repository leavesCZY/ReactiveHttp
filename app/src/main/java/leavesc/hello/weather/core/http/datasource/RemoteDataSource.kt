package leavesc.hello.weather.core.http.datasource

import leavesc.hello.weather.core.http.BaseRemoteDataSource
import leavesc.hello.weather.core.http.HttpConfig
import leavesc.hello.weather.core.http.IBaseViewModelEvent
import leavesc.hello.weather.core.http.RequestCallback
import leavesc.hello.weather.core.http.api.ApiService
import leavesc.hello.weather.core.model.DistrictBean
import leavesc.hello.weather.core.model.ForecastsBean

/**
 * 作者：leavesC
 * 时间：2019/5/31 14:27
 * 描述：
 */
class MapDataSource(baseViewModelEvent: IBaseViewModelEvent) : BaseRemoteDataSource(baseViewModelEvent) {

    fun getProvince(callback: RequestCallback<List<DistrictBean>>) {
        execute(getService(ApiService::class.java, HttpConfig.BASE_URL_MAP).getProvince(), callback)
    }

    fun getCity(keywords: String, callback: RequestCallback<List<DistrictBean>>) {
        execute(getService(ApiService::class.java, HttpConfig.BASE_URL_MAP).getCity(keywords), callback)
    }

    fun getCounty(keywords: String, callback: RequestCallback<List<DistrictBean>>) {
        execute(getService(ApiService::class.java, HttpConfig.BASE_URL_MAP).getCounty(keywords), callback)
    }

}

class WeatherDataSource(baseViewModelEvent: IBaseViewModelEvent) : BaseRemoteDataSource(baseViewModelEvent) {

    fun getWeather(city: String, callback: RequestCallback<List<ForecastsBean>>) {
        executeQuietly(getService(ApiService::class.java, HttpConfig.BASE_URL_MAP).getWeather(city), callback)
    }

}