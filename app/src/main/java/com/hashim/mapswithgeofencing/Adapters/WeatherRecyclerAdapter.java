package com.hashim.mapswithgeofencing.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.Models.WeatherModel.WeatherModelToShow;
import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;
import com.hashim.mapswithgeofencing.R;
import com.hashim.mapswithgeofencing.ViewHolders.TodaysWeatherVH;
import com.hashim.mapswithgeofencing.ViewHolders.WeeklyWeatherVH;
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
        View hView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_weather1, parent, false);
        return new WeeklyWeatherVH(hView);
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
        UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hDayTv, hdate);
        switch (hTempUnit) {
            case Constants.H_CELCIUS_UNIT:
                UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hMaxTempTv,
                        "Temp:\n" + hMaxTemp + hContext.getString(R.string.degree_symbol));
                break;
            case Constants.H_FARENHEIT_UNIT:
                UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hMaxTempTv,
                        "Temp:\n" + hMaxTemp + hContext.getString(R.string.farenheit_symbol));
                break;
            case Constants.H_KELVIL_UNIT:
                UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hMaxTempTv,
                        "Temp:\n" + hMaxTemp + hContext.getString(R.string.kelvin_symbol));
                break;
        }
        UIHelper.hMakeVisibleInVisible(hWeeklyWeatherVH.hTimeTv, Constants.H_INVISIBLE);
        UIHelper.hSetTextToTextView(hWeeklyWeatherVH.hDescriptionTv, hDescription);
        Picasso.get()
                .load(Constants.H_ICON_URL + hIcon + ".png")
                .resize(200, 200)
                .centerCrop()
                .into(hWeeklyWeatherVH.hImageTv);
    }

    private void hBindTodaysVh(TodaysWeatherVH todaysWeatherVh, final int position) {
        WeatherModelToShow hWeatherModelToShow = hWeatherModelToShowList.get(position);
        String hTemp = hWeatherModelToShow.gethMaxTemp();
        String hIcon = hWeatherModelToShow.gethIcon();
        String hTime = hWeatherModelToShow.gethTime();
        String hDescription = hWeatherModelToShow.gethDescription();
        UIHelper.hSetTextToTextView(todaysWeatherVh.hIrwTimeTv, hTime);
        UIHelper.hSetTextToTextView(todaysWeatherVh.hDescriptionTv, hDescription);
        switch (hTempUnit) {
            case Constants.H_CELCIUS_UNIT:
                UIHelper.hSetTextToTextView(todaysWeatherVh.hIrwTemp,
                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.degree_symbol)));
                break;
            case Constants.H_FARENHEIT_UNIT:
                UIHelper.hSetTextToTextView(todaysWeatherVh.hIrwTemp,
                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.farenheit_symbol)));
                break;
            case Constants.H_KELVIL_UNIT:
                UIHelper.hSetTextToTextView(todaysWeatherVh.hIrwTemp,
                        "Temp:\n" + hTemp.concat(hContext.getString(R.string.kelvin_symbol)));
                break;
        }
        Picasso.get()
                .load(Constants.H_ICON_URL + hIcon + ".png")
                .resize(200, 200)
                .centerCrop()
                .into(todaysWeatherVh.hIrwIv);
    }


    @Override
    public int getItemCount() {
        return hWeatherModelToShowList.size();
    }


    @NonNull
    private RecyclerView.ViewHolder hGetTodaysWeatherVH(ViewGroup parent) {
        View hView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_weather, parent, false);
        return new TodaysWeatherVH(hView);
    }
}
