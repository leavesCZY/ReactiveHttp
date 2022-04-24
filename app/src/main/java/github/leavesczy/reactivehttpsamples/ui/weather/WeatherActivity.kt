package github.leavesczy.reactivehttpsamples.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesczy.reactivehttpsamples.adapter.WeatherAdapter
import github.leavesczy.reactivehttpsamples.base.BaseActivity
import github.leavesczy.reactivehttpsamples.core.mode.CastsMode
import github.leavesczy.reactivehttpsamples.core.mode.ForecastsMode
import github.leavesczy.reactivehttpsamples.core.viewmodel.weather.WeatherViewModel
import github.leavesczy.reactivehttpsamples.databinding.ActivityWeatherBinding

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class WeatherActivity : BaseActivity() {

    companion object {

        private const val adCodeKey = "adCodeKey"

        fun navTo(context: Context, adCode: String) {
            val intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra(adCodeKey, adCode)
            context.startActivity(intent)
        }

    }

    override val bind by getBind<ActivityWeatherBinding>()

    private val weatherViewModel by getViewModel<WeatherViewModel> {
        forecastsModeLiveData.observe(this@WeatherActivity) {
            showWeather(it)
        }
    }

    private val castsBeanList = mutableListOf<CastsMode>()

    private val weatherAdapter = WeatherAdapter(castsBeanList)

    private val adCode by lazy {
        intent.getStringExtra(adCodeKey) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.rvDailyForecast.layoutManager = LinearLayoutManager(this)
        bind.rvDailyForecast.adapter = weatherAdapter
        bind.swipeRefreshLayout.setOnRefreshListener {
            weatherViewModel.getWeather(adCode)
        }
        weatherViewModel.getWeather(adCode)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showWeather(forecastsMode: ForecastsMode) {
        bind.tvCity.text = forecastsMode.city
        castsBeanList.clear()
        castsBeanList.addAll(forecastsMode.casts)
        weatherAdapter.notifyDataSetChanged()
        bind.swipeRefreshLayout.isRefreshing = false
    }

}