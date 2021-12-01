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

    fun <VM> getViewModelFast(
        lifecycleOwner: LifecycleOwner,
        viewModelClass: Class<VM>,
        factory: ViewModelProvider.Factory? = null,
        initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): VM where VM : ViewModel {
        return when (lifecycleOwner) {
            is ViewModelStoreOwner -> {
                if (factory == null) {
                    ViewModelProvider(lifecycleOwner).get(viewModelClass)
                } else {
                    ViewModelProvider(lifecycleOwner, factory).get(viewModelClass)
                }
            }
            else -> {
                factory?.create(viewModelClass) ?: viewModelClass.newInstance()
            }
        }.apply {
            applyExtraAction(lifecycleOwner = lifecycleOwner, viewModel = this)
            collectUiActionIfNeed(lifecycleOwner = lifecycleOwner, viewModel = this)
            initializer?.invoke(this, lifecycleOwner)
        }
    }

    /**
     * 外部可以通过此方法来为 ViewModel 增加一些额外操作
     */
    fun applyExtraAction(lifecycleOwner: LifecycleOwner, viewModel: ViewModel) {

    }

    fun <VM> collectUiActionIfNeed(
        lifecycleOwner: LifecycleOwner,
        viewModel: VM
    ) where VM : ViewModel {
        val uiActionEventFlow = (viewModel as? IUIAction)?.uiActionEventFlow
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