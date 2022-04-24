package github.leavesczy.reactivehttpsamples.ui.weather

import android.os.Bundle
import androidx.fragment.app.commit
import github.leavesczy.reactivehttpsamples.R
import github.leavesczy.reactivehttpsamples.base.BaseActivity
import github.leavesczy.reactivehttpsamples.databinding.ActivityAreaBinding

/**
 * @Author: leavesCZY
 * @Date: 2020/10/26 15:31
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class AreaActivity : BaseActivity() {

    companion object {

        const val onClickProvinceKey = "onClickProvinceKey"

        const val onClickCityKey = "onClickCityKey"

        const val onClickCountyKey = "onClickCountyKey"

        const val parameterKey = "parameterKey"

    }

    override val bind by getBind<ActivityAreaBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.setFragmentResultListener(
            onClickProvinceKey,
            this
        ) { _, result ->
            showCityList(result.getString(parameterKey, ""))
        }
        supportFragmentManager.setFragmentResultListener(
            onClickCityKey,
            this
        ) { _, result ->
            showCountyList(result.getString(parameterKey, ""))
        }
        supportFragmentManager.setFragmentResultListener(
            onClickCountyKey,
            this
        ) { _, result ->
            WeatherActivity.navTo(this, result.getString(parameterKey, ""))
            finish()
        }
        showProvinceList()
    }

    private fun showProvinceList() {
        supportFragmentManager.commit {
            add(R.id.fragmentContainerView, ProvinceFragment())
            setReorderingAllowed(true)
        }
    }

    private fun showCityList(province: String) {
        supportFragmentManager.commit {
            add(R.id.fragmentContainerView, CityFragment.newInstance(province))
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun showCountyList(city: String) {
        supportFragmentManager.commit {
            add(R.id.fragmentContainerView, CountyFragment.newInstance(city))
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}