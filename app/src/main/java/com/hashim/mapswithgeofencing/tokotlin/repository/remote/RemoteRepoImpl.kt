/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.repository.remote

import android.location.Location
import com.hashim.mapswithgeofencing.tokotlin.network.RetroService
import com.hashim.mapswithgeofencing.tokotlin.ui.main.Category

class RemoteRepoImpl(
        private val hRetroService: RetroService,
        private val hKey: String
) : RemoteRepo {
    override fun hGetWeather() {

        TODO("Not yet implemented")
    }

    override fun hGetForecast() {
        TODO("Not yet implemented")
    }


    override fun hGetDirections() {
        TODO("Not yet implemented")
    }

    override suspend fun hFindNearybyPlaces(category: Category, location: Location) {
        val hStringBuilder = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=")
        hStringBuilder.append("${location.latitude},${location.longitude}&radius=1000&type=${category.name}&key=$hKey")

        hRetroService.hFindNearByPlaces(hStringBuilder.toString())

        /*
        *
        *  private void hFindNearByPlaces(String locationTag, Location hLocation, String whatToLoad) {
        hShowLoader();
        hNearByPlaces = true;
        hWhatToLoad = whatToLoad;
        Double hLat = hLocation.getLatitude();
        Double hLong = hLocation.getLongitude();
        String hApiRequestUrl = Constants.H_G_DIRECTIONS_API_URL
                + locationTag + Constants.H_G_LOCATION + hLat + "," + hLong +
                Constants.H_G_RADIUS + Constants.H_API_KEY;
        hCreateHttpClient(hApiRequestUrl);


    }

    private void hCreateHttpClient(String hApiRequestUrl) {
        OkHttpClient hOkHttpClient;
        hOkHttpClient = new OkHttpClient.Builder().build();
        final Request hRequest = new Request.Builder().url(hApiRequestUrl).build();
        hOkHttpClient.newCall(hRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hHandler.post(() -> hGoogleMap.clear());

                String hJsonString = response.body().string();

                List<Result> hResultsList = new ArrayList<>();

                try {
                    JSONObject hJsonObject = new JSONObject(hJsonString);
                    JSONArray hJsonResultArray = hJsonObject.getJSONArray("results");

                    for (int i = 0; i < hJsonResultArray.length(); i++) {
                        String hArrayitem = hJsonResultArray.get(i).toString();
                        Gson hGson = new Gson();
                        Result hResult = hGson.fromJson(hArrayitem, Result.class);
                        hResultsList.add(hResult);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < hResultsList.size(); i++) {
                    final LatLng hLatLng = new LatLng(hResultsList.get(i).getGeometry().getLocation().getLat(),
                            hResultsList.get(i).getGeometry().getLocation().getLng());
                    final String hName = hResultsList.get(i).getName();


                    hHandler.post(() -> {
                        hCreateMarker(hLatLng, hWhatToLoad, hName);
                    });


                }
                hHandler.postDelayed(() -> {
                    hHideLoader();

                }, Constants.H_1Secs_Timer);
            }
        });
    }
*/
    }

}