package github.leavesc.reactivehttpsamples.ui

import android.os.Bundle
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.viewmodel.SingleRequestViewModel
import github.leavesc.reactivehttpsamples.databinding.ActivitySingleRequestBinding

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:00
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class SingleRequestActivity : BaseActivity() {

    override val bind by getBind<ActivitySingleRequestBinding>()

    private val singleRequestViewModel by getViewModel<SingleRequestViewModel> {
        logLiveData.observe(this@SingleRequestActivity, {
            bind.tvLog.text = it
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.btnExecute.setOnClickListener {
            singleRequestViewModel.execute()
        }
        bind.btnCancelExecuteJob.setOnClickListener {
            singleRequestViewModel.cancelExecuteJob()
        }
        bind.btnRequest.setOnClickListener {
            singleRequestViewModel.request()
        }
        bind.btnCleanLog.setOnClickListener {
            singleRequestViewModel.clearLog()
        }
    }

}