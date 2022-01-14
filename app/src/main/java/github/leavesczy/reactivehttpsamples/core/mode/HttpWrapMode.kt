package github.leavesczy.reactivehttpsamples.core.mode

import github.leavesczy.reactivehttp.mode.IHttpWrapMode

/**
 * @Author: leavesCZY
 * @Date: 2020/4/30 15:22
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
data class DistrictsWrapMode<T>(
    val infocode: Int = 0,
    val info: String? = null,
    val districts: T
) : IHttpWrapMode<T> {

    override val httpCode: Int
        get() = infocode

    override val httpMsg: String
        get() = info ?: ""

    override val httpData: T
        get() = districts

    override val httpIsSuccess: Boolean
        get() = infocode == 10000

}

class ForecastsWrapMode<T>(
    val infocode: Int = 0,
    val info: String? = null,
    val forecasts: T
) : IHttpWrapMode<T> {

    override val httpCode: Int
        get() = infocode

    override val httpMsg: String
        get() = info ?: ""

    override val httpData: T
        get() = forecasts

    override val httpIsSuccess: Boolean
        get() = infocode == 10000

}