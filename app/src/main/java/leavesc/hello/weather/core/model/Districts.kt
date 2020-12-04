package leavesc.hello.weather.core.model

/**
 * 作者：leavesC
 * 时间：2019/5/31 20:09
 * 描述：
 */
data class DistrictBean(
    val adcode: String,
    val center: String,
    val level: String,
    val name: String,
    val districts: List<DistrictBean>
)