# Maps Weather Geofences

### Architecture
## MVI-Model View Intent
 Everything the view requires is packeged into a single class "ViewState" as
 single source of truth and view observes the change in the data.
 StateEvents are fired from the view as intents i.e. what users wants to do towards
 viewmodel.


### Technologies Used
 * Dagger-Hilt
 * Retrofit
 * Coroutines
 * Repository
 * Google Maps Api
 * Google Directions Api
 * Google Places Api
 * Goolge Geofences Api
 * Open Weather api
 * Database caching
 * Navigation Components
 * Room



![ScreenShot](/images/main_page.png)

![ScreenShot](/images/nearyby_page.png)

![ScreenShot](/images/path_page.png)

![ScreenShot](/images/route_page.png)

![ScreenShot](/images/settings_page.png)

![ScreenShot](/images/weather_page.png)