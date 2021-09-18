package github.leavesc.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import github.leavesc.reactivehttpsamples.core.bean.HttpWrapBean
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

    private suspend fun delayTest(): HttpWrapBean<String> {
        delay(2000)
        return HttpWrapBean.success("how are you")
    }

    fun execute() {
        cancelExecuteJob()
        executeReqJob = remoteDataSource.enqueue({ delayTest() }) {
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
            onSuccessIO { data ->
                repeat(5) { time ->
                    delay(300)
                    log("onSuccessIO： $time  $data")
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

    private suspend fun delayTest2(): HttpWrapBean<String> {
        delay(1000)
        return HttpWrapBean.success("how are you")
    }

    fun request() {
        val res = remoteDataSource.execute({ delayTest2() })
        log(res)
    }

    private var log = ""

    @Synchronized
    private fun log(msg: String) {
        val newLog = "[${Thread.currentThread().name}]-${msg}"
        log = log + "\n" + newLog
        logLiveData.postValue(log)
        Log.e("TAG", newLog)
    }

    fun clearLog() {
        log = ""
        logLiveData.postValue("")
    }

}