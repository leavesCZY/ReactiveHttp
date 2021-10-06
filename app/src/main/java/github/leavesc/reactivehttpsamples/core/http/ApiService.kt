package github.leavesc.reactivehttpsamples.core.http

import github.leavesc.reactivehttpsamples.core.mode.DistrictMode
import github.leavesc.reactivehttpsamples.core.mode.ForecastsMode
import github.leavesc.reactivehttpsamples.core.mode.HttpWrapMode
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: leavesC
 * @Date: 2020/2/25 16:34
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
interface ApiService {

    @GET("config/district")
    suspend fun getProvince(): HttpWrapMode<List<DistrictMode>>

    @GET("config/district")
    suspend fun getCity(@Query("keywords") keywords: String): HttpWrapMode<List<DistrictMode>>

    @GET("config/district")
    suspend fun getCounty(@Query("keywords") keywords: String): HttpWrapMode<List<DistrictMode>>

    @GET("weather/weatherInfo?extensions=all")
    suspend fun getWeather(@Query("city") city: String): HttpWrapMode<List<ForecastsMode>>

    @GET("weather/mustFailed")
    suspend fun mustFailed(): HttpWrapMode<List<ForecastsMode>>

}