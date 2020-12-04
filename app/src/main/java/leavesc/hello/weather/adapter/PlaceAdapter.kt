package leavesc.hello.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import leavesc.hello.weather.R
import leavesc.hello.weather.core.model.DistrictBean

/**
 * 作者：leavesC
 * 时间：2019/6/1 9:58
 * 描述：
 */
class PlaceAdapter(private val dataList: List<DistrictBean>, private val onClickListener: OnClickListener) :
        RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    interface OnClickListener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))
    }

    override fun getItemCount() = if (dataList.isNullOrEmpty()) 0 else dataList.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.tv_placeName.text = dataList[position].name
        holder.tv_placeName.setOnClickListener {
            onClickListener.onClick(holder.adapterPosition)
        }
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_placeName: TextView = itemView.findViewById(R.id.tv_placeName)

    }

}