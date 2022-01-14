package github.leavesczy.reactivehttpsamples.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesczy.reactivehttpsamples.adapter.PlaceAdapter
import github.leavesczy.reactivehttpsamples.base.BaseActivity
import github.leavesczy.reactivehttpsamples.core.mode.DistrictMode
import github.leavesczy.reactivehttpsamples.core.viewmodel.MapViewModel
import github.leavesczy.reactivehttpsamples.databinding.ActivityMapBinding

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class MapActivity : BaseActivity() {

    override val bind by getBind<ActivityMapBinding>()

    private val dataList = mutableListOf<DistrictMode>()

    private val mapViewModel by getViewModel<MapViewModel> {
        stateLiveData.observe(this@MapActivity, {
            when (it) {
                MapViewModel.TYPE_PROVINCE -> {
                    bind.tvTopBarTitle.text = "省份"
                }
                MapViewModel.TYPE_CITY -> {
                    bind.tvTopBarTitle.text = "城市"
                }
                MapViewModel.TYPE_COUNTY -> {
                    bind.tvTopBarTitle.text = "区县"
                }
            }
        })
        realLiveData.observe(it, {
            dataList.clear()
            dataList.addAll(it)
            placeAdapter.notifyDataSetChanged()
        })
        adCodeSelectedLiveData.observe(it, {
            val intent = Intent(this@MapActivity, WeatherActivity::class.java)
            intent.putExtra("adCode", it)
            startActivity(intent)
            finish()
        })
    }

    private val placeAdapter: PlaceAdapter =
        PlaceAdapter(dataList, object : PlaceAdapter.OnClickListener {
            override fun onClick(position: Int) {
                mapViewModel.onPlaceClicked(position)
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind.rvPlaceList.layoutManager = LinearLayoutManager(this)
        bind.rvPlaceList.adapter = placeAdapter
        mapViewModel.getProvince()
    }

    override fun onBackPressed() {
        if (mapViewModel.onBackPressed()) {
            super.onBackPressed()
        }
    }

}