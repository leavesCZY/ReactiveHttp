package leavesc.hello.weather.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_weather.*
import leavesc.hello.weather.R
import leavesc.hello.weather.adapter.WeatherAdapter
import leavesc.hello.weather.core.cache.AreaCache
import leavesc.hello.weather.core.http.viewmodel.BaseViewModel
import leavesc.hello.weather.core.model.CastsBean
import leavesc.hello.weather.core.model.ForecastsBean
import leavesc.hello.weather.core.view.BaseActivity
import leavesc.hello.weather.core.viewmodel.WeatherViewModel

/**
 * 作者：leavesC
 * 时间：2019/6/2 20:18
 * 描述：
 * GitHub：https://github.com/leavesC
 */
class WeatherActivity : BaseActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    private val castsBeanList = mutableListOf<CastsBean>()

    private val weatherAdapter = WeatherAdapter(castsBeanList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        rv_dailyForecast.layoutManager = LinearLayoutManager(this)
        rv_dailyForecast.adapter = weatherAdapter
        swipeRefreshLayout.setOnRefreshListener {
            weatherViewModel.getWeather(AreaCache.getAdCode(this))
        }
        iv_place.setOnClickListener {
            startActivity(MapActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        weatherViewModel.getWeather(AreaCache.getAdCode(this))
    }

    override fun initViewModel(): BaseViewModel? {
        weatherViewModel = getViewModel(WeatherViewModel::class.java)
        weatherViewModel.forecastsBeanLiveData.observe(this, Observer {
            showWeather(it)
        })
        return weatherViewModel
    }

    private fun showWeather(forecastsBean: ForecastsBean) {
        tv_city.text = forecastsBean.city
        castsBeanList.clear()
        castsBeanList.addAll(forecastsBean.casts)
        weatherAdapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

}