package leavesc.hello.weather.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.layout_top_bar.*
import leavesc.hello.weather.R
import leavesc.hello.weather.adapter.PlaceAdapter
import leavesc.hello.weather.core.cache.AreaCache
import leavesc.hello.weather.core.http.viewmodel.BaseViewModel
import leavesc.hello.weather.core.model.DistrictBean
import leavesc.hello.weather.core.view.BaseActivity
import leavesc.hello.weather.core.viewmodel.MapViewModel
import leavesc.hello.weather.widget.CommonItemDecoration

/**
 * 作者：leavesC
 * 时间：2019/5/31 20:48
 * 描述：
 * GitHub：https://github.com/leavesC
 */
class MapActivity : BaseActivity() {

    private val dataList = mutableListOf<DistrictBean>()

    private val placeAdapter = PlaceAdapter(dataList, object : PlaceAdapter.OnClickListener {
        override fun onClick(position: Int) {
            mapViewModel.onPlaceClicked(position)
        }
    })

    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        rv_placeList.layoutManager = LinearLayoutManager(this)
        rv_placeList.addItemDecoration(
            CommonItemDecoration(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.divider_plan_detail
                ), LinearLayoutManager.VERTICAL
            )
        )
        rv_placeList.adapter = placeAdapter
        mapViewModel.getProvince()
    }

    override fun initViewModel(): BaseViewModel? {
        mapViewModel = getViewModel(MapViewModel::class.java)
        mapViewModel.stateLiveData.observe(this, Observer {
            when (it) {
                MapViewModel.TYPE_PROVINCE -> {
                    tv_topBarTitle.text = "省份"
                }
                MapViewModel.TYPE_CITY -> {
                    tv_topBarTitle.text = "城市"
                }
                MapViewModel.TYPE_COUNTY -> {
                    tv_topBarTitle.text = "区县"
                }
            }
        })
        mapViewModel.realLiveData.observe(this, Observer<List<DistrictBean>> {
            dataList.clear()
            dataList.addAll(it)
            placeAdapter.notifyDataSetChanged()
        })
        mapViewModel.adCodeSelectedLiveData.observe(this, Observer {
            it?.let { adCode ->
                AreaCache.saveAdCode(this@MapActivity, adCode)
            }
            startActivity(WeatherActivity::class.java)
            finish()
        })
        return mapViewModel
    }

    override fun onBackPressed() {
        if (mapViewModel.onBackPressed()) {
            super.onBackPressed()
        }
    }

}