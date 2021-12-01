package github.leavesc.reactivehttpsamples.base

import github.leavesc.reactivehttp.base.ReactiveViewModel
import github.leavesc.reactivehttpsamples.core.http.AppRemoteDataSource

/**
 * @Author: leavesC
 * @Date: 2020/12/4 22:12
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
open class BaseViewModel : ReactiveViewModel() {

    /**
     * 正常来说单个项目中应该只有一个 RemoteDataSource 实现类，即全局使用同一份配置
     * 但父类也应该允许子类使用一个单独的 RemoteDataSource
     */
    protected open val remoteDataSource = AppRemoteDataSource(this)

}