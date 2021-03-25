/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerWeather1Binding
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerWeatherBinding
import com.hashim.mapswithgeofencing.tobeDeleted.ViewHolders.TodaysWeatherVH
import com.hashim.mapswithgeofencing.tobeDeleted.ViewHolders.WeeklyWeatherVH

class WeatherAdapter(val adapterType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (adapterType) {
            H_TODAYS_RECYCLER -> return hGetTodaysVh(parent)
            H_WEEKLY_RECYCLER -> return hGetWeeklyVh(parent)
            else -> return hGetTodaysVh(parent)
        }
    }

    private fun hGetWeeklyVh(parent: ViewGroup): WeeklyWeatherVH {
        return WeeklyWeatherVH(
                ItemRecyclerWeather1Binding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        )
    }

    private fun hGetTodaysVh(parent: ViewGroup): TodaysWeatherVH {
        return TodaysWeatherVH(
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
                hBindTodaysVh()
            }
            H_WEEKLY_RECYCLER -> {
                hBindWeeklyVh()
            }
        }
    }

    private fun hBindWeeklyVh() {
//        WeatherModelToShow hWeatherModelToShow = hWeatherModelToShowList.get(position);
//        String hMaxTemp = hWeatherModelToShow.gethMaxTemp();
//        String hMinTemp = hWeatherModelToShow.gethMinTemp();
//        String hIcon = hWeatherModelToShow.gethIcon();
//        String hTime = hWeatherModelToShow.gethTime();
//        String hdate = hWeatherModelToShow.getHdate();
//        String hDescription = hWeatherModelToShow.gethDescription();
//        UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1DayTv, hdate);
//        switch (hTempUnit) {
//            case Constants.H_CELCIUS_UNIT:
//            UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1MaxTempTv,
//                    "Temp:\n" + hMaxTemp + hContext.getString(R.string.degree_symbol));
//            break;
//            case Constants.H_FARENHEIT_UNIT:
//            UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1MaxTempTv,
//                    "Temp:\n" + hMaxTemp + hContext.getString(R.string.farenheit_symbol));
//            break;
//            case Constants.H_KELVIL_UNIT:
//            UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1MaxTempTv,
//                    "Temp:\n" + hMaxTemp + hContext.getString(R.string.kelvin_symbol));
//            break;
//        }
//        UIHelper.hMakeVisibleInVisible(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1TimeTv, Constants.H_INVISIBLE);
//        UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1DescriptionTv, hDescription);
//        Picasso.get()
//                .load(Constants.H_ICON_URL + hIcon + ".png")
//                .resize(200, 200)
//                .centerCrop()
//                .into(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1ImageV);
    }

    private fun hBindTodaysVh() {
        //        WeatherModelToShow hWeatherModelToShow = hWeatherModelToShowList.get(position);
//        String hTemp = hWeatherModelToShow.gethMaxTemp();
//        String hIcon = hWeatherModelToShow.gethIcon();
//        String hTime = hWeatherModelToShow.gethTime();
//        String hDescription = hWeatherModelToShow.gethDescription();
//        UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTimeTv, hTime);
//        UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwDescriptionTv, hDescription);
//        switch (hTempUnit) {
//            case Constants.H_CELCIUS_UNIT:
//                UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTempTv,
//                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.degree_symbol)));
//                break;
//            case Constants.H_FARENHEIT_UNIT:
//                UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTempTv,
//                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.farenheit_symbol)));
//                break;
//            case Constants.H_KELVIL_UNIT:
//                UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTempTv,
//                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.kelvin_symbol)));
//                break;
//        }
//        Picasso.get()
//                .load(Constants.H_ICON_URL + hIcon + ".png")
//                .resize(200, 200)
//                .centerCrop()
//                .into(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwImageV);
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        const val H_TODAYS_RECYCLER = 1
        const val H_WEEKLY_RECYCLER = 2
    }
}