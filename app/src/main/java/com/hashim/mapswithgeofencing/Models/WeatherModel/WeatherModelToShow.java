package com.hashim.mapswithgeofencing.Models.WeatherModel;

public class WeatherModelToShow {


    String hdate;
    String hdate1;
    String hTime;
    String hIcon;
    String hMaxTemp;
    String hMinTemp;
    String hDescription;

    public WeatherModelToShow(String date, String time, String icon, String maxTemp, String description) {
        this.hdate = date;
        this.hTime = time;
        this.hIcon = icon;
        this.hMaxTemp = maxTemp;
        this.hDescription = description;
    }

    public WeatherModelToShow(String dayName, String time, String icon, String maxTemp, String minTemp, String description) {
        this.hdate = dayName;
        this.hTime = time;
        this.hIcon = icon;
        this.hMaxTemp = maxTemp;
        this.hDescription = description;
        this.hMinTemp = minTemp;
    }

    public String gethDescription() {
        return hDescription;
    }

    public void sethDescription(String hDescription) {
        this.hDescription = hDescription;
    }

    String hTemp;


    public String getHdate() {
        return hdate;
    }

    public void setHdate(String hdate) {
        this.hdate = hdate;
    }

    public String getHdate1() {
        return hdate1;
    }

    public void setHdate1(String hdate1) {
        this.hdate1 = hdate1;
    }

    public String gethTime() {
        return hTime;
    }

    public void sethTime(String hTime) {
        this.hTime = hTime;
    }

    public String gethIcon() {
        return hIcon;
    }

    public void sethIcon(String hIcon) {
        this.hIcon = hIcon;
    }

    public String gethMaxTemp() {
        return hMaxTemp;
    }

    public void sethMaxTemp(String hMaxTemp) {
        this.hMaxTemp = hMaxTemp;
    }

    public String gethMinTemp() {
        return hMinTemp;
    }

    public void sethMinTemp(String hMinTemp) {
        this.hMinTemp = hMinTemp;
    }

    public String gethTemp() {
        return hTemp;
    }

    public void sethTemp(String hTemp) {
        this.hTemp = hTemp;
    }

    public String gethDayName() {
        return hDayName;
    }

    public void sethDayName(String hDayName) {
        this.hDayName = hDayName;
    }

    String hDayName;


//     hMainDayMonthSimpleDateFormat.format(hCalendar.getTime());
//     hDayMonthSimpleDateFormat.format(hCalendar.getTime());
//     hTimeFormatter.format(hCalendar.getTime());
//     weatherList.getWeather().get(0).getIcon();
//     String.valueOf(weatherList.getMain().getTempMax());
//     String.valueOf(weatherList.getMain().getTempMin());
//     String.valueOf(weatherList.getMain().getTempMax());
//     hNameDayFormatter.format(hCalendar.getTime());
}
