package github.leavesczy.reactivehttp

/**
 * @Author: leavesCZY
 * @Date: 2020/10/22 10:37
 * @Desc: Exception
 * @Github：https://github.com/leavesCZY
 */
/**
 * @param errorCode 服务器返回的错误码 或者是 HttpConfig 中定义的本地错误码
 * @param errorMessage 服务器返回的异常信息 或者是 请求过程中抛出的信息，是最原始的异常信息
 * @param cause 用于当 code 是本地错误码时，存储真实的运行时异常
 */
open class ReactiveHttpException(
    val errorCode: Int,
    val errorMessage: String,
    cause: Throwable?
) : Exception(errorMessage, cause) {

    companion object {

        /**
         * 此变量用于表示在网络请求过程过程中抛出了异常，例如：服务器返回的 Json 解析失败、网络请求被取消
         */
        const val CODE_ERROR_LOCAL_UNKNOWN = -338751699

    }

    /**
     * 是否是由于服务器返回的 code != successCode 导致的异常
     */
    val isServerCodeBadException: Boolean
        get() = this is ServerCodeBadException

    /**
     * 是否是由于网络请求过程中抛出的异常
     */
    val isLocalBadException: Boolean
        get() = this is LocalBadException

}

/**
 * 网络请求成功了，但 code != successCode
 * @param errorCode
 * @param errorMessage
 */
class ServerCodeBadException(errorCode: Int, errorMessage: String) :
    ReactiveHttpException(errorCode = errorCode, errorMessage = errorMessage, cause = null) {

    constructor(mode: IHttpWrapMode<*>) : this(mode.httpCode, mode.httpMsg)

}

/**
 * 请求过程抛出异常
 * @param cause
 */
class LocalBadException(cause: Throwable) :
    ReactiveHttpException(
        errorCode = CODE_ERROR_LOCAL_UNKNOWN,
        errorMessage = cause.message ?: "",
        cause = cause
    )