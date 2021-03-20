/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Weather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity /*implements
         RecyclerInterface */{

    private Double hLat;
    private Double hLng;
    private final String hOnlyDateFormat = "dd";
    private final String hYearMonthDayHrsMinsSecFormat = "yyyy-MM-dd HH:mm:ss";
    private final String hYearMonthDayFormat = "yyyy-MM-dd";
    private final String hHrsMinSecsFormat = "HH:mm:ss";
    private final String hJstDayNameFormat = "EEEE";
    private final String hDayNameMonthDate = "EEEE, MMMM d";
    private final String hHrsMinTime = " h:mm aa";
    private String hLastDayName;
//    ActivityWeatherBinding hActivityWeatherBinding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather);
//        hActivityWeatherBinding = ActivityWeatherBinding.inflate(getLayoutInflater());
//        setContentView(hActivityWeatherBinding.getRoot());
//
//        UIHelper.hOreoOrientationCheck(this);
//
//        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
////        hToolBarHelper.hSetToolbar(hActivityWeatherBinding.hAwAppbar.toolbar);
////        hToolBarHelper.hSetToolbarTitle(hActivityWeatherBinding.hAwAppbar.toolbarTitle, "Weather Updates");
//
//
//        hGetIntentData();
//        hGeoCodeLatLng();

    }

    private void hLoadWeather() {
//        RequestWeatherTask hRequestWeatherTask =
//                new RequestWeatherTask(this, hLat, hLng, Constants.H_GET_WEATHER);
//        hRequestWeatherTask.execute();
//
//        RequestWeatherTask hRequestWeatherTask1 =
//                new RequestWeatherTask(this, hLat, hLng, Constants.H_GET_FORECAST);
//        hRequestWeatherTask1.execute();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
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
//                    UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hCurrrentCityTv, city + ", " + country);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_settings, menu);
//        return true;
//
//    }
//
//    private void hSetUpRecyclerView(int recyclerNumber, List<WeatherModelToShow> hWeatherModelToShowList) {
//        switch (recyclerNumber) {
//            case Constants.H_TODAYS_RECYCLER:
//                LinearLayoutManager layoutManager = new LinearLayoutManager(this,
//                        LinearLayoutManager.HORIZONTAL, false);
//                WeatherRecyclerAdapter weatherRecyclerAdapter = new WeatherRecyclerAdapter(this,
//                        hWeatherModelToShowList, Constants.H_TODAYS_RECYCLER);
//              hActivityWeatherBinding.  hTodaysRv.setLayoutManager(layoutManager);
//                hActivityWeatherBinding.    hTodaysRv.setAdapter(weatherRecyclerAdapter);
//                break;
//            case Constants.H_WEEKLY_RECYCLER:
//                LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,
//                        LinearLayoutManager.HORIZONTAL, false);
//                WeatherRecyclerAdapter weatherRecyclerAdapter1 = new WeatherRecyclerAdapter(this,
//                        hWeatherModelToShowList, Constants.H_WEEKLY_RECYCLER);
//                hActivityWeatherBinding.   hWeeklyRv.setLayoutManager(layoutManager1);
//                hActivityWeatherBinding.   hWeeklyRv.setAdapter(weatherRecyclerAdapter1);
//                break;
//        }
//
//
//    }
//
//
//    private void hGetIntentData() {
//        Bundle hBundle = getIntent().getExtras();
//        if (hBundle != null) {
//            hLat = hBundle.getDouble(Constants.H_LATITUDE);
//            hLng = hBundle.getDouble(Constants.H_LONGITUDE);
//        }
//    }
//
////    @Override
////    public void onWeatherFetch(Object object, String hDecodedString) {
////        if (object instanceof LatLonReturnModel) {
////            LatLonReturnModel hLatLonReturnModel = (LatLonReturnModel) object;
////            hSetCurrentWeatherData(hLatLonReturnModel);
////        }
////        if (object instanceof WeatherMainReturnResponse) {
////            WeatherMainReturnResponse hWeatherMainReturnResponse = (WeatherMainReturnResponse) object;
////            hSetForeCast(hWeatherMainReturnResponse);
////        }
////
////    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        UIHelper.hOreoOrientationCheck(this);
//        hLoadWeather();
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
//    }
//
//    private void hSetCurrentWeatherData(LatLonReturnModel hLatLonReturnModel) {
//
//        Calendar hCalendar = Calendar.getInstance();
//
//
//        hActivityWeatherBinding.  hTodayWeatherTv.setPaintFlags(   hActivityWeatherBinding.hTodayWeatherTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        hActivityWeatherBinding.  hWeekWeatherTv.setPaintFlags(   hActivityWeatherBinding.hTodayWeatherTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//
//        UIHelper.hSetTextToTextView(   hActivityWeatherBinding.hTodayWeatherTv, getString(R.string.today_s_weather));
//        UIHelper.hSetTextToTextView(   hActivityWeatherBinding.hWeekWeatherTv, getString(R.string.weekly_weather));
//
//
//        SimpleDateFormat hDayMonthSimpleDateFormat = new SimpleDateFormat(hDayNameMonthDate, Locale.getDefault());
//        SimpleDateFormat hTimeSimpleDateFormat = new SimpleDateFormat(hHrsMinTime, Locale.getDefault());
//
//
//        String hDayMonthString = hDayMonthSimpleDateFormat.format(hCalendar.getTime());
//        String hTimeString = hTimeSimpleDateFormat.format(hCalendar.getTime());
//        String hIcon = hLatLonReturnModel.getWeather().get(0).getIcon();
//
//        String hPressure = String.valueOf(hLatLonReturnModel.getMain().getPressure());
//        String hHumidity = String.valueOf(hLatLonReturnModel.getMain().getHumidity());
//        UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hPressureDetailTv, hPressure + " Pa");
//        UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hHumidityTv, hHumidity + "g/" + getString(R.string.cubic_meter));
//
//        UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hCurrrentDateTv, hDayMonthString);
//        UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hCurrentTimeTv, hTimeString);
//        UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hCurrentWeatherDetailTv,
//                hLatLonReturnModel.getWeather().get(0).getDescription());
//        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(this);
//        switch (hSettingsPrefrences.hGetTempUnit()) {
//            case Constants.H_FARENHEIT_UNIT:
//                UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hCurrrentTempTv,
//                        String.valueOf(hLatLonReturnModel.getMain().getTemp().intValue()).
//                                concat(getString(R.string.farenheit_symbol)));
//                break;
//            case Constants.H_CELCIUS_UNIT:
//                UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hCurrrentTempTv,
//                        String.valueOf(hLatLonReturnModel.getMain().getTemp().intValue()).
//                                concat(getString(R.string.degree_symbol)));
//                break;
//            case Constants.H_KELVIL_UNIT:
//                UIHelper.hSetTextToTextView(hActivityWeatherBinding.hWeatherHeader.hCurrrentTempTv,
//                        String.valueOf(hLatLonReturnModel.getMain().getTemp().intValue()).
//                                concat(getString(R.string.kelvin_symbol)));
//                break;
//        }
//        Picasso.get().load(Constants.H_ICON_URL + hIcon + ".png")
//                .resize(200, 200).centerCrop()
//                .into(hActivityWeatherBinding.hWeatherHeader.hCurrentWeatherIcon);
//
//    }
//
//    @Override
//    public void hOnClickListener(View hClickedView, int hClickedPosition) {
//
//    }
//
//    @Override
//    public void hOnClickListener(View v, int position, String hText) {
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        }
//        if (item.getItemId() == R.id.settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
