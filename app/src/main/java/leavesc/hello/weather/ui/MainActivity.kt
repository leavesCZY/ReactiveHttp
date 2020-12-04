package leavesc.hello.weather.ui

import android.os.Bundle
import android.text.TextUtils
import leavesc.hello.weather.R
import leavesc.hello.weather.core.cache.AreaCache
import leavesc.hello.weather.core.view.BaseActivity

/**
 * 作者：leavesC
 * 时间：2019/5/31 15:39
 * 描述：
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (TextUtils.isEmpty(AreaCache.getAdCode(this))) {
            startActivity(MapActivity::class.java)
        } else {
            startActivity(WeatherActivity::class.java)
        }
        finish()
    }

}