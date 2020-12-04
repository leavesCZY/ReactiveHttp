package leavesc.hello.weather.core.http

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import leavesc.hello.weather.core.http.viewmodel.BaseViewModel
import leavesc.hello.weather.core.http.viewmodel.BaseViewModelEvent

/**
 * 作者：leavesC
 * 时间：2019/5/31 9:38
 * 描述：
 */
interface IBaseViewModelEvent {

    fun showLoading(msg: String)

    fun showLoading() {
        showLoading("")
    }

    fun dismissLoading()

    fun showToast(msg: String)

    fun finishView()

    fun pop()

}

interface IIBaseViewModelEventObserver : IBaseViewModelEvent {

    fun initViewModel(): BaseViewModel? {
        return null
    }

    fun initViewModelList(): MutableList<BaseViewModel>? {
        return null
    }

    fun getLifecycleOwner(): LifecycleOwner

    fun initViewModelEvent() {
        var list: MutableList<BaseViewModel>? = null
        val initViewModelList = initViewModelList()
        if (initViewModelList.isNullOrEmpty()) {
            val baseViewModel = initViewModel()
            baseViewModel?.let {
                list = mutableListOf(baseViewModel)
            }
        } else {
            list = initViewModelList
        }
        list?.let {
            observeEvent(list!!)
        }
    }

    fun observeEvent(viewModelList: MutableList<BaseViewModel>) {
        for (viewModel in viewModelList) {
            viewModel.baseActionEvent.observe(getLifecycleOwner(), Observer { it ->
                it?.let {
                    when (it.action) {
                        BaseViewModelEvent.SHOW_LOADING_DIALOG -> {
                            showLoading(it.message)
                        }
                        BaseViewModelEvent.DISMISS_LOADING_DIALOG -> {
                            dismissLoading()
                        }
                        BaseViewModelEvent.SHOW_TOAST -> {
                            showToast(it.message)
                        }
                        BaseViewModelEvent.FINISH -> {
                            finishView()
                        }
                        BaseViewModelEvent.POP -> {
                            pop()
                        }
                    }
                }
            })
        }
    }

    fun getLContext(): Context

    fun <T> startActivity(clazz: Class<T>) {
        getLContext().startActivity(Intent(getLContext(), clazz))
    }

    override fun showToast(msg: String) {
        Toast.makeText(getLContext(), msg, Toast.LENGTH_SHORT).show()
    }

}