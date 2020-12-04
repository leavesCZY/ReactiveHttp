package leavesc.hello.weather.core.http.api

import io.reactivex.Observable
import leavesc.hello.weather.core.http.model.BaseResponse
import leavesc.hello.weather.core.model.DistrictBean
import leavesc.hello.weather.core.model.ForecastsBean
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 作者：leavesC
 * 时间：2019/5/31 11:17
 * 描述：
 */
interface ApiService {

    @GET("config/district")
    fun getProvince(): Observable<BaseResponse<List<DistrictBean>>>

    @GET("config/district")
    fun getCity(@Query("keywords") keywords: String): Observable<BaseResponse<List<DistrictBean>>>

    @GET("config/district")
    fun getCounty(@Query("keywords") keywords: String): Observable<BaseResponse<List<DistrictBean>>>

    @GET("weather/weatherInfo?extensions=all")
    fun getWeather(@Query("city") city: String): Observable<BaseResponse<List<ForecastsBean>>>

}