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
     * 在显示 Loading 之后且开始网络请求之前执行
     */
    internal var onStart: (() -> Unit)? = null

    /**
     * 如果外部主动取消了网络请求，不会回调 onFail，而是回调此方法，随后回调 onFinally
     * 但如果当取消网络请求时已回调了 onSuccess / onSuccessIO 方法，则不会回调此方法
     */
    internal var onCancelled: (() -> Unit)? = null

    /**
     * 当网络请求失败时会调用此方法，在 onFinally 被调用之前执行
     */
    internal var onFailed: ((ReactiveHttpException) -> Unit)? = null

    /**
     * 用于控制是否当网络请求失败时 Toast 失败原因
     * 默认为 true，即进行 Toast 提示
     */
    internal var onFailToast: (() -> Boolean) = { true }

    /**
     * 在网络请求结束之后（不管请求成功与否）且隐藏 Loading 之前执行
     */
    internal var onFinally: (() -> Unit)? = null

    fun onStart(block: () -> Unit) {
        this.onStart = block
    }

    fun onCancelled(block: () -> Unit) {
        this.onCancelled = block
    }

    fun onFailed(block: (ReactiveHttpException) -> Unit) {
        this.onFailed = block
    }

    fun onFailToast(block: () -> Boolean) {
        this.onFailToast = block
    }

    fun onFinally(block: () -> Unit) {
        this.onFinally = block
    }

}

class RequestCallback<Data> : BaseRequestCallback() {

    /**
     * 当网络请求成功时会调用此方法，随后会先后调用 onSuccessIO、onFinally 方法
     */
    internal var onSuccess: ((Data) -> Unit)? = null

    /**
     * 在 onSuccess 方法之后，onFinally 方法之前执行
     * 考虑到网络请求成功后有需要将数据保存到数据库之类的耗时需求，所以提供了此方法用于在 IO 线程进行执行
     * 注意外部不要在此处另开子线程，此方法会等到耗时任务完成后再执行 onFinally 方法
     */
    internal var onSuccessIO: (suspend (Data) -> Unit)? = null

    fun onSuccess(block: (data: Data) -> Unit) {
        this.onSuccess = block
    }

    fun onSuccessIO(block: suspend (Data) -> Unit) {
        this.onSuccessIO = block
    }

}

class RequestPairCallback<DataA, DataB> : BaseRequestCallback() {

    internal var onSuccess: ((dataA: DataA, dataB: DataB) -> Unit)? = null

    internal var onSuccessIO: (suspend (dataA: DataA, dataB: DataB) -> Unit)? = null

    fun onSuccess(block: (dataA: DataA, dataB: DataB) -> Unit) {
        this.onSuccess = block
    }

    fun onSuccessIO(block: suspend (dataA: DataA, dataB: DataB) -> Unit) {
        this.onSuccessIO = block
    }

}

class RequestTripleCallback<DataA, DataB, DataC> : BaseRequestCallback() {

    internal var onSuccess: ((dataA: DataA, dataB: DataB, dataC: DataC) -> Unit)? = null

    internal var onSuccessIO: (suspend (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit)? = null

    fun onSuccess(block: (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit) {
        this.onSuccess = block
    }

    fun onSuccessIO(block: suspend (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit) {
        this.onSuccessIO = block
    }

}