package github.leavesczy.reactivehttpsamples.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesczy.reactivehttpsamples.adapter.AreaAdapter
import github.leavesczy.reactivehttpsamples.base.BaseFragment
import github.leavesczy.reactivehttpsamples.core.viewmodel.weather.CountyViewModel
import github.leavesczy.reactivehttpsamples.databinding.FragmentAreaBinding

/**
 * @Author: leavesCZY
 * @Date: 2022/4/23 21:08
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class CountyFragment : BaseFragment() {

    companion object {

        private const val keyRequestParameter = "keyRequestParameter"

        fun newInstance(city: String): Fragment {
            val fragment = CountyFragment()
            val bundle = Bundle()
            bundle.putString(keyRequestParameter, city)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var bind: FragmentAreaBinding? = null

    private val mBind: FragmentAreaBinding
        get() = bind!!

    private val city by lazy {
        requireArguments().getString(keyRequestParameter) ?: throw IllegalArgumentException()
    }

    private val countyViewModel by getViewModelInstance(create = {
        CountyViewModel(city)
    }) {
        countyLiveData.observe(viewLifecycleOwner) {
            val list = it
            if (list.isEmpty()) {
                onClickCounty(city)
            } else {
                val placeAdapter = AreaAdapter(it, object : AreaAdapter.OnClickListener {
                    override fun onClick(position: Int) {
                        onClickCounty(list[position].adcode)
                    }
                })
                mBind.rvPlaceList.adapter = placeAdapter
            }
        }
    }

    private fun onClickCounty(adCode: String) {
        val bundle = Bundle()
        bundle.putString(AreaActivity.parameterKey, adCode)
        parentFragmentManager.setFragmentResult(AreaActivity.onClickCountyKey, bundle)
    }

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
        mBind.rvPlaceList.layoutManager = LinearLayoutManager(requireActivity())
        mBind.tvTopBarTitle.text = "区县"
        countyViewModel.getCounty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

}