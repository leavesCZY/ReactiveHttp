package github.leavesczy.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import github.leavesczy.reactivehttpsamples.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class TogetherRequestViewModel : BaseViewModel() {

    val logLiveData = MutableLiveData<String>()

    private var togetherSuccessJob: Job? = null

    fun togetherSuccess() {
        cancelTogetherSuccessJob()
        togetherSuccessJob = remoteDataSource.enqueue(apiFunA = {
            val result = getProvince()
            //主动延迟一点时间，以便来得及取消网络请求
            delay(1500)
            result
        }, apiFunB = {
            getCity("广州")
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
                log("onSuccess： \ndataA:\n ${toPrettyJson(dataA)} \ndataB:\n ${toPrettyJson(dataB)}")
            }
            onFinally {
                log("onFinally")
            }
        }
    }

    fun cancelTogetherSuccessJob() {
        togetherSuccessJob?.cancel()
    }

    private var togetherFailedJob: Job? = null

    fun togetherFailed() {
        cancelTogetherFailedJob()
        togetherFailedJob = remoteDataSource.enqueue(apiFunA = {
            delay(600)
            getProvince()
        }, apiFunB = {
            delay(400)
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
                log("onSuccess： \ndataA:\n ${toPrettyJson(dataA)} \ndataA:\n ${toPrettyJson(dataB)}")
            }
            onFinally {
                log("onFinally")
            }
        }
    }

    private fun cancelTogetherFailedJob() {
        togetherFailedJob?.cancel()
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