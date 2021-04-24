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
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.WeatherFragmentBinding
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent.OnFetchForecast
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent.OnFetchWeather
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState.*
import com.hashim.mapswithgeofencing.ui.weather.WeatherAdapter.Companion.H_TODAYS_RECYCLER
import com.hashim.mapswithgeofencing.ui.weather.WeatherAdapter.Companion.H_WEEKLY_RECYCLER
import com.hashim.mapswithgeofencing.utils.GlideUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var hTodaysAdapter: WeatherAdapter
    private lateinit var hWeeklyAdapter: WeatherAdapter
    private lateinit var hWeatherViewModel: WeatherViewModel
    private lateinit var hWeatherFragmentBinding: WeatherFragmentBinding
    val hArguments: WeatherFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }
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
                        hLat = hArguments.hCurrentLocation?.hLat,
                        hLng = hArguments.hCurrentLocation?.hLng
                )
        )

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            hWeatherViewModel.hSetStateEvent(
                    OnFetchForecast(
                            hLat = hArguments.hCurrentLocation?.hLat,
                            hLng = hArguments.hCurrentLocation?.hLng
                    )
            )
        }, 500)

    }

    private fun hSetupView() {

        hWeatherFragmentBinding.hTodayWeatherTv.paintFlags = (hWeatherFragmentBinding.hTodayWeatherTv.paintFlags
                or Paint.UNDERLINE_TEXT_FLAG)
        hWeatherFragmentBinding.hWeekWeatherTv.paintFlags = (hWeatherFragmentBinding.hTodayWeatherTv.paintFlags
                or Paint.UNDERLINE_TEXT_FLAG)

        hWeatherFragmentBinding.hTodayWeatherTv.text = getString(R.string.today_s_weather)
        hWeatherFragmentBinding.hWeekWeatherTv.text = getString(R.string.weekly_weather)


    }


    private fun hInitRecyclerView() {
        hTodaysAdapter = WeatherAdapter(H_TODAYS_RECYCLER, requireContext())
        hWeeklyAdapter = WeatherAdapter(H_WEEKLY_RECYCLER, requireContext())

        hWeatherFragmentBinding.hTodaysRv.apply {
            layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            adapter = hTodaysAdapter
        }
        hWeatherFragmentBinding.hWeeklyRv.apply {
            layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            adapter = hWeeklyAdapter
        }

    }

    private fun hSubscribeObservers() {
        hWeatherViewModel.hDataState.observe(viewLifecycleOwner) {
            it.hData?.let {
                it.hWeatherFields.let {
                    it.hWeatherVS.let {
                        hWeatherViewModel.hSetWeatherData(it)

                    }
                    it.hForecastVS.let {
                        if (it != null) {
                            hWeatherViewModel.hSetForecastData(it)
                        }
                    }
                }
            }
        }

        hWeatherViewModel.hWeatherViewState.observe(viewLifecycleOwner) { weatherViewState ->

            weatherViewState.hWeatherFields.hWeatherVS?.let {
                hSetNowWeather(it)
            }
            weatherViewState.hWeatherFields.hForecastVS?.let {
                it.hWeeksList?.let {
                    hSetWeeksForecast(it)
                }
                it.hTodaysList?.let {
                    hSetTodaysForeCast(it)
                }
            }
        }
    }

    private fun hSetWeeksForecast(hWeeksForecast: List<WeekForecast>) {
        hWeeklyAdapter.hSetData(hWeeksForecast)
    }

    private fun hSetTodaysForeCast(hTodaysForeCast: List<TodaysForeCast>) {
        hTodaysAdapter.hSetData(hTodaysForeCast)
    }

    private fun hSetNowWeather(weatherVS: WeatherVS) {


        hWeatherFragmentBinding.hWeatherHeader.hPressureDetailTv.text = "${weatherVS.hPressure} Pa"
        hWeatherFragmentBinding.hWeatherHeader.hHumidityTv.text = "${weatherVS.hHumidity} g/ ${getString(R.string.cubic_meter)}"

        hWeatherFragmentBinding.hWeatherHeader.hCurrrentDateTv.text = weatherVS.hDay
        hWeatherFragmentBinding.hWeatherHeader.hCurrentTimeTv.text = weatherVS.hTime
        hWeatherFragmentBinding.hWeatherHeader.hCurrentWeatherDetailTv.text = weatherVS.hDescription
        hWeatherFragmentBinding.hWeatherHeader.hCurrrentTempTv.text = weatherVS.hTemperature

        weatherVS.hIconUrl?.let {
            GlideUtils.hSetImage(
                    hContext = requireContext(),
                    hUrl = it,
                    hImageView = hWeatherFragmentBinding.hWeatherHeader.hCurrentWeatherIcon
            )
        }
    }

}

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