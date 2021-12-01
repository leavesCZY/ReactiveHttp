package github.leavesc.reactivehttp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @Author: leavesC
 * @Date: 2021/11/29 11:38
 * @Desc:
 * @Github：https://github.com/leavesC
 */
interface IUIAction {

    val lifecycleSupportedScope: CoroutineScope

    val uiActionEventFlow: Flow<UIActionEvent>?

    fun showLoading() {
        dispatchUIActionEvent(ShowLoadingEvent)
    }

    fun dismissLoading() {
        dispatchUIActionEvent(DismissLoadingEvent)
    }

    fun showToast(msg: String) {
        dispatchUIActionEvent(ShowToastEvent(msg))
    }

    fun finishView() {
        dispatchUIActionEvent(FinishViewEvent)
    }

    fun dispatchUIActionEvent(actionEvent: UIActionEvent) {

    }

}

interface IUIActionEventObserver {

    val lifecycleSupportedScope: CoroutineScope

    fun showLoading()

    fun dismissLoading()

    fun showToast(msg: String)

    fun finishView()

    /**
     * 用于方便获取 ViewModel 实例
     * 重点在于调用了 collectUiActionIfNeed 方法
     * 启动了协程对 uiActionEventFlow 进行监听
     */
    fun <VM> getViewModelFast(
        viewModelStoreOwner: ViewModelStoreOwner,
        lifecycleOwner: LifecycleOwner,
        viewModelClass: Class<VM>,
        factory: ViewModelProvider.Factory? = null,
        initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): VM where VM : ViewModel, VM : IUIAction {
        return if (factory == null) {
            ViewModelProvider(viewModelStoreOwner).get(viewModelClass)
        } else {
            ViewModelProvider(viewModelStoreOwner, factory).get(viewModelClass)
        }.apply {
            initializer?.invoke(this, lifecycleOwner)
            collectUiActionIfNeed(viewModel = this)
        }
    }

    /**
     * 如果外部不通过 getViewModelFast 方法来获取 ViewModel 实例的话
     * 则必须在自己构建 ViewModel 实例后调用 collectUiActionIfNeed 方法
     * 以便能够启动协程对 uiActionEventFlow 进行监听
     */
    fun <VM> collectUiActionIfNeed(
        viewModel: VM
    ) where VM : ViewModel, VM : IUIAction {
        val uiActionEventFlow = viewModel.uiActionEventFlow
        if (uiActionEventFlow != null) {
            lifecycleSupportedScope.launch(Dispatchers.Main.immediate) {
                uiActionEventFlow.collect {
                    when (it) {
                        is ShowLoadingEvent -> {
                            showLoading()
                        }
                        DismissLoadingEvent -> {
                            dismissLoading()
                        }
                        is ShowToastEvent -> {
                            if (it.message.isNotBlank()) {
                                showToast(it.message)
                            }
                        }
                        FinishViewEvent -> {
                            finishView()
                        }
                    }
                }
            }
        }
    }

}