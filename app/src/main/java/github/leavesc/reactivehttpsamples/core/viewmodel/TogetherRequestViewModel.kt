package github.leavesc.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class TogetherRequestViewModel : BaseViewModel() {

    val logLiveData = MutableLiveData<String>()

    private var togetherSuccessJob: Job? = null

    fun togetherSuccess() {
        cancelTogetherSuccessJob()
        togetherSuccessJob = remoteDataSource.enqueue({ getProvince() }, {
            getCity("广东")
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
            onSuccess { dataA, dataB ->
                log("onSuccess：")
                log("dataA：$dataA")
                log("dataB：$dataB")
            }
            onSuccessIO { _, _ ->
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

    fun cancelTogetherSuccessJob() {
        if (togetherSuccessJob?.isActive == true) {
            togetherSuccessJob?.cancel()
        }
    }

    private var togetherFailedJob: Job? = null

    fun togetherFailed() {
        cancelTogetherFailedJob()
        togetherFailedJob = remoteDataSource.enqueue({ getProvince() }, {
            mustFailed()
        }) {
            onStart {
                log("onStart")
            }
            onCancelled {
                log("onCancelled")
            }
            onFailed {
                log("onFailed：$it")
                showToast(it.errorMessage)
            }
            onFailToast {
                false
            }
            onSuccess { dataA, dataB ->
                log("onSuccess： $dataA   $dataB")
            }
            onSuccessIO { _, _ ->
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

    fun cancelTogetherFailedJob() {
        if (togetherFailedJob?.isActive == true) {
            togetherFailedJob?.cancel()
        }
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