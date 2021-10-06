package github.leavesc.reactivehttpsamples.core.mode

import com.google.gson.annotations.SerializedName
import github.leavesc.reactivehttp.mode.IHttpWrapMode
import github.leavesc.reactivehttpsamples.core.http.HttpConfig

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:22
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class HttpWrapMode<T>(
    @SerializedName("status") var code: Int = 0,
    @SerializedName("info") var message: String? = null,
    @SerializedName("districts", alternate = ["forecasts"]) var data: T
) : IHttpWrapMode<T> {

    override val httpCode: Int
        get() = code

    override val httpMsg: String
        get() = message ?: ""

    override val httpData: T
        get() = data

    override val httpIsSuccess: Boolean
        get() = code == HttpConfig.CODE_SERVER_SUCCESS || message == "OK"

    override fun toString(): String {
        return "HttpResBean(code=$code, message=$message, data=$data)"
    }

}