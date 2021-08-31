package github.leavesc.reactivehttpsamples.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesc.reactivehttpsamples.adapter.WeatherAdapter
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.bean.CastsBean
import github.leavesc.reactivehttpsamples.core.bean.ForecastsBean
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
        forecastsBeanLiveData.observe(this@WeatherActivity, {
            showWeather(it)
        })
    }

    private val castsBeanList = mutableListOf<CastsBean>()

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

    private fun showWeather(forecastsBean: ForecastsBean) {
        bind.tvCity.text = forecastsBean.city
        castsBeanList.clear()
        castsBeanList.addAll(forecastsBean.casts)
        weatherAdapter.notifyDataSetChanged()
        bind.swipeRefreshLayout.isRefreshing = false
    }

}