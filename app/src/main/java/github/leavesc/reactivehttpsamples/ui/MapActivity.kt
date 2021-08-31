package github.leavesc.reactivehttpsamples.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesc.reactivehttpsamples.adapter.PlaceAdapter
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.bean.DistrictBean
import github.leavesc.reactivehttpsamples.core.viewmodel.MapViewModel
import github.leavesc.reactivehttpsamples.databinding.ActivityMapBinding

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class MapActivity : BaseActivity() {

    override val bind by getBind<ActivityMapBinding>()

    private val dataList = mutableListOf<DistrictBean>()

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