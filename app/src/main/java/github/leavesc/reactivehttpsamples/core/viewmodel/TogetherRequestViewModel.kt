package github.leavesc.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import github.leavesc.reactivehttpsamples.core.bean.HttpWrapBean
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

    private suspend fun togetherSuccessJobFunA(): HttpWrapBean<String> {
        delay(2000)
        return HttpWrapBean.success("how are you")
    }

    private suspend fun togetherSuccessJobFunB(): HttpWrapBean<Int> {
        delay(3000)
        return HttpWrapBean.success(300)
    }

    private var togetherSuccessJob: Job? = null

    fun togetherSuccess() {
        cancelTogetherSuccessJob()
        togetherSuccessJob = remoteDataSource.enqueue({ togetherSuccessJobFunA() }, {
            togetherSuccessJobFunB()
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
                log("onSuccess： $dataA   $dataB")
            }
            onSuccessIO { dataA, dataB ->
                repeat(5) { time ->
                    delay(300)
                    log("onSuccessIO： $time $dataA $dataB")
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

    private suspend fun togetherFailedJobFunA(): HttpWrapBean<String> {
        delay(2000)
        throw RuntimeException("404~")
    }

    private suspend fun togetherFailedJobFunB(): HttpWrapBean<Int> {
        delay(3000)
        return HttpWrapBean.failed(300)
    }

    private var togetherFailedJob: Job? = null

    fun togetherFailed() {
        cancelTogetherFailedJob()
        togetherFailedJob = remoteDataSource.enqueue({ togetherFailedJobFunA() }, {
            togetherFailedJobFunB()
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
            onSuccessIO { dataA, dataB ->
                repeat(5) { time ->
                    delay(300)
                    log("onSuccessIO： $time $dataA $dataB")
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