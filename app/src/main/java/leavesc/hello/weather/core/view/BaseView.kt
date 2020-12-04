package leavesc.hello.weather.core.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import leavesc.hello.weather.core.http.IIBaseViewModelEventObserver
import leavesc.hello.weather.core.http.viewmodel.BaseViewModel

/**
 * 作者：leavesC
 * 时间：2019/5/31 9:36
 * 描述：
 */
@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), IIBaseViewModelEventObserver {

    private var loadDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelEvent()
    }

    override fun getLContext(): Context = this

    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun showLoading(msg: String) {
        if (loadDialog == null) {
            loadDialog = ProgressDialog(getLContext())
            loadDialog!!.setCancelable(false)
            loadDialog!!.setCanceledOnTouchOutside(false)
        }
        loadDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    override fun dismissLoading() {
        loadDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun finishView() {
        finish()
    }

    override fun pop() {

    }

    fun <T : BaseViewModel> getViewModel(clazz: Class<T>) =
            ViewModelProviders.of(this).get(clazz)

}

abstract class BaseFragment : Fragment(), IIBaseViewModelEventObserver {

    private var loadDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelEvent()
    }

    override fun getLContext(): Context = this.context!!

    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun showLoading(msg: String) {
        if (loadDialog == null) {
            loadDialog = ProgressDialog(getLContext())
            loadDialog!!.setCancelable(false)
            loadDialog!!.setCanceledOnTouchOutside(false)
        }
        loadDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    override fun dismissLoading() {
        loadDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun finishView() {
        activity?.finish()
    }

    override fun pop() {

    }

    fun <T : BaseViewModel> getViewModel(clazz: Class<T>) =
            ViewModelProviders.of(this).get(clazz)

}