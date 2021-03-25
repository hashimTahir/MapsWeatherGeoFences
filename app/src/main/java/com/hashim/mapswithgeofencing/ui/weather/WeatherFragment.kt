/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.weather

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.WeatherFragmentBinding
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent.OnFetchForecast
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent.OnFetchWeather
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WeatherFragment : Fragment() {

/*






//    private void hGeoCodeLatLng() {
//        try {
//            Geocoder geo = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geo.getFromLocation(hLat, hLng, 1);
//            if (addresses.isEmpty()) {
//
//            } else {
//
//                if (addresses.size() > 0) {
//
//
////                    String address = addresses.get(0).getAddressLine(0);
//
//                    String city = addresses.get(0).getLocality();
//                    String country = addresses.get(0).getCountryName();
////                    String state = addresses.get(0).getAdminArea();
////                    String postalCode = addresses.get(0).getPostalCode();
////                    String knownName = addresses.get(0).getFeatureName(); // On
////                    Address returnAddress = addresses.get(0);
//
//                    hWeatherFragmentBinding.hWeatherHeader.hCurrrentCityTv, city + ", " + country);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//

    */


    private lateinit var hWeatherViewModel: WeatherViewModel
    private lateinit var hWeatherFragmentBinding: WeatherFragmentBinding
    val hArguments: WeatherFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hWeatherFragmentBinding = WeatherFragmentBinding.inflate(
                inflater,
                container,
                false
        )
        return hWeatherFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hWeatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        hSetupView()

        hSubscribeObservers()

        hInitRecyclerView()

        hWeatherViewModel.hSetStateEvent(
                OnFetchWeather(
                        hLat = hArguments?.hCurrentLocation?.hLat,
                        hLng = hArguments?.hCurrentLocation?.hLng
                )
        )

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            hWeatherViewModel.hSetStateEvent(
                    OnFetchForecast(
                            hLat = hArguments?.hCurrentLocation?.hLat,
                            hLng = hArguments?.hCurrentLocation?.hLng
                    )
            )
        }, 500)

    }

    private fun hSetupView() {

        hWeatherFragmentBinding.hTodayWeatherTv.setPaintFlags(hWeatherFragmentBinding.hTodayWeatherTv.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        hWeatherFragmentBinding.hWeekWeatherTv.setPaintFlags(hWeatherFragmentBinding.hTodayWeatherTv.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        hWeatherFragmentBinding.hTodayWeatherTv.text = getString(R.string.today_s_weather)
        hWeatherFragmentBinding.hWeekWeatherTv.text = getString(R.string.weekly_weather)


    }

    private fun hInitRecyclerView() {

        hWeatherFragmentBinding.hTodaysRv.apply {
            layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            adapter = null
        }
        hWeatherFragmentBinding.hWeeklyRv.apply {
            layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            adapter = null
        }

    }

    private fun hSubscribeObservers() {
        hWeatherViewModel.hDataState.observe(viewLifecycleOwner) {
            it.hData?.let {
                it.hForecastFields?.let {
                    hWeatherViewModel.hSetForecastData(it)
                }
            }
            it.hData.let {
                it?.hWeatherFields?.let {
                    hWeatherViewModel.hSetWeatherData(it)
                }
            }
        }

        hWeatherViewModel.hWeatherViewState.observe(viewLifecycleOwner) { weatherViewState ->

            weatherViewState.hForecastFields.let { forecastFields ->
                forecastFields.hTodaysList?.let { hTodaysForecast ->
                    hSetTodaysForeCast(hTodaysForecast)
                }
                forecastFields.hWeeksList.let { hWeeksForecast ->
                    hSetWeeksForecast(hWeeksForecast)
                }
            }

            weatherViewState.hWeatherFields.let { weatherFields ->
                weatherFields.let {
                    hSetNowWeather(it)
                }
            }
        }
    }

    private fun hSetWeeksForecast(hWeeksForecast: List<WeatherViewState.WeekForecast>?) {
        Timber.d("hSetWeeksForecast ${hWeeksForecast?.size}")
    }

    private fun hSetTodaysForeCast(hTodaysForeCast: List<WeatherViewState.TodaysForeCast>) {
        Timber.d("hSetTodaysForeCast ${hTodaysForeCast.size}")
    }

    private fun hSetNowWeather(weather: WeatherViewState.WeatherFields) {


        hWeatherFragmentBinding.hWeatherHeader.hPressureDetailTv.text = "${weather.hPressure} Pa"
        hWeatherFragmentBinding.hWeatherHeader.hHumidityTv.text = "${weather.hHumidity} g/ ${getString(R.string.cubic_meter)}"

        hWeatherFragmentBinding.hWeatherHeader.hCurrrentDateTv.text = weather.hDay
        hWeatherFragmentBinding.hWeatherHeader.hCurrentTimeTv.text = weather.hTime
        hWeatherFragmentBinding.hWeatherHeader.hCurrentWeatherDetailTv.text = weather.hDescription
        hWeatherFragmentBinding.hWeatherHeader.hCurrrentTempTv.text = weather.hTemperature

        val hRequestOptions = RequestOptions()
                .override(200, 200)
                .centerCrop()
                .priority(Priority.HIGH)
        Glide.with(requireContext())
                .load(weather.hIconUrl)
                .apply(hRequestOptions)
                .into(hWeatherFragmentBinding.hWeatherHeader.hCurrentWeatherIcon)
    }

}