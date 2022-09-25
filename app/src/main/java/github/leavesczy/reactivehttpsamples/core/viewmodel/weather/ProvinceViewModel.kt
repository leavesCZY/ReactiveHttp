package github.leavesczy.reactivehttpsamples.core.viewmodel.weather

import androidx.lifecycle.MutableLiveData
import github.leavesczy.reactivehttpsamples.base.BaseViewModel
import github.leavesczy.reactivehttpsamples.core.mode.DistrictMode
import kotlinx.coroutines.delay

/**
 * @Author: leavesCZY
 * @Date: 2022/4/23 21:06
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class ProvinceViewModel : BaseViewModel() {

    val provinceLiveData = MutableLiveData<List<DistrictMode>>()

    fun getProvince() {
        remoteDataSource.enqueue({
            delay(1200)
            getProvince()
        }) {
            onSuccess {
                provinceLiveData.value = it[0].districts
            }
        }
    }

}