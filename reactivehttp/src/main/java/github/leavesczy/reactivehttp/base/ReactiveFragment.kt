package github.leavesczy.reactivehttp.base

import android.app.ProgressDialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import github.leavesczy.reactivehttp.viewmodel.IUIAction
import github.leavesczy.reactivehttp.viewmodel.IUIActionEventObserver
import kotlinx.coroutines.CoroutineScope

/**
 * @Author: leavesCZY
 * @Date: 2021/12/1 23:16
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
open class ReactiveFragment : Fragment(), IUIActionEventObserver {

    protected inline fun <reified VM> getViewModel(
        lifecycleOwner: LifecycleOwner = this,
        factory: ViewModelProvider.Factory? = null,
        noinline initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): Lazy<VM> where VM : ViewModel, VM : IUIAction {
        return lazy {
            getViewModelFast(
                viewModelStoreOwner = this,
                lifecycleOwner = lifecycleOwner,
                viewModelClass = VM::class.java,
                factory = factory,
                initializer = initializer
            )
        }
    }

    protected inline fun <reified VM> getViewModelInstance(
        lifecycleOwner: LifecycleOwner = this,
        crossinline create: () -> VM,
        noinline initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null
    ): Lazy<VM> where VM : ViewModel, VM : IUIAction {
        return lazy {
            getViewModelFast(
                viewModelStoreOwner = this,
                lifecycleOwner = lifecycleOwner,
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
        get() = viewLifecycleOwner.lifecycleScope

    private var loadDialog: ProgressDialog? = null

    override fun showLoading() {
        dismissLoading()
        activity?.let { act ->
            loadDialog = ProgressDialog(act).apply {
                setCancelable(true)
                setCanceledOnTouchOutside(false)
                show()
            }
        }
    }

    override fun dismissLoading() {
        loadDialog?.takeIf { it.isShowing }?.dismiss()
        loadDialog = null
    }

    override fun showToast(msg: String) {
        activity?.let { act ->
            if (msg.isNotBlank()) {
                Toast.makeText(act, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun finishView() {
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
    }

}