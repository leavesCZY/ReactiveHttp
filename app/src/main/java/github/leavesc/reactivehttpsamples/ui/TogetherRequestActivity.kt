package github.leavesc.reactivehttpsamples.ui

import android.os.Bundle
import github.leavesc.reactivehttpsamples.R
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.viewmodel.TogetherRequestViewModel
import kotlinx.android.synthetic.main.activity_together_request.*

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class TogetherRequestActivity : BaseActivity() {

    private val togetherRequestViewModel by getViewModel<TogetherRequestViewModel> {
        logLiveData.observe(this@TogetherRequestActivity, {
            tv_log.text = it
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_together_request)
        btn_togetherSuccess.setOnClickListener {
            togetherRequestViewModel.togetherSuccess()
        }
        btn_cancelTogetherSuccessJob.setOnClickListener {
            togetherRequestViewModel.cancelTogetherSuccessJob()
        }
        btn_togetherFailed.setOnClickListener {
            togetherRequestViewModel.togetherFailed()
        }
        btn_cancelTogetherFailedJob.setOnClickListener {
            togetherRequestViewModel.cancelTogetherFailedJob()
        }
        btn_cleanLog.setOnClickListener {
            togetherRequestViewModel.clearLog()
        }
    }

}