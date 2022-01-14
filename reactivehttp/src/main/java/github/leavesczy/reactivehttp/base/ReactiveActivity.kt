package github.leavesczy.reactivehttp.base

import android.app.ProgressDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import github.leavesczy.reactivehttp.viewmodel.IUIAction
import github.leavesczy.reactivehttp.viewmodel.IUIActionEventObserver
import kotlinx.coroutines.CoroutineScope

/**
 * @Author: leavesCZY
 * @Date: 2020/10/22 10:27
 * @Desc: BaseActivity
 * @Github：https://github.com/leavesCZY
 */
open class ReactiveActivity : AppCompatActivity(), IUIActionEventObserver {

    protected inline fun <reified VM> getViewModel(
        factory: ViewModelProvider.Factory? = null,
        noinline initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): Lazy<VM> where VM : ViewModel, VM : IUIAction {
        return lazy {
            getViewModelFast(
                viewModelStoreOwner = this,
                lifecycleOwner = this,
                viewModelClass = VM::class.java,
                factory = factory,
                initializer = initializer
            )
        }
    }

    protected inline fun <reified VM> getViewModelInstance(
        crossinline create: () -> VM,
        noinline initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): Lazy<VM> where VM : ViewModel, VM : IUIAction {
        return lazy {
            getViewModelFast(
                viewModelStoreOwner = this,
                lifecycleOwner = this,
                viewModelClass = VM::class.java,
                factory = object : ViewModelProvider.NewInstanceFactory() {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return create() as T
                    }
                },
                initializer = initializer
            )
        }
    }

    override val lifecycleSupportedScope: CoroutineScope
        get() = lifecycleScope

    private var loadDialog: ProgressDialog? = null

    override fun showLoading() {
        dismissLoading()
        loadDialog = ProgressDialog(this).apply {
            setCancelable(true)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    override fun dismissLoading() {
        loadDialog?.takeIf { it.isShowing }?.dismiss()
        loadDialog = null
    }

    override fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun finishView() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }

}