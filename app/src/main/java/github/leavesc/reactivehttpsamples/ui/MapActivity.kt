package github.leavesc.reactivehttpsamples.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesc.reactivehttpsamples.R
import github.leavesc.reactivehttpsamples.adapter.PlaceAdapter
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.bean.DistrictBean
import github.leavesc.reactivehttpsamples.core.viewmodel.MapViewModel
import github.leavesc.reactivehttpsamples.widget.CommonItemDecoration
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.layout_top_bar.*

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class MapActivity : BaseActivity() {

    private val dataList = mutableListOf<DistrictBean>()

    private val mapViewModel by getViewModel<MapViewModel> {
        stateLiveData.observe(this@MapActivity, {
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

    override fun onBackPressed() {
        if (mapViewModel.onBackPressed()) {
            super.onBackPressed()
        }
    }

}