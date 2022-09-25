package github.leavesczy.reactivehttpsamples.ui

import android.os.Bundle
import androidx.activity.viewModels
import github.leavesczy.reactivehttpsamples.base.BaseActivity
import github.leavesczy.reactivehttpsamples.core.viewmodel.TogetherRequestViewModel
import github.leavesczy.reactivehttpsamples.databinding.ActivityTogetherRequestBinding

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class TogetherRequestActivity : BaseActivity() {

    override val bind by getBind<ActivityTogetherRequestBinding>()

    private val togetherRequestViewModel by viewModels<TogetherRequestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        togetherRequestViewModel.logLiveData.observe(this@TogetherRequestActivity) {
            bind.tvLog.text = it
        }
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