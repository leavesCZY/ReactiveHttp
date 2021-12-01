package github.leavesc.reactivehttp.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.leavesc.reactivehttp.viewmodel.IUIAction
import github.leavesc.reactivehttp.viewmodel.UIActionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @Author: leavesC
 * @Date: 2021/11/28 20:08
 * @Desc:
 * @Github：https://github.com/leavesC
 */
open class ReactiveViewModel : ViewModel(), IUIAction {

    override val lifecycleSupportedScope: CoroutineScope
        get() = viewModelScope

    private val _uiActionEventFlow = MutableSharedFlow<UIActionEvent>(
        replay = 0,
        extraBufferCapacity = 4,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val uiActionEventFlow: Flow<UIActionEvent>?
        get() = _uiActionEventFlow

    override fun dispatchUIActionEvent(actionEvent: UIActionEvent) {
        _uiActionEventFlow.let {
            lifecycleSupportedScope.launch(Dispatchers.Main.immediate) {
                it.emit(actionEvent)
            }
        }
    }

}

open class ReactiveAndroidViewModel(application: Application) : AndroidViewModel(application),
    IUIAction {

    override val lifecycleSupportedScope: CoroutineScope
        get() = viewModelScope

    private val _uiActionEventFlow = MutableSharedFlow<UIActionEvent>(
        replay = 0,
        extraBufferCapacity = 4,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val uiActionEventFlow: Flow<UIActionEvent>?
        get() = _uiActionEventFlow

    override fun dispatchUIActionEvent(actionEvent: UIActionEvent) {
        _uiActionEventFlow.let {
            lifecycleSupportedScope.launch(Dispatchers.Main.immediate) {
                it.emit(actionEvent)
            }
        }
    }

}