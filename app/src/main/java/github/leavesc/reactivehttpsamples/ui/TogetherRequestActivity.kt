package github.leavesc.reactivehttpsamples.ui

import android.os.Bundle
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.viewmodel.TogetherRequestViewModel
import github.leavesc.reactivehttpsamples.databinding.ActivityTogetherRequestBinding

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class TogetherRequestActivity : BaseActivity() {

    override val bind by getBind<ActivityTogetherRequestBinding>()

    private val togetherRequestViewModel by getViewModel<TogetherRequestViewModel> {
        logLiveData.observe(this@TogetherRequestActivity, {
            bind.tvLog.text = it
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.btnTogetherSuccess.setOnClickListener {
            togetherRequestViewModel.togetherSuccess()
        }
        bind.btnCancelTogetherSuccessJob.setOnClickListener {
            togetherRequestViewModel.cancelTogetherSuccessJob()
        }
        bind.btnTogetherFailed.setOnClickListener {
            togetherRequestViewModel.togetherFailed()
        }
        bind.btnCleanLog.setOnClickListener {
            togetherRequestViewModel.clearLog()
        }
    }

}