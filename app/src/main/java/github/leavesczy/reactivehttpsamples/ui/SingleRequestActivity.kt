package github.leavesczy.reactivehttpsamples.ui

import android.os.Bundle
import androidx.activity.viewModels
import github.leavesczy.reactivehttpsamples.base.BaseActivity
import github.leavesczy.reactivehttpsamples.core.viewmodel.SingleRequestViewModel
import github.leavesczy.reactivehttpsamples.databinding.ActivitySingleRequestBinding

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:00
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class SingleRequestActivity : BaseActivity() {

    override val bind by getBind<ActivitySingleRequestBinding>()

    private val singleRequestViewModel by viewModels<SingleRequestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        singleRequestViewModel.logLiveData.observe(this@SingleRequestActivity) {
            bind.tvLog.text = it
        }
        bind.btnEnqueue.setOnClickListener {
            singleRequestViewModel.enqueue()
        }
        bind.btnCancelEnqueueJob.setOnClickListener {
            singleRequestViewModel.cancelEnqueueJob()
        }
        bind.btnExecuteOrigin.setOnClickListener {
            singleRequestViewModel.enqueueOrigin()
        }
        bind.btnExecute.setOnClickListener {
            singleRequestViewModel.execute()
        }
        bind.btnCleanLog.setOnClickListener {
            singleRequestViewModel.clearLog()
        }
    }

}