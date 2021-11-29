package github.leavesc.reactivehttpsamples.core.mode

import com.google.gson.annotations.SerializedName
import github.leavesc.reactivehttp.mode.IHttpWrapMode

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:22
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class HttpWrapMode<T>(
    var infocode: Int = 0,
    var info: String? = null,
    @SerializedName("districts", alternate = ["forecasts"]) var data: T
) : IHttpWrapMode<T> {

    override val httpCode: Int
        get() = infocode

    override val httpMsg: String
        get() = info ?: ""

    override val httpData: T
        get() = data

    override val httpIsSuccess: Boolean
        get() = infocode == 10000

    override fun toString(): String {
        return "HttpResBean(code=$infocode, message=$info, data=$data)"
    }

}