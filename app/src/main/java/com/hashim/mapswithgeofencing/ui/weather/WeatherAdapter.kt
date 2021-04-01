/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerWeather1Binding
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerWeatherBinding
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState.TodaysForeCast
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState.WeekForecast
import com.hashim.mapswithgeofencing.ui.viewholders.TodaysVh
import com.hashim.mapswithgeofencing.ui.viewholders.WeeklyVh
import com.hashim.mapswithgeofencing.utils.GlideUtils

class WeatherAdapter(
        val adapterType: Int,
        val hContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var hList = mutableListOf<Any>()
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        when (adapterType) {
            H_TODAYS_RECYCLER -> return hGetTodaysVh(parent)
            H_WEEKLY_RECYCLER -> return hGetWeeklyVh(parent)
            else -> return hGetTodaysVh(parent)
        }
    }

    private fun hGetWeeklyVh(parent: ViewGroup): WeeklyVh {
        return WeeklyVh(
                ItemRecyclerWeather1Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    private fun hGetTodaysVh(parent: ViewGroup): TodaysVh {
        return TodaysVh(
                ItemRecyclerWeatherBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (adapterType) {
            H_TODAYS_RECYCLER -> {
                hBindTodaysVh(holder as TodaysVh, hList.get(position) as TodaysForeCast)
            }
            H_WEEKLY_RECYCLER -> {
                hBindWeeklyVh(holder as WeeklyVh, hList.get(position) as WeekForecast)
            }
        }
    }

    private fun hBindWeeklyVh(weeklyWeatherVH: WeeklyVh, weekForecast: WeekForecast) {
        weeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1DayTv.text = weekForecast.time
//        TODO: Use temp unit here

        weeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1MaxTempTv.text = weekForecast.tempMax.toString()
        weeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1DescriptionTv.text = weekForecast.description
        weeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1TimeTv.visibility = View.INVISIBLE

        weekForecast.icon?.let {
            GlideUtils.hSetImage(
                    hUrl = it,
                    hContext = hContext,
                    hImageView = weeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1ImageV
            )
        }
    }

    private fun hBindTodaysVh(todaysWeatherVH: TodaysVh, todaysForeCast: TodaysForeCast) {
        todaysWeatherVH.hItemRecyclerWeatherBinding.hIrwTimeTv.text = todaysForeCast.time
        todaysWeatherVH.hItemRecyclerWeatherBinding.hIrwDescriptionTv.text = todaysForeCast.description
        todaysWeatherVH.hItemRecyclerWeatherBinding.hIrwTempTv.text = todaysForeCast.tempMax.toString()
        /*Todo:Use Temperatue*/

        todaysForeCast.icon?.let {
            GlideUtils.hSetImage(
                    hContext = hContext,
                    hImageView = todaysWeatherVH.hItemRecyclerWeatherBinding.hIrwImageV,
                    hUrl = it
            )
        }
    }

    override fun getItemCount(): Int {
        return hList.size
    }

    fun hSetData(list: List<Any>) {
        hList = list as MutableList<Any>
        notifyDataSetChanged()
    }

    companion object {
        const val H_TODAYS_RECYCLER = 1
        const val H_WEEKLY_RECYCLER = 2
    }
}