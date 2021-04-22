package github.leavesc.reactivehttp.exception

import github.leavesc.reactivehttp.bean.IHttpWrapBean

/**
 * @Author: leavesC
 * @Date: 2020/10/22 10:37
 * @Desc: Exception
 * @GitHub：https://github.com/leavesC
 */
/**
 * @param errorCode        服务器返回的错误码 或者是 HttpConfig 中定义的本地错误码
 * @param errorMessage     服务器返回的异常信息 或者是 请求过程中抛出的信息，是最原始的异常信息
 * @param realException    用于当 code 是本地错误码时，存储真实的运行时异常
 */
open class BaseHttpException(
    val errorCode: Int,
    val errorMessage: String,
    val realException: Throwable?
) : Exception(errorMessage) {

    companion object {

        /**
         * 此变量用于表示在网络请求过程过程中抛出了异常
         */
        const val CODE_ERROR_LOCAL_UNKNOWN = -1024520

    }

    /**
     * 是否是由于服务器返回的 code != successCode 导致的异常
     */
    val isServerCodeBadException: Boolean
        get() = this is ServerCodeBadException

    /**
     * 是否是由于网络请求过程中抛出的异常（例如：服务器返回的 Json 解析失败）
     */
    val isLocalBadException: Boolean
        get() = this is LocalBadException

}

/**
 * API 请求成功了，但 code != successCode
 * @param errorCode
 * @param errorMessage
 */
class ServerCodeBadException(errorCode: Int, errorMessage: String) :
    BaseHttpException(errorCode, errorMessage, null) {

    constructor(bean: IHttpWrapBean<*>) : this(bean.httpCode, bean.httpMsg)

}

/**
 * 请求过程抛出异常
 * @param throwable
 */
class LocalBadException(throwable: Throwable) : BaseHttpException(
    CODE_ERROR_LOCAL_UNKNOWN, throwable.message
        ?: "", throwable
)