package github.leavesczy.reactivehttpsamples.core.viewmodel.weather

import androidx.lifecycle.MutableLiveData
import github.leavesczy.reactivehttpsamples.base.BaseViewModel
import github.leavesczy.reactivehttpsamples.core.mode.DistrictMode
import kotlinx.coroutines.delay

/**
 * @Author: leavesCZY
 * @Date: 2022/4/23 21:07
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class CityViewModel(private val province: String) : BaseViewModel() {

    val cityLiveData = MutableLiveData<List<DistrictMode>>()

    fun getCity() {
        remoteDataSource.enqueueLoading({
            //主动延迟一段时间，避免弹窗太快消失
            delay(1200)
            getCity(province)
        }) {
            onSuccess {
                cityLiveData.value = it[0].districts
            }
        }
    }

}