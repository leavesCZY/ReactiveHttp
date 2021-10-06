package github.leavesc.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:08
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class SingleRequestViewModel : BaseViewModel() {

    val logLiveData = MutableLiveData<String>()

    private var executeReqJob: Job? = null

    fun execute() {
        cancelExecuteJob()
        executeReqJob = remoteDataSource.enqueue({
            val result = getProvince()
            //主动延迟一点时间，以便来得及取消网络请求
            delay(1500)
            result
        }) {
            onStart {
                log("onStart")
            }
            onCancelled {
                log("onCancelled")
            }
            onFailed {
                log("onFailed：$it")
            }
            onFailToast {
                true
            }
            onSuccess {
                log("onSuccess： $it")
            }
            onSuccessIO {
                log("onSuccessIO：")
                repeat(5) { time ->
                    delay(300)
                    log("$time")
                }
            }
            onFinally {
                log("onFinally")
            }
        }
    }

    fun cancelExecuteJob() {
        executeReqJob?.cancel()
    }

    fun request() {
        val res = remoteDataSource.execute({ getProvince() })
        log(res)
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