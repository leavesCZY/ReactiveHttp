package github.leavesc.reactivehttp.callback

import github.leavesc.reactivehttp.exception.ReactiveHttpException

/**
 * @Author: leavesC
 * @Date: 2021/11/28 23:10
 * @Desc:
 * @Github：https://github.com/leavesC
 */
open class BaseRequestCallback {

    /**
     * 在显示 showLoading 后且开始网络请求之前执行
     * 当网络请求结束后，不管成功与否，就会马上 dismissLoading
     */
    internal var onStart: (() -> Unit)? = null

    /**
     * 用于控制是否当网络请求失败时 Toast 失败原因
     * 默认为 true，即进行 Toast 提示
     */
    internal var onFailToast: (() -> Boolean) = { true }

    /**
     * 当网络请求失败时会调用此方法
     */
    internal var onFailed: ((ReactiveHttpException) -> Unit)? = null

    /**
     * 当网络请求被取消时会回调此方法
     */
    internal var onCancelled: (() -> Unit)? = null

    /**
     * 在网络请求结束之后（不管网络请求是否有被取消、成功与否），是最后一个被回调的方法
     */
    internal var onFinally: (() -> Unit)? = null

    fun onStart(block: () -> Unit) {
        this.onStart = block
    }

    fun onFailed(block: (ReactiveHttpException) -> Unit) {
        this.onFailed = block
    }

    fun onFailToast(block: () -> Boolean) {
        this.onFailToast = block
    }

    fun onCancelled(block: () -> Unit) {
        this.onCancelled = block
    }

    fun onFinally(block: () -> Unit) {
        this.onFinally = block
    }

}

class RequestCallback<Data> : BaseRequestCallback() {

    /**
     * 当网络请求成功时会调用此 suspend 方法，可用于执行 suspend 函数
     */
    internal var onSuccess: (suspend (Data) -> Unit)? = null

    fun onSuccess(block: suspend (data: Data) -> Unit) {
        this.onSuccess = block
    }

}

class RequestPairCallback<DataA, DataB> : BaseRequestCallback() {

    internal var onSuccess: (suspend (dataA: DataA, dataB: DataB) -> Unit)? = null

    fun onSuccess(block: suspend (dataA: DataA, dataB: DataB) -> Unit) {
        this.onSuccess = block
    }

}

class RequestTripleCallback<DataA, DataB, DataC> : BaseRequestCallback() {

    internal var onSuccess: (suspend (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit)? = null

    fun onSuccess(block: suspend (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit) {
        this.onSuccess = block
    }

}