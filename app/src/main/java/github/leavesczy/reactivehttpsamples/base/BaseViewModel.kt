package github.leavesczy.reactivehttpsamples.base

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.leavesczy.reactivehttpsamples.MainApplication
import github.leavesczy.reactivehttpsamples.core.http.AppRemoteDataSource

/**
 * @Author: leavesCZY
 * @Date: 2020/12/4 22:12
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
open class BaseViewModel : ViewModel() {

    /**
     * 正常来说单个项目中应该只有一个 RemoteDataSource 实现类，即全局使用同一份配置
     * 但父类也应该允许子类使用一个单独的 RemoteDataSource
     */
    protected val remoteDataSource = AppRemoteDataSource(coroutineScope = viewModelScope)

    protected fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            Toast.makeText(MainApplication.context, msg, Toast.LENGTH_SHORT).show()
        }
    }

}