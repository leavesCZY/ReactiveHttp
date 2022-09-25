package github.leavesczy.reactivehttpsamples.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import github.leavesczy.reactivehttpsamples.adapter.AreaAdapter
import github.leavesczy.reactivehttpsamples.base.BaseFragment
import github.leavesczy.reactivehttpsamples.core.viewmodel.weather.ProvinceViewModel
import github.leavesczy.reactivehttpsamples.databinding.FragmentAreaBinding

/**
 * @Author: leavesCZY
 * @Date: 2022/4/23 21:07
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class ProvinceFragment : BaseFragment() {

    private var bind: FragmentAreaBinding? = null

    private val mBind: FragmentAreaBinding
        get() = bind!!

    private val provinceViewModel by viewModels<ProvinceViewModel>()

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
        provinceViewModel.provinceLiveData.observe(viewLifecycleOwner) {
            val list = it
            val placeAdapter = AreaAdapter(list, object : AreaAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    val bundle = Bundle()
                    bundle.putString(AreaActivity.parameterKey, list[position].adcode)
                    parentFragmentManager.setFragmentResult(AreaActivity.onClickProvinceKey, bundle)
                }
            })
            mBind.rvPlaceList.adapter = placeAdapter
        }
        mBind.rvPlaceList.layoutManager = LinearLayoutManager(requireActivity())
        mBind.tvTopBarTitle.text = "省份"
        provinceViewModel.getProvince()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

}