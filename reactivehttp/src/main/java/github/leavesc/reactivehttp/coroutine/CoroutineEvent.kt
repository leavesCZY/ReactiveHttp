package github.leavesc.reactivehttp.coroutine

import kotlinx.coroutines.*

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:25
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
interface ICoroutineEvent {

    /**
     * 此字段用于声明在 BaseViewModel，BaseRemoteDataSource，BaseView 下和生命周期绑定的协程作用域
     * 推荐的做法是：
     * 1.BaseView 单独声明自己和 View 相关联的作用域
     * 2.BaseViewModel 单独声明自己和 ViewModel 相关联的作用域，因为一个 BaseViewModel 可能和多个 BaseView 相关联，所以不要把 BaseView 的 CoroutineScope 传给 BaseViewModel
     * 3.BaseRemoteDataSource 首选使用 BaseViewModel 传过来的 lifecycleCoroutineScope，因为 BaseRemoteDataSource 和 BaseViewModel 是一对一的关系
     */
    val lifecycleSupportedScope: CoroutineScope

    /**
     * 此字段用于声明在全局范围下的协程作用域，不和生命周期绑定
     */
    val globalScope: CoroutineScope
        get() = GlobalScope

    val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    val cpuDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    suspend fun <T> withNonCancellable(block: suspend CoroutineScope.() -> T): T {
        return withContext(NonCancellable, block)
    }

    suspend fun <T> withMain(block: suspend CoroutineScope.() -> T): T {
        return withContext(mainDispatcher, block)
    }

    suspend fun <T> withIO(block: suspend CoroutineScope.() -> T): T {
        return withContext(ioDispatcher, block)
    }

    suspend fun <T> withCPU(block: suspend CoroutineScope.() -> T): T {
        return withContext(cpuDispatcher, block)
    }

    /******************************不和生命周期绑定的方法******************************/

    fun launchMainG(block: suspend CoroutineScope.() -> Unit): Job {
        return globalScope.launch(context = mainDispatcher, block = block)
    }

    fun launchIOG(block: suspend CoroutineScope.() -> Unit): Job {
        return globalScope.launch(context = ioDispatcher, block = block)
    }

    fun launchCPUG(block: suspend CoroutineScope.() -> Unit): Job {
        return globalScope.launch(context = cpuDispatcher, block = block)
    }

    fun <T> asyncMainG(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return globalScope.async(context = mainDispatcher, block = block)
    }

    fun <T> asyncIOG(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return globalScope.async(context = ioDispatcher, block = block)
    }

    fun <T> asyncCPUG(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return globalScope.async(context = cpuDispatcher, block = block)
    }

    /******************************和生命周期绑定的方法******************************/

    fun launchMain(block: suspend CoroutineScope.() -> Unit): Job {
        return lifecycleSupportedScope.launch(context = mainDispatcher, block = block)
    }

    fun launchIO(block: suspend CoroutineScope.() -> Unit): Job {
        return lifecycleSupportedScope.launch(context = ioDispatcher, block = block)
    }

    fun launchCPU(block: suspend CoroutineScope.() -> Unit): Job {
        return lifecycleSupportedScope.launch(context = cpuDispatcher, block = block)
    }

    fun <T> asyncMain(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return lifecycleSupportedScope.async(context = mainDispatcher, block = block)
    }

    fun <T> asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return lifecycleSupportedScope.async(context = ioDispatcher, block = block)
    }

    fun <T> asyncCPU(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return lifecycleSupportedScope.async(context = cpuDispatcher, block = block)
    }

    /******************************扩展方法，外部不可调用******************************/

    private fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(context = mainDispatcher, block = block)
    }

    private fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(context = ioDispatcher, block = block)
    }

    private fun CoroutineScope.launchCPU(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(context = cpuDispatcher, block = block)
    }

    private fun <T> CoroutineScope.asyncMain(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(context = mainDispatcher, block = block)
    }

    private fun <T> CoroutineScope.asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(context = ioDispatcher, block = block)
    }

    private fun <T> CoroutineScope.asyncCPU(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(context = cpuDispatcher, block = block)
    }

}