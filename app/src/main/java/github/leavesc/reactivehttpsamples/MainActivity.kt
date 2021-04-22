package github.leavesc.reactivehttpsamples

import android.os.Bundle
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.ui.MapActivity
import github.leavesc.reactivehttpsamples.ui.SingleRequestActivity
import github.leavesc.reactivehttpsamples.ui.TogetherRequestActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_weather.setOnClickListener {
            startActivity<MapActivity>()
        }
        btn_single.setOnClickListener {
            startActivity<SingleRequestActivity>()
        }
        btn_together.setOnClickListener {
            startActivity<TogetherRequestActivity>()
        }
    }

}