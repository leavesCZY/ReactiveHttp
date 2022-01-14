package github.leavesczy.reactivehttpsamples.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.leavesczy.reactivehttpsamples.R
import github.leavesczy.reactivehttpsamples.core.mode.DistrictMode

/**
 * @Author: leavesCZY
 * @Date: 2020/12/3 15:16
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class PlaceAdapter(
    private val dataList: List<DistrictMode>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    interface OnClickListener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        )
    }

    override fun getItemCount() = if (dataList.isNullOrEmpty()) 0 else dataList.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.tvPlaceName.text = dataList[position].name
        holder.tvPlaceName.setOnClickListener {
            onClickListener.onClick(holder.adapterPosition)
        }
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvPlaceName: TextView = itemView.findViewById(R.id.tvPlaceName)

    }

}