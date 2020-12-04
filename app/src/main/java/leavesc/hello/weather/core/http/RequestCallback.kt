package leavesc.hello.weather.core.http

/**
 * 作者：leavesC
 * 时间：2019/5/31 10:47
 * 描述：
 */
interface RequestCallback<T> {

    fun onSuccess(data: T)

}

interface RequestMultiplyCallback<T> : RequestCallback<T> {

    fun onFail(exception: BaseException)

}

interface RequestMultiplyToastCallback<T> : RequestMultiplyCallback<T>