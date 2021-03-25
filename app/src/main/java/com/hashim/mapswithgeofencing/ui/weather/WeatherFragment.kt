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
import com.hashim.mapswithgeofencing.Domain.model.Weather
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.WeatherFragmentBinding
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent.OnFetchForecast
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent.OnFetchWeather
import com.hashim.mapswithgeofencing.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private val hLat: Double? = null
    private val hLng: Double? = null
    private val hOnlyDateFormat = "dd"
    private val hYearMonthDayHrsMinsSecFormat = "yyyy-MM-dd HH:mm:ss"
    private val hYearMonthDayFormat = "yyyy-MM-dd"
    private val hHrsMinSecsFormat = "HH:mm:ss"
    private val hJstDayNameFormat = "EEEE"
    private val hDayNameMonthDate = "EEEE, MMMM d"
    private val hHrsMinTime = " h:mm aa"
    private val hLastDayName: String? = null
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


//    private void hSetForeCast(WeatherMainReturnResponse hWeatherMainReturnResponse) {
//        List<WeatherList> hWeatherLists = hWeatherMainReturnResponse.getWeatherList();
//        List<WeatherModelToShow> hTodaysList = new ArrayList<>();
//        List<WeatherModelToShow> hWeeklyList = new ArrayList<>();
//
//        Calendar hCalendarToday = Calendar.getInstance();
//        SimpleDateFormat hdateFormatter = new SimpleDateFormat(hOnlyDateFormat, Locale.getDefault());
//        int hTodaysDate = Integer.parseInt(hdateFormatter.format(hCalendarToday.getTime()));
//        for (WeatherList weatherList : hWeatherLists) {
//
////            "2018-11-08 12:00:00"
//
//            //format date from api
//
//            SimpleDateFormat hMainDayMonthSimpleDateFormat = new SimpleDateFormat(hYearMonthDayHrsMinsSecFormat, Locale.getDefault());
//            SimpleDateFormat hDayMonthSimpleDateFormat = new SimpleDateFormat(hYearMonthDayFormat, Locale.getDefault());
//            SimpleDateFormat hTimeFormatter = new SimpleDateFormat(hHrsMinTime, Locale.getDefault());
//            SimpleDateFormat hNameDayFormatter = new SimpleDateFormat(hJstDayNameFormat, Locale.getDefault());
//
//
//            Calendar hCalendar = Calendar.getInstance();
//            try {
//                hCalendar.setTime(hMainDayMonthSimpleDateFormat.parse(weatherList.getDtTxt()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            int hLiveDate = Integer.parseInt(hdateFormatter.format(hCalendar.getTime()));
//
//
//            if (hLiveDate <= hTodaysDate) {
//                String hDescription = weatherList.getWeather().get(0).getDescription();
//                String hdate1 = hDayMonthSimpleDateFormat.format(hCalendar.getTime());
//                String hTime = hTimeFormatter.format(hCalendar.getTime());
//                String hIcon = weatherList.getWeather().get(0).getIcon();
//                String hMaxTemp = String.valueOf(weatherList.getMain().getTempMax().intValue());
//                hTodaysList.add(new WeatherModelToShow(hdate1, hTime, hIcon, hMaxTemp, hDescription));
//
//            } else {
//                String hDayName = hNameDayFormatter.format(hCalendar.getTime());
//                if (hDayName.equals(hLastDayName)) {
//                    continue;
//                } else {
//                    String hTime = hTimeFormatter.format(hCalendar.getTime());
//                    String hDescription = weatherList.getWeather().get(0).getDescription();
//
//
//                    String hIcon = weatherList.getWeather().get(0).getIcon();
//                    String hMinTemp = String.valueOf(weatherList.getMain().getTempMin().intValue());
//                    String hMaxTemp = String.valueOf(weatherList.getMain().getTempMax().intValue());
//                    hWeeklyList.add(new WeatherModelToShow(hDayName, hTime, hIcon, hMaxTemp, hMinTemp, hDescription));
//                }
//                hLastDayName = hDayName;
//            }
//        }
//        if (hTodaysList.size() > 0) {
//            hSetUpRecyclerView(Constants.H_TODAYS_RECYCLER, hTodaysList);
//        } else {
//            LogToastSnackHelper.hMakeLongToast(this, "Unable to Retrieve weather,Plz try again");
//        }
//        if (hWeeklyList.size() > 0) {
//            hSetUpRecyclerView(Constants.H_WEEKLY_RECYCLER, hWeeklyList);
//        } else {
//            LogToastSnackHelper.hMakeLongToast(this, "Unable to Retrieve weather,Plz try again");
//
//        }
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

            Timber.d("Setting Data to view $weatherViewState")
            weatherViewState.hForecastFields.let { forecastFields ->
                forecastFields.hForecast?.let {
                    Timber.d("Forecast is $it")
                }
            }

            weatherViewState.hWeatherFields.let { weatherFields ->
                weatherFields.hWeather?.let {
                    hSetNowWeather(it)

                }
            }
        }
    }

    private fun hSetNowWeather(weather: Weather) {

        val hCalendar = Calendar.getInstance()


        hWeatherFragmentBinding.hTodayWeatherTv.setPaintFlags(hWeatherFragmentBinding.hTodayWeatherTv.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        hWeatherFragmentBinding.hWeekWeatherTv.setPaintFlags(hWeatherFragmentBinding.hTodayWeatherTv.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        hWeatherFragmentBinding.hTodayWeatherTv.text = getString(R.string.today_s_weather)
        hWeatherFragmentBinding.hWeekWeatherTv.text = getString(R.string.weekly_weather)


        val hDayMonthSimpleDateFormat = SimpleDateFormat(hDayNameMonthDate, Locale.getDefault())
        val hTimeSimpleDateFormat = SimpleDateFormat(hHrsMinTime, Locale.getDefault())


        val hDayMonthString = hDayMonthSimpleDateFormat.format(hCalendar.time)
        val hTimeString = hTimeSimpleDateFormat.format(hCalendar.time)
        val hIcon: String = weather.icon!!
        val hIconUrl = String.format(Constants.H_ICON_URL, hIcon)
        val hPressure: String = weather.pressure.toString()
        val hHumidity: String = weather.humidity.toString()
        val hCountry: String = weather.country

        hWeatherFragmentBinding.hWeatherHeader.hPressureDetailTv.text = "$hPressure Pa"
        hWeatherFragmentBinding.hWeatherHeader.hHumidityTv.text = "$hHumidity g/ ${getString(R.string.cubic_meter)}"

        hWeatherFragmentBinding.hWeatherHeader.hCurrrentDateTv.text = hDayMonthString
        hWeatherFragmentBinding.hWeatherHeader.hCurrentTimeTv.text = hTimeString
        hWeatherFragmentBinding.hWeatherHeader.hCurrentWeatherDetailTv.text = weather.description
        hWeatherFragmentBinding.hWeatherHeader.hCurrrentTempTv.text = weather.temp.toString()

        val hRequestOptions = RequestOptions()
                .override(200, 200)
                .centerCrop()
                .priority(Priority.HIGH)
       Glide.with(requireContext())
                .load(hIconUrl)
                .apply(hRequestOptions)
                .into(hWeatherFragmentBinding.hWeatherHeader.hCurrentWeatherIcon)
    }

}