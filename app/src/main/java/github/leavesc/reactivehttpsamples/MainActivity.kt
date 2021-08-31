package github.leavesc.reactivehttpsamples

import android.os.Bundle
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.databinding.ActivityMainBinding
import github.leavesc.reactivehttpsamples.ui.MapActivity
import github.leavesc.reactivehttpsamples.ui.SingleRequestActivity
import github.leavesc.reactivehttpsamples.ui.TogetherRequestActivity

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class MainActivity : BaseActivity() {

    override val bind by getBind<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.btnWeather.setOnClickListener {
            startActivity<MapActivity>()
        }
        bind.btnSingle.setOnClickListener {
            startActivity<SingleRequestActivity>()
        }
        bind.btnTogether.setOnClickListener {
            startActivity<TogetherRequestActivity>()
        }
    }

}