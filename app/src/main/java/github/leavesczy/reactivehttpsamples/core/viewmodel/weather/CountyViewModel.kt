package github.leavesczy.reactivehttpsamples.core.viewmodel.weather

import androidx.lifecycle.MutableLiveData
import github.leavesczy.reactivehttpsamples.base.BaseViewModel
import github.leavesczy.reactivehttpsamples.core.mode.DistrictMode

/**
 * @Author: leavesCZY
 * @Date: 2022/4/23 21:07
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class CountyViewModel(private val city: String) : BaseViewModel() {

    val countyLiveData = MutableLiveData<List<DistrictMode>>()

    fun getCounty() {
        remoteDataSource.enqueue({
            getCounty(city)
        }) {
            onSuccess {
                val districts = it[0].districts
                countyLiveData.value = districts
            }
        }
    }

}