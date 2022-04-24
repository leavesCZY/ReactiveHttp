package github.leavesczy.reactivehttpsamples

import android.os.Bundle
import github.leavesczy.reactivehttpsamples.base.BaseActivity
import github.leavesczy.reactivehttpsamples.databinding.ActivityMainBinding
import github.leavesczy.reactivehttpsamples.ui.SingleRequestActivity
import github.leavesczy.reactivehttpsamples.ui.TogetherRequestActivity
import github.leavesczy.reactivehttpsamples.ui.weather.AreaActivity

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class MainActivity : BaseActivity() {

    override val bind by getBind<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.btnSingle.setOnClickListener {
            startActivity<SingleRequestActivity>()
        }
        bind.btnTogether.setOnClickListener {
            startActivity<TogetherRequestActivity>()
        }
        bind.btnWeather.setOnClickListener {
            startActivity<AreaActivity>()
        }
    }

}