package github.leavesc.reactivehttpsamples.ui

import android.os.Bundle
import github.leavesc.reactivehttpsamples.R
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.viewmodel.SingleRequestViewModel
import kotlinx.android.synthetic.main.activity_single_request.*

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:00
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class SingleRequestActivity : BaseActivity() {

    private val singleRequestViewModel by getViewModel<SingleRequestViewModel> {
        logLiveData.observe(this@SingleRequestActivity, {
            tv_log.text = it
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_request)
        btn_execute.setOnClickListener {
            singleRequestViewModel.execute()
        }
        btn_cancelExecuteJob.setOnClickListener {
            singleRequestViewModel.cancelExecuteJob()
        }
        btn_request.setOnClickListener {
            singleRequestViewModel.request()
        }
        btn_cleanLog.setOnClickListener {
            singleRequestViewModel.clearLog()
        }
    }

}