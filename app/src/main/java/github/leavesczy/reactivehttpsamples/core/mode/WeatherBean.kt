package github.leavesczy.reactivehttpsamples.core.mode

/**
 * @Author: leavesCZY
 * @Date: 2020/2/25 16:34
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
data class ForecastsMode(
    val city: String, val adcode: String, val province: String,
    val reporttime: String, val casts: List<CastsMode>
)

data class CastsMode(
    val date: String,
    val week: String,
    val dayweather: String,
    val nightweather: String,
    val daytemp: String,
    val nighttemp: String,
    val daywind: String,
    val nightwind: String,
    val daypower: String,
    val nightpower: String
)

data class DistrictMode(
    val adcode: String,
    val center: String,
    val level: String,
    val name: String,
    val districts: List<DistrictMode>
)