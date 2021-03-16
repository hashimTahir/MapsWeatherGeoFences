package com.hashim.mapswithgeofencing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.Models.WeatherModel.WeatherModelToShow;
import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.ViewHolders.TodaysWeatherVH;
import com.hashim.mapswithgeofencing.ViewHolders.WeeklyWeatherVH;
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerWeather1Binding;
import com.hashim.mapswithgeofencing.databinding.ItemRecyclerWeatherBinding;
import com.squareup.picasso.Picasso;

import java.util.List;


public class WeatherRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context hContext;
    List<WeatherModelToShow> hWeatherModelToShowList;
    int hRecyclerType;
    SettingsPrefrences hSettingsPrefrences;
    private final int hTempUnit;


    public WeatherRecyclerAdapter(Context context, List<WeatherModelToShow> weatherModelToShowList, int recyclerType) {
        hContext = context;
        this.hRecyclerType = recyclerType;
        hWeatherModelToShowList = weatherModelToShowList;
        RecyclerInterface hRecyclerInterface = (RecyclerInterface) context;
        hSettingsPrefrences = new SettingsPrefrences(hContext);
        hTempUnit = hSettingsPrefrences.hGetTempUnit();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (hRecyclerType) {
            case Constants.H_TODAYS_RECYCLER:
                return hGetTodaysWeatherVH(parent);
            case Constants.H_WEEKLY_RECYCLER:
                return hGetWeeklyWeatherVH(parent);
        }
        return null;
    }

    private RecyclerView.ViewHolder hGetWeeklyWeatherVH(ViewGroup parent) {
        return new WeeklyWeatherVH(
                ItemRecyclerWeather1Binding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (hRecyclerType) {
            case Constants.H_TODAYS_RECYCLER:
                TodaysWeatherVH hTodaysWeatherVH = (TodaysWeatherVH) holder;
                hBindTodaysVh(hTodaysWeatherVH, position);
                break;
            case Constants.H_WEEKLY_RECYCLER:
                WeeklyWeatherVH hWeeklyWeatherVH = (WeeklyWeatherVH) holder;
                hBindWeeklyVH(hWeeklyWeatherVH, position);
                break;
        }
    }

    private void hBindWeeklyVH(WeeklyWeatherVH hWeeklyWeatherVH, int position) {
        WeatherModelToShow hWeatherModelToShow = hWeatherModelToShowList.get(position);
        String hMaxTemp = hWeatherModelToShow.gethMaxTemp();
        String hMinTemp = hWeatherModelToShow.gethMinTemp();
        String hIcon = hWeatherModelToShow.gethIcon();
        String hTime = hWeatherModelToShow.gethTime();
        String hdate = hWeatherModelToShow.getHdate();
        String hDescription = hWeatherModelToShow.gethDescription();
        UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1DayTv, hdate);
        switch (hTempUnit) {
            case Constants.H_CELCIUS_UNIT:
                UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1MaxTempTv,
                        "Temp:\n" + hMaxTemp + hContext.getString(R.string.degree_symbol));
                break;
            case Constants.H_FARENHEIT_UNIT:
                UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1MaxTempTv,
                        "Temp:\n" + hMaxTemp + hContext.getString(R.string.farenheit_symbol));
                break;
            case Constants.H_KELVIL_UNIT:
                UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1MaxTempTv,
                        "Temp:\n" + hMaxTemp + hContext.getString(R.string.kelvin_symbol));
                break;
        }
        UIHelper.hMakeVisibleInVisible(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1TimeTv, Constants.H_INVISIBLE);
        UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1DescriptionTv, hDescription);
        Picasso.get()
                .load(Constants.H_ICON_URL + hIcon + ".png")
                .resize(200, 200)
                .centerCrop()
                .into(hWeeklyWeatherVH.hItemRecyclerWeather1Binding.hIrw1ImageV);
    }

    private void hBindTodaysVh(TodaysWeatherVH todaysWeatherVh, final int position) {
        WeatherModelToShow hWeatherModelToShow = hWeatherModelToShowList.get(position);
        String hTemp = hWeatherModelToShow.gethMaxTemp();
        String hIcon = hWeatherModelToShow.gethIcon();
        String hTime = hWeatherModelToShow.gethTime();
        String hDescription = hWeatherModelToShow.gethDescription();
        UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTimeTv, hTime);
        UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwDescriptionTv, hDescription);
        switch (hTempUnit) {
            case Constants.H_CELCIUS_UNIT:
                UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTempTv,
                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.degree_symbol)));
                break;
            case Constants.H_FARENHEIT_UNIT:
                UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTempTv,
                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.farenheit_symbol)));
                break;
            case Constants.H_KELVIL_UNIT:
                UIHelper.hSetTextToTextView(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwTempTv,
                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.kelvin_symbol)));
                break;
        }
        Picasso.get()
                .load(Constants.H_ICON_URL + hIcon + ".png")
                .resize(200, 200)
                .centerCrop()
                .into(todaysWeatherVh.hItemRecyclerWeatherBinding.hIrwImageV);
    }


    @Override
    public int getItemCount() {
        return hWeatherModelToShowList.size();
    }


    @NonNull
    private RecyclerView.ViewHolder hGetTodaysWeatherVH(ViewGroup parent) {
        return new TodaysWeatherVH(
                ItemRecyclerWeatherBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }
}
