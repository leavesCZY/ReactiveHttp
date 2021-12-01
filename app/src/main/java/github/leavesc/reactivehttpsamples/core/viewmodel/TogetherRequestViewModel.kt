package github.leavesc.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
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
        togetherSuccessJob = remoteDataSource.enqueue(apiFunA = {
            val result = getProvince()
            //主动延迟一点时间，以便来得及取消网络请求
            delay(1500)
            result
        }, apiFunB = {
            getCity("广州")
        }, showLoading = true) {
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
                log("onSuccess： \n ${toPrettyJson(dataA)} \n ${toPrettyJson(dataB)}")
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
            getProvince()
        }, apiFunB = {
            mustFailed()
        }, showLoading = true) {
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
                log("onSuccess： \n ${toPrettyJson(dataA)} \n ${toPrettyJson(dataB)}")
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