package leavesc.hello.weather.core.viewmodel

import androidx.lifecycle.MutableLiveData
import leavesc.hello.weather.core.http.RequestCallback
import leavesc.hello.weather.core.http.datasource.MapDataSource
import leavesc.hello.weather.core.http.viewmodel.BaseViewModel
import leavesc.hello.weather.core.model.DistrictBean

/**
 * 作者：leavesC
 * 时间：2019/5/31 20:41
 * 描述：
 */
class MapViewModel : BaseViewModel() {

    companion object {
        const val TYPE_PROVINCE = 10

        const val TYPE_CITY = 20

        const val TYPE_COUNTY = 30
    }

    private val mapDataSource = MapDataSource(this)

    val stateLiveData = MutableLiveData<Int>()

    init {
        stateLiveData.value = TYPE_PROVINCE
    }

    val provinceLiveData = MutableLiveData<List<DistrictBean>>()

    val cityLiveData = MutableLiveData<List<DistrictBean>>()

    val realLiveData = MutableLiveData<List<DistrictBean>>()

    val adCodeSelectedLiveData = MutableLiveData<String>()

    fun getProvince() {
        mapDataSource.getProvince(object : RequestCallback<List<DistrictBean>> {
            override fun onSuccess(data: List<DistrictBean>) {
                stateLiveData.value = TYPE_PROVINCE
                provinceLiveData.value = data[0].districts
                realLiveData.value = data[0].districts
            }
        })
    }

    private fun getCity(province: String) {
        mapDataSource.getCity(province, object : RequestCallback<List<DistrictBean>> {
            override fun onSuccess(data: List<DistrictBean>) {
                stateLiveData.value = TYPE_CITY
                cityLiveData.value = data[0].districts
                realLiveData.value = data[0].districts
            }
        })
    }

    private fun getCounty(city: String) {
        mapDataSource.getCounty(city, object : RequestCallback<List<DistrictBean>> {
            override fun onSuccess(data: List<DistrictBean>) {
                val districts = data[0].districts
                if (districts.isNullOrEmpty()) {
                    adCodeSelectedLiveData.value = city
                } else {
                    stateLiveData.value = TYPE_COUNTY
                    realLiveData.value = data[0].districts
                }
            }
        })
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

}