package github.leavesc.reactivehttpsamples.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesc.reactivehttpsamples.adapter.WeatherAdapter
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.mode.CastsMode
import github.leavesc.reactivehttpsamples.core.mode.ForecastsMode
import github.leavesc.reactivehttpsamples.core.viewmodel.WeatherViewModel
import github.leavesc.reactivehttpsamples.databinding.ActivityWeatherBinding

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class WeatherActivity : BaseActivity() {

    override val bind by getBind<ActivityWeatherBinding>()

    private val weatherViewModel by getViewModel<WeatherViewModel> {
        forecastsModeLiveData.observe(this@WeatherActivity, {
            showWeather(it)
        })
    }

    private val castsBeanList = mutableListOf<CastsMode>()

    private val weatherAdapter = WeatherAdapter(castsBeanList)

    private val adCode by lazy {
        intent.getStringExtra("adCode") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.rvDailyForecast.layoutManager = LinearLayoutManager(this)
        bind.rvDailyForecast.adapter = weatherAdapter
        bind.swipeRefreshLayout.setOnRefreshListener {
            weatherViewModel.getWeather(adCode)
        }
        bind.ivPlace.setOnClickListener {
            startActivity<MapActivity>()
            finish()
        }
        weatherViewModel.getWeather(adCode)
    }

    private fun showWeather(forecastsMode: ForecastsMode) {
        bind.tvCity.text = forecastsMode.city
        castsBeanList.clear()
        castsBeanList.addAll(forecastsMode.casts)
        weatherAdapter.notifyDataSetChanged()
        bind.swipeRefreshLayout.isRefreshing = false
    }

}