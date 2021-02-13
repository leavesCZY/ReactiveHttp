package github.leavesc.reactivehttp.callback

/**
 * @Author: leavesC
 * @Date: 2020/10/28 18:16
 * @Desc: Callback
 * @GitHub：https://github.com/leavesC
 */
class RequestCallback<Data>(internal var onSuccess: ((Data) -> Unit)? = null,
                            internal var onSuccessIO: (suspend (Data) -> Unit)? = null) : BaseRequestCallback() {

    /**
     * 当网络请求成功时会调用此方法，随后会先后调用 onSuccessIO、onFinally 方法
     */
    fun onSuccess(block: (data: Data) -> Unit) {
        this.onSuccess = block
    }

    /**
     * 在 onSuccess 方法之后，onFinally 方法之前执行
     * 考虑到网络请求成功后有需要将数据保存到数据库之类的耗时需求，所以提供了此方法用于在 IO 线程进行执行
     * 注意外部不要在此处另开子线程，此方法会等到耗时任务完成后再执行 onFinally 方法
     */
    fun onSuccessIO(block: suspend (Data) -> Unit) {
        this.onSuccessIO = block
    }

}

class RequestPairCallback<DataA, DataB>(internal var onSuccess: ((dataA: DataA, dataB: DataB) -> Unit)? = null,
                                        internal var onSuccessIO: (suspend (dataA: DataA, dataB: DataB) -> Unit)? = null) : BaseRequestCallback() {

    /**
     * 当网络请求成功时会调用此方法，随后会先后调用 onSuccessIO、onFinally 方法
     */
    fun onSuccess(block: (dataA: DataA, dataB: DataB) -> Unit) {
        this.onSuccess = block
    }

    /**
     * 在 onSuccess 方法之后，onFinally 方法之前执行
     * 考虑到网络请求成功后有需要将数据保存到数据库之类的耗时需求，所以提供了此方法用于在 IO 线程进行执行
     * 注意外部不要在此处另开子线程，此方法会等到耗时任务完成后再执行 onFinally 方法
     */
    fun onSuccessIO(block: suspend (dataA: DataA, dataB: DataB) -> Unit) {
        this.onSuccessIO = block
    }

}

class RequestTripleCallback<DataA, DataB, DataC>(internal var onSuccess: ((dataA: DataA, dataB: DataB, dataC: DataC) -> Unit)? = null,
                                                 internal var onSuccessIO: (suspend (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit)? = null) : BaseRequestCallback() {

    /**
     * 当网络请求成功时会调用此方法，随后会先后调用 onSuccessIO、onFinally 方法
     */
    fun onSuccess(block: (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit) {
        this.onSuccess = block
    }

    /**
     * 在 onSuccess 方法之后，onFinally 方法之前执行
     * 考虑到网络请求成功后有需要将数据保存到数据库之类的耗时需求，所以提供了此方法用于在 IO 线程进行执行
     * 注意外部不要在此处另开子线程，此方法会等到耗时任务完成后再执行 onFinally 方法
     */
    fun onSuccessIO(block: suspend (dataA: DataA, dataB: DataB, dataC: DataC) -> Unit) {
        this.onSuccessIO = block
    }

}