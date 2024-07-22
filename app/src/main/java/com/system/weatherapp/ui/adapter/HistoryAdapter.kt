package com.system.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.system.weatherapp.R
import com.system.weatherapp.data.models.WeatherResponse
import com.system.weatherapp.databinding.ItemWeatherHistoryBinding

class HistoryAdapter : ListAdapter<WeatherResponse, HistoryAdapter.HistoryViewHolder>(
    WeatherResponseDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemWeatherHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class HistoryViewHolder(private val binding: ItemWeatherHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherResponse: WeatherResponse) {
            binding.weatherResponse = weatherResponse
            binding.executePendingBindings()
            Glide.with(binding.root.context)
                .load(weatherResponse.weatherIcon)
                .placeholder(R.drawable.sun)
                .into( binding.imageWeatherIconHistory)
        }
    }

    class WeatherResponseDiffCallback : DiffUtil.ItemCallback<WeatherResponse>() {
        override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
            return oldItem == newItem
        }
    }
}
