package leavesc.hello.weather.core.http.model

import com.google.gson.annotations.SerializedName

/**
 * 作者：leavesC
 * 时间：2019/5/31 10:58
 * 描述：
 */
class BaseResponse<T>(
    @SerializedName("status") var code: Int = 0,
    @SerializedName("info") var message: String? = null,
    @SerializedName("districts", alternate = ["forecasts"]) var data: T
)

class OptionT<T>(val value: T)