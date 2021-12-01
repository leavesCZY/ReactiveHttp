package github.leavesc.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:08
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class SingleRequestViewModel : BaseViewModel() {

    val logLiveData = MutableLiveData<String>()

    private var enqueueJob: Job? = null

    /**
     * 异步请求
     * 在 onSuccess 回调中直接拿到 HttpWrapMode 中的 Data
     */
    fun enqueue() {
        cancelEnqueueJob()
        enqueueJob = remoteDataSource.enqueue({
            val result = getProvince()
            //主动延迟一点时间，以便来得及取消网络请求
            delay(1500)
            result
        }) {
            /**
             * 在显示 showLoading 后且开始网络请求之前执行
             * 当网络请求结束后，不管成功与否，就会马上 dismissLoading
             */
            onStart {
                log("onStart")
            }
            /**
             * 当网络请求成功时会调用此 suspend 方法，可用于执行 suspend 函数
             */
            onSuccess { response ->
                withContext(Dispatchers.IO) {
                    repeat(5) {
                        delay(100)
                        log("onSuccess delay: $it")
                    }
                }
                log("onSuccess response: ${toPrettyJson(response)}")
            }
            /**
             * 用于控制是否当网络请求失败时 Toast 失败原因
             * 默认为 true，即进行 Toast 提示
             */
            onFailToast {
                true
            }
            /**
             * 当网络请求失败时会调用此方法
             */
            onFailed {
                log("onFailed: $it")
            }
            /**
             * 当网络请求被取消时会回调此方法
             */
            onCancelled {
                log("onCancelled")
            }
            /**
             * 在网络请求结束之后（不管网络请求是否有被取消、成功与否），是最后一个被回调的方法
             */
            onFinally {
                log("onFinally")
            }
        }
    }

    fun cancelEnqueueJob() {
        enqueueJob?.cancel()
    }

    /**
     * 异步请求
     * 不限定 Api 的返回值类型
     * 在 onSuccess 回调中直接返回在 ApiService 中定义的整个返回值类型
     */
    fun enqueueOrigin() {
        remoteDataSource.enqueueOrigin(apiFun = {
            getCity(keywords = "广州")
        }, showLoading = true) {
            onSuccess { response ->
                log("onSuccess response: ${toPrettyJson(response)}")
            }
        }
    }

    /**
     * 同步请求，可能会抛出异常
     * 在 onSuccess 回调中直接拿到 IHttpWrapMode 中的 Data
     */
    fun execute() {
        viewModelScope.launch {
            val time = measureTimeMillis {
                try {
                    val res = remoteDataSource.execute {
                        getProvince()
                    }
                    log(toPrettyJson(res))
                } catch (e: Throwable) {
                    log(e.message)
                }
            }
            log("耗时：" + time + "毫秒")
        }
    }

    private fun toPrettyJson(any: Any): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(any)
    }

    private var log = ""

    @Synchronized
    private fun log(msg: Any?) {
        val newLog = "[${Thread.currentThread().name}]-${msg}"
        log = "$log\n*************\n$newLog"
        logLiveData.postValue(log)
        Log.e("TAG", newLog)
    }

    @Synchronized
    fun clearLog() {
        log = ""
        logLiveData.postValue("")
    }

}