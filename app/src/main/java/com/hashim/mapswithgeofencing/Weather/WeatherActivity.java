package com.hashim.mapswithgeofencing.Weather;

import android.content.Intent;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.hashim.mapswithgeofencing.Activities.SettingsActivity;
import com.hashim.mapswithgeofencing.Adapters.WeatherRecyclerAdapter;
import com.hashim.mapswithgeofencing.Helper.Constants;
import com.hashim.mapswithgeofencing.Helper.LogToastSnackHelper;
import com.hashim.mapswithgeofencing.Helper.ToolBarHelper;
import com.hashim.mapswithgeofencing.Helper.UIHelper;
import com.hashim.mapswithgeofencing.Interfaces.RecyclerInterface;
import com.hashim.mapswithgeofencing.Models.WeatherModel.LatLngReturnModel.LatLonReturnModel;
import com.hashim.mapswithgeofencing.Models.WeatherModel.ListWeatherModel.WeatherList;
import com.hashim.mapswithgeofencing.Models.WeatherModel.ListWeatherModel.WeatherMainReturnResponse;
import com.hashim.mapswithgeofencing.Models.WeatherModel.WeatherModelToShow;
import com.hashim.mapswithgeofencing.Prefrences.SettingsPrefrences;
import com.hashim.mapswithgeofencing.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements
        WeatherAysncCallBack, RecyclerInterface {
    @BindView(R.id.toolbar_title)
    TextView hToolbarTitle;

    @BindView(R.id.hAwAppbar)
    Toolbar hToolbar;

    @BindView(R.id.hTodaysRv)
    RecyclerView hTodaysRv;

    @BindView(R.id.hWeeklyRv)
    RecyclerView hWeeklyRv;

    @BindView(R.id.hCurrrentTempTv)
    TextView hCurrrentTempTv;

    @BindView(R.id.hCurrrentDateTv)
    TextView hCurrrentDateTv;

    @BindView(R.id.hCurrrentCityTv)
    TextView hCurrrentCityTv;

    @BindView(R.id.hCurrentTimeTv)
    TextView hCurrentTimeTv;

    @BindView(R.id.hCurrentWeatherIcon)
    ImageView hCurrentWeatherIcon;

    @BindView(R.id.hCurrentWeatherDetailTv)
    TextView hCurrentWeatherDetailTv;


    @BindView(R.id.hWeatherHeader)
    ConstraintLayout hCurrentWeatherLayout;

    @BindView(R.id.hTodayWeatherTv)
    TextView hTodayWeatherTv;

    @BindView(R.id.hWeekWeatherTv)
    TextView hWeekWeatherTv;

    @BindView(R.id.hPressureDetailTv)
    TextView hPressureDetailTv;

    @BindView(R.id.hHumidityTv)
    TextView hHumidityTv;

    private Double hLat;
    private Double hLng;
    private final String hOnlyDateFormat = "dd";
    private final String hYearMonthDayHrsMinsSecFormat = "yyyy-MM-dd HH:mm:ss";
    private final String hYearMonthDayFormat = "yyyy-MM-dd";
    private final String hHrsMinSecsFormat = "HH:mm:ss";
    private final String hJstDayNameFormat = "EEEE";
    private final String hDayNameMonthDate = "EEEE, MMMM d";
    private final String hHrsMinTime = " h:mm aa";
    private H_InterstetialAdd h_interstetialAdd;
    private String hLastDayName;
    @BindView(R.id.adView)
    AdView hAdView;
    private HAdmob hAdmob;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        UIHelper.hOreoOrientationCheck(this);

        ToolBarHelper hToolBarHelper = new ToolBarHelper(this);
        hToolBarHelper.hSetToolbar(hToolbar);
        hToolBarHelper.hSetToolbarTitle(hToolbarTitle, "Weather Updates");

        h_interstetialAdd = new H_InterstetialAdd(this);

        hGetIntentData();
        hGeoCodeLatLng();


        hAdmob = new HAdmob(this, hAdView);
        hAdmob.hLoadAd();
    }

    private void hLoadWeather() {
        RequestWeatherTask hRequestWeatherTask =
                new RequestWeatherTask(this, hLat, hLng, Constants.H_GET_WEATHER);
        hRequestWeatherTask.execute();

        RequestWeatherTask hRequestWeatherTask1 =
                new RequestWeatherTask(this, hLat, hLng, Constants.H_GET_FORECAST);
        hRequestWeatherTask1.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h_interstetialAdd.hShowInterstitial();
    }

    private void hGeoCodeLatLng() {
        try {
            Geocoder geo = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(hLat, hLng, 1);
            if (addresses.isEmpty()) {

            } else {

                if (addresses.size() > 0) {


//                    String address = addresses.get(0).getAddressLine(0);

                    String city = addresses.get(0).getLocality();
                    String country = addresses.get(0).getCountryName();
//                    String state = addresses.get(0).getAdminArea();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String knownName = addresses.get(0).getFeatureName(); // On
//                    Address returnAddress = addresses.get(0);

                    UIHelper.hSetTextToTextView(hCurrrentCityTv, city + ", " + country);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;

    }

    private void hSetUpRecyclerView(int recyclerNumber, List<WeatherModelToShow> hWeatherModelToShowList) {
        switch (recyclerNumber) {
            case Constants.H_TODAYS_RECYCLER:
                LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL, false);
                WeatherRecyclerAdapter weatherRecyclerAdapter = new WeatherRecyclerAdapter(this,
                        hWeatherModelToShowList, Constants.H_TODAYS_RECYCLER);
                hTodaysRv.setLayoutManager(layoutManager);
                hTodaysRv.setAdapter(weatherRecyclerAdapter);
                break;
            case Constants.H_WEEKLY_RECYCLER:
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL, false);
                WeatherRecyclerAdapter weatherRecyclerAdapter1 = new WeatherRecyclerAdapter(this,
                        hWeatherModelToShowList, Constants.H_WEEKLY_RECYCLER);
                hWeeklyRv.setLayoutManager(layoutManager1);
                hWeeklyRv.setAdapter(weatherRecyclerAdapter1);
                break;
        }


    }


    private void hGetIntentData() {
        Bundle hBundle = getIntent().getExtras();
        if (hBundle != null) {
            hLat = hBundle.getDouble(Constants.H_LATITUDE);
            hLng = hBundle.getDouble(Constants.H_LONGITUDE);
        }
    }

    @Override
    public void onWeatherFetch(Object object, String hDecodedString) {
        if (object instanceof LatLonReturnModel) {
            LatLonReturnModel hLatLonReturnModel = (LatLonReturnModel) object;
            hSetCurrentWeatherData(hLatLonReturnModel);
        }
        if (object instanceof WeatherMainReturnResponse) {
            WeatherMainReturnResponse hWeatherMainReturnResponse = (WeatherMainReturnResponse) object;
            hSetForeCast(hWeatherMainReturnResponse);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        UIHelper.hOreoOrientationCheck(this);
        hLoadWeather();
        h_interstetialAdd.hLoadAd();
        hAdmob.hResumeAdd();
    }

    private void hSetForeCast(WeatherMainReturnResponse hWeatherMainReturnResponse) {
        List<WeatherList> hWeatherLists = hWeatherMainReturnResponse.getWeatherList();
        List<WeatherModelToShow> hTodaysList = new ArrayList<>();
        List<WeatherModelToShow> hWeeklyList = new ArrayList<>();

        Calendar hCalendarToday = Calendar.getInstance();
        SimpleDateFormat hdateFormatter = new SimpleDateFormat(hOnlyDateFormat, Locale.getDefault());
        int hTodaysDate = Integer.parseInt(hdateFormatter.format(hCalendarToday.getTime()));
        for (WeatherList weatherList : hWeatherLists) {

//            "2018-11-08 12:00:00"

            //format date from api

            SimpleDateFormat hMainDayMonthSimpleDateFormat = new SimpleDateFormat(hYearMonthDayHrsMinsSecFormat, Locale.getDefault());
            SimpleDateFormat hDayMonthSimpleDateFormat = new SimpleDateFormat(hYearMonthDayFormat, Locale.getDefault());
            SimpleDateFormat hTimeFormatter = new SimpleDateFormat(hHrsMinTime, Locale.getDefault());
            SimpleDateFormat hNameDayFormatter = new SimpleDateFormat(hJstDayNameFormat, Locale.getDefault());


            Calendar hCalendar = Calendar.getInstance();
            try {
                hCalendar.setTime(hMainDayMonthSimpleDateFormat.parse(weatherList.getDtTxt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int hLiveDate = Integer.parseInt(hdateFormatter.format(hCalendar.getTime()));


            if (hLiveDate <= hTodaysDate) {
                String hDescription = weatherList.getWeather().get(0).getDescription();
                String hdate1 = hDayMonthSimpleDateFormat.format(hCalendar.getTime());
                String hTime = hTimeFormatter.format(hCalendar.getTime());
                String hIcon = weatherList.getWeather().get(0).getIcon();
                String hMaxTemp = String.valueOf(weatherList.getMain().getTempMax().intValue());
                hTodaysList.add(new WeatherModelToShow(hdate1, hTime, hIcon, hMaxTemp,hDescription));

            } else {
                String hDayName = hNameDayFormatter.format(hCalendar.getTime());
                if (hDayName.equals(hLastDayName)) {
                    continue;
                } else {
                    String hTime = hTimeFormatter.format(hCalendar.getTime());
                    String hDescription = weatherList.getWeather().get(0).getDescription();


                    String hIcon = weatherList.getWeather().get(0).getIcon();
                    String hMinTemp = String.valueOf(weatherList.getMain().getTempMin().intValue());
                    String hMaxTemp = String.valueOf(weatherList.getMain().getTempMax().intValue());
                    hWeeklyList.add(new WeatherModelToShow(hDayName, hTime, hIcon, hMaxTemp, hMinTemp,hDescription));
                }
                hLastDayName = hDayName;
            }
        }
        if (hTodaysList.size() > 0) {
            hSetUpRecyclerView(Constants.H_TODAYS_RECYCLER, hTodaysList);
        } else {
            LogToastSnackHelper.hMakeLongToast(this, "Unable to Retrieve weather,Plz try again");
        }
        if (hWeeklyList.size() > 0) {
            hSetUpRecyclerView(Constants.H_WEEKLY_RECYCLER, hWeeklyList);
        } else {
            LogToastSnackHelper.hMakeLongToast(this, "Unable to Retrieve weather,Plz try again");

        }
    }

    private void hSetCurrentWeatherData(LatLonReturnModel hLatLonReturnModel) {

        Calendar hCalendar = Calendar.getInstance();


        hTodayWeatherTv.setPaintFlags(hTodayWeatherTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        hWeekWeatherTv.setPaintFlags(hTodayWeatherTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        UIHelper.hSetTextToTextView(hTodayWeatherTv, getString(R.string.today_s_weather));
        UIHelper.hSetTextToTextView(hWeekWeatherTv, getString(R.string.weekly_weather));


        SimpleDateFormat hDayMonthSimpleDateFormat = new SimpleDateFormat(hDayNameMonthDate, Locale.getDefault());
        SimpleDateFormat hTimeSimpleDateFormat = new SimpleDateFormat(hHrsMinTime, Locale.getDefault());


        String hDayMonthString = hDayMonthSimpleDateFormat.format(hCalendar.getTime());
        String hTimeString = hTimeSimpleDateFormat.format(hCalendar.getTime());
        String hIcon = hLatLonReturnModel.getWeather().get(0).getIcon();

        String hPressure = String.valueOf(hLatLonReturnModel.getMain().getPressure());
        String hHumidity = String.valueOf(hLatLonReturnModel.getMain().getHumidity());
        UIHelper.hSetTextToTextView(hPressureDetailTv, hPressure + " Pa");
        UIHelper.hSetTextToTextView(hHumidityTv, hHumidity + "g/" + getString(R.string.cubic_meter));

        UIHelper.hSetTextToTextView(hCurrrentDateTv, hDayMonthString);
        UIHelper.hSetTextToTextView(hCurrentTimeTv, hTimeString);
        UIHelper.hSetTextToTextView(hCurrentWeatherDetailTv,
                hLatLonReturnModel.getWeather().get(0).getDescription());
        SettingsPrefrences hSettingsPrefrences = new SettingsPrefrences(this);
        switch (hSettingsPrefrences.hGetTempUnit()) {
            case Constants.H_FARENHEIT_UNIT:
                UIHelper.hSetTextToTextView(hCurrrentTempTv,
                        String.valueOf(hLatLonReturnModel.getMain().getTemp().intValue()).
                                concat(getString(R.string.farenheit_symbol)));
                break;
            case Constants.H_CELCIUS_UNIT:
                UIHelper.hSetTextToTextView(hCurrrentTempTv,
                        String.valueOf(hLatLonReturnModel.getMain().getTemp().intValue()).
                                concat(getString(R.string.degree_symbol)));
                break;
            case Constants.H_KELVIL_UNIT:
                UIHelper.hSetTextToTextView(hCurrrentTempTv,
                        String.valueOf(hLatLonReturnModel.getMain().getTemp().intValue()).
                                concat(getString(R.string.kelvin_symbol)));
                break;
        }
        Picasso.get().load(Constants.H_ICON_URL + hIcon + ".png")
                .resize(200, 200).centerCrop()
                .into(hCurrentWeatherIcon);

    }

    @Override
    public void hOnClickListener(View hClickedView, int hClickedPosition) {

    }

    @Override
    public void hOnClickListener(View v, int position, String hText) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            h_interstetialAdd.hShowInterstitial();
        }
        return super.onOptionsItemSelected(item);
    }
}
