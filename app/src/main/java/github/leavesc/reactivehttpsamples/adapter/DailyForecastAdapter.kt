package github.leavesc.reactivehttpsamples.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.leavesc.reactivehttpsamples.R
import github.leavesc.reactivehttpsamples.core.bean.CastsBean

/**
 * @Author: leavesC
 * @Date: 2020/12/3 15:16
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class WeatherAdapter(private val districtsBeanList: List<CastsBean>) :
        RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_daily_forecast,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return districtsBeanList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val bean = districtsBeanList[position]
        holder.tv_date.text = bean.date
        holder.tv_dayWeather.text = "白天天气：${bean.daytemp} ℃ ${bean.dayweather}"
        holder.tv_nightWeather.text = "夜晚天气：${bean.nighttemp} ℃ ${bean.nightweather}"
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv_date: TextView = itemView.findViewById(R.id.tv_date)

        val tv_dayWeather: TextView = itemView.findViewById(R.id.tv_dayWeather)

        val tv_nightWeather: TextView = itemView.findViewById(R.id.tv_nightWeather)

    }

}