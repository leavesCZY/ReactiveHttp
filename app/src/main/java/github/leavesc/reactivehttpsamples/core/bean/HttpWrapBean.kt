package github.leavesc.reactivehttpsamples.core.bean

import com.google.gson.annotations.SerializedName
import github.leavesc.reactivehttp.bean.IHttpWrapBean
import github.leavesc.reactivehttpsamples.core.http.HttpConfig

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:22
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class HttpWrapBean<T>(
    @SerializedName("status") var code: Int = 0,
    @SerializedName("info") var message: String? = null,
    @SerializedName("districts", alternate = ["forecasts"]) var data: T
) : IHttpWrapBean<T> {

    companion object {

        fun <T> success(data: T): HttpWrapBean<T> {
            return HttpWrapBean(HttpConfig.CODE_SERVER_SUCCESS, "success", data)
        }

        fun <T> failed(data: T): HttpWrapBean<T> {
            return HttpWrapBean(-200, "服务器停止维护了~~", data)
        }

    }

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