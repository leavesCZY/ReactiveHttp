package github.leavesczy.reactivehttpsamples.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.leavesczy.reactivehttpsamples.R
import github.leavesczy.reactivehttpsamples.core.mode.CastsMode

/**
 * @Author: leavesCZY
 * @Date: 2020/12/3 15:16
 * @Desc:
 * @Github：https://github.com/leavesCZY
 */
class WeatherAdapter(private val districtsModeList: List<CastsMode>) :
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
        return districtsModeList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val bean = districtsModeList[position]
        holder.tvDate.text = bean.date
        holder.tvDayWeather.text = "白天天气：${bean.daytemp} ℃ ${bean.dayweather}"
        holder.tvNightWeather.text = "夜晚天气：${bean.nighttemp} ℃ ${bean.nightweather}"
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        val tvDayWeather: TextView = itemView.findViewById(R.id.tvDayWeather)

        val tvNightWeather: TextView = itemView.findViewById(R.id.tvNightWeather)

    }

}