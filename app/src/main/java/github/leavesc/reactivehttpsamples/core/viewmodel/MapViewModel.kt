package github.leavesc.reactivehttpsamples.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import github.leavesc.reactivehttpsamples.core.bean.DistrictBean
import kotlinx.coroutines.delay

/**
 * @Author: leavesC
 * @Date: 2020/6/23 0:37
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class MapViewModel : BaseViewModel() {

    companion object {

        const val TYPE_PROVINCE = 10

        const val TYPE_CITY = 20

        const val TYPE_COUNTY = 30

    }

    val stateLiveData = MutableLiveData<Int>()

    init {
        stateLiveData.value = TYPE_PROVINCE
    }

    private val provinceLiveData = MutableLiveData<List<DistrictBean>>()

    private val cityLiveData = MutableLiveData<List<DistrictBean>>()

    val realLiveData = MutableLiveData<List<DistrictBean>>()

    val adCodeSelectedLiveData = MutableLiveData<String>()

    fun getProvince() {
        remoteDataSource.enqueueLoading({
            //主动延迟一段时间，避免弹窗太快消失
            delay(1200)
            getProvince()
        }) {
            onStart {
                log("onStart")
            }
            onSuccess {
                log("onSuccess")
                stateLiveData.value = TYPE_PROVINCE
                provinceLiveData.value = it[0].districts
                realLiveData.value = it[0].districts
            }
            onSuccessIO {
                log("onSuccessIO")
            }
            onFailed {
                log("onFailed")
            }
            onCancelled {
                log("onCancelled")
            }
            onFinally {
                log("onFinally")
            }
        }
    }

    private fun getCity(province: String) {
        remoteDataSource.enqueueLoading({
            getCity(province)
        }) {
            onSuccess {
                stateLiveData.value = TYPE_CITY
                cityLiveData.value = it[0].districts
                realLiveData.value = it[0].districts
            }
        }
    }

    private fun getCounty(city: String) {
        remoteDataSource.enqueueLoading({
            getCounty(city)
        }) {
            onSuccess {
                val districts = it[0].districts
                if (districts.isNullOrEmpty()) {
                    adCodeSelectedLiveData.value = city
                } else {
                    stateLiveData.value = TYPE_COUNTY
                    realLiveData.value = it[0].districts
                }
            }
        }
    }

    fun onBackPressed(): Boolean {
        when (stateLiveData.value) {
            TYPE_PROVINCE -> {
                return true
            }
            TYPE_CITY -> {
                stateLiveData.value = TYPE_PROVINCE
                realLiveData.value = provinceLiveData.value
            }
            TYPE_COUNTY -> {
                stateLiveData.value = TYPE_CITY
                realLiveData.value = cityLiveData.value
            }
        }
        return false
    }

    fun onPlaceClicked(position: Int) {
        when (stateLiveData.value) {
            TYPE_PROVINCE -> {
                realLiveData.value?.get(position)?.adcode?.let {
                    getCity(it)
                }
            }
            TYPE_CITY -> {
                realLiveData.value?.get(position)?.adcode?.let {
                    getCounty(it)
                }
            }
            TYPE_COUNTY -> {
                adCodeSelectedLiveData.value = realLiveData.value?.get(position)?.adcode
            }
        }
    }

    private var log = ""

    @Synchronized
    private fun log(msg: String) {
        val newLog = "[${Thread.currentThread().name}]-${msg}"
        log = log + "\n" + newLog
        Log.e("TAG", newLog)
    }

}