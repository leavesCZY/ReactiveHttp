package github.leavesczy.reactivehttpsamples.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesczy.reactivehttpsamples.adapter.AreaAdapter
import github.leavesczy.reactivehttpsamples.base.BaseFragment
import github.leavesczy.reactivehttpsamples.core.viewmodel.weather.CityViewModel
import github.leavesczy.reactivehttpsamples.databinding.FragmentAreaBinding

/**
 * @Author: leavesCZY
 * @Date: 2022/4/23 21:08
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class CityFragment : BaseFragment() {

    companion object {

        private const val keyRequestParameter = "keyRequestParameter"

        fun newInstance(province: String): Fragment {
            val fragment = CityFragment()
            val bundle = Bundle()
            bundle.putString(keyRequestParameter, province)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var bind: FragmentAreaBinding? = null

    private val mBind: FragmentAreaBinding
        get() = bind!!

    private val province by lazy {
        requireArguments().getString(keyRequestParameter) ?: throw IllegalArgumentException()
    }

    private val cityViewModel by viewModels<CityViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CityViewModel(province) as T
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentAreaBinding.inflate(inflater, container, false)
        return mBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityViewModel.cityLiveData.observe(viewLifecycleOwner) {
            val list = it
            val placeAdapter = AreaAdapter(list, object : AreaAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    val bundle = Bundle()
                    bundle.putString(AreaActivity.parameterKey, list[position].adcode)
                    parentFragmentManager.setFragmentResult(AreaActivity.onClickCityKey, bundle)
                }
            })
            mBind.rvPlaceList.adapter = placeAdapter
        }
        mBind.rvPlaceList.layoutManager = LinearLayoutManager(requireActivity())
        mBind.tvTopBarTitle.text = "城市"
        cityViewModel.getCity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

}