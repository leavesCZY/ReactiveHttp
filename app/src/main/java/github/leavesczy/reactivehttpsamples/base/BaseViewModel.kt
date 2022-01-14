package github.leavesczy.reactivehttpsamples.base

import github.leavesczy.reactivehttp.base.ReactiveViewModel
import github.leavesczy.reactivehttpsamples.core.http.AppRemoteDataSource

/**
 * @Author: leavesCZY
 * @Date: 2020/12/4 22:12
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
open class BaseViewModel : ReactiveViewModel() {

    /**
     * 正常来说单个项目中应该只有一个 RemoteDataSource 实现类，即全局使用同一份配置
     * 但父类也应该允许子类使用一个单独的 RemoteDataSource
     */
    protected open val remoteDataSource = AppRemoteDataSource(this)

}