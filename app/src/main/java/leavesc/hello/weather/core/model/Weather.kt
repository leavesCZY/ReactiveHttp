package leavesc.hello.weather.core.model

/**
 * 作者：leavesC
 * 时间：2019/6/7 19:29
 * 描述：
 * GitHub：https://github.com/leavesC
 */
data class ForecastsBean(
    val city: String, val adcode: String, val province: String,
    val reporttime: String, val casts: List<CastsBean>
)

data class CastsBean(
    val date: String, val week: String, val dayweather: String, val nightweather: String, val daytemp: String,
    val nighttemp: String, val daywind: String, val nightwind: String, val daypower: String, val nightpower: String
)