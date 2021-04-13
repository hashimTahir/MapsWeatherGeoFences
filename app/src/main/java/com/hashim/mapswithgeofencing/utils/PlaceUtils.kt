import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.*
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.hashim.mapswithgeofencing.R
import timber.log.Timber
import java.util.*

class PlaceUtils(
        private val hContext: Context
) {
    private val hPlacesClient: PlacesClient
    private var hMinLat = 0.0
    private var hMinLong = 0.0
    private var hMaxLat = 0.0
    private var hMaxLong = 0.0

    init {
        Places.initialize(hContext, hContext.getString(R.string.google_maps_key))

        hPlacesClient = Places.createClient(hContext)
    }

    public fun hFetchAPlaceById(placeId: String, hOnPlaceFound: (hPlace: Place?, errorMessage: String?) -> Unit) {
        // INSERT_PLACE_ID_HERE
        val hPlaceId = placeId

        // Specify the fields to return.
        val hPlaceFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.LAT_LNG,
                Place.Field.VIEWPORT, Place.Field.ADDRESS_COMPONENTS,
                Place.Field.PHOTO_METADATAS)

        // Construct a request object, passing the place ID and fields array.
        val hFetchPlaceRequest = FetchPlaceRequest
                .builder(placeId, hPlaceFields)
                .build()
        hPlacesClient
                .fetchPlace(hFetchPlaceRequest)
                .addOnSuccessListener { fetchPlaceResponse: FetchPlaceResponse ->
                    val place = fetchPlaceResponse.place
                    Timber.d("Place found: $place.name")
                    hOnPlaceFound(place, null)
                }
                .addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        val hApiException = exception
                        hApiException.statusCode
                        hApiException.message
                        hOnPlaceFound(null, hApiException.message)
                    } else {
                        hOnPlaceFound(null, exception.message)
                    }
                }
    }

    private fun hFetchPlacePhoto(place: Place, hOnPlacePhotoFound: (hPlace: Bitmap?, errorMessage: String?) -> Unit) {
        // Get the photo metadata.
        val hPhotoMetadata = place.photoMetadatas!![0]


        // Get the attribution text.
        val hPhotoMetadataAttributions = hPhotoMetadata.attributions

        // Create a FetchPhotoRequest.
        val hFetchPhotoRequest = FetchPhotoRequest
                .builder(hPhotoMetadata)
                .setMaxWidth(500) // Optional.
                .setMaxHeight(300) // Optional.
                .build()
        hPlacesClient
                .fetchPhoto(hFetchPhotoRequest)
                .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                    val hPlaceBitmap = fetchPhotoResponse.bitmap
                    hOnPlacePhotoFound(hPlaceBitmap, null)
                }
                .addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        val hApiException = exception
                        hApiException.statusCode
                        hApiException.message
                        hOnPlacePhotoFound(null, hApiException.message)

                    } else {
                        hOnPlacePhotoFound(null, exception.message)

                    }
                }
    }

    @SuppressLint("MissingPermission")
    private fun hFindCurrentPlace(hOnPlaceFound: (hOnCurrentPlaceFound: MutableList<PlaceLikelihood>?, errorMessage: String?) -> Unit) {
        // Use fields to define the data types to return.
        val hPlaceFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.LAT_LNG,
                Place.Field.VIEWPORT,
                Place.Field.PHOTO_METADATAS)

        // Use the builder to create a FindCurrentPlaceRequest.
        val hFindCurrentPlaceRequest = FindCurrentPlaceRequest
                .builder(hPlaceFields)
                .build()
        hPlacesClient
                .findCurrentPlace(hFindCurrentPlaceRequest)
                .addOnSuccessListener { findCurrentPlaceResponse: FindCurrentPlaceResponse ->
                    val hPlaceLikelihoodList = findCurrentPlaceResponse.placeLikelihoods
                    for (placeLikelihood in hPlaceLikelihoodList) {
                        Timber.d("Place '%s' has likelihood: %f with latlng %s",
                                placeLikelihood.place.name,
                                placeLikelihood.likelihood,
                                placeLikelihood.place.latLng)
                    }
                    hOnPlaceFound(hPlaceLikelihoodList, null)
                }
                .addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        val hApiException = exception
                        hApiException.statusCode
                        hApiException.message
                        hOnPlaceFound(null, hApiException.message)
                    } else {
                        hOnPlaceFound(null, exception.message)

                    }
                }
    }

    private fun hFindAutocompletePredictions(
            query: String,
            latLng: LatLng,
            hOnPredictionsFound: (hPlace: AutocompletePrediction?, errorMessage: String?) -> Unit
    ) {
        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        val hAutocompleteSessionToken = AutocompleteSessionToken.newInstance()
        hSetBounds(latLng, 5500)
        // Create a RectangularBounds object.
        val bounds = RectangularBounds.newInstance(
                LatLng(hMinLat, hMinLong),
                LatLng(hMaxLat, hMaxLong))

        // Use the builder to create a FindAutocompletePredictionsRequest.
        val hFindAutocompletePredictionsRequest = FindAutocompletePredictionsRequest.builder() // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                .setLocationRestriction(bounds)
                .setCountry("au")
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(hAutocompleteSessionToken)
                .setQuery(query)
                .build()
        hPlacesClient
                .findAutocompletePredictions(hFindAutocompletePredictionsRequest)
                .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                    for (prediction in response.autocompletePredictions) {
                        Timber.d(prediction.placeId)
                        Timber.d(prediction.getPrimaryText(null).toString())
                        hOnPredictionsFound(prediction, null)
                        //Can use callbacks here.
                    }
                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        val hApiException = exception
                        hApiException.statusCode
                        hApiException.message
                        hOnPredictionsFound(null, hApiException.message)
                    } else {
                        hOnPredictionsFound(null, exception.message)
                    }
                }
    }

    private fun hSetBounds(latLng: LatLng, mDistanceInMeters: Int) {
        val hLocation = Location(LocationManager.GPS_PROVIDER)
        hLocation.latitude = latLng.latitude
        hLocation.longitude = latLng.longitude
        val latRadian = Math.toRadians(hLocation.latitude)
        val degLatKm = 110.574235
        val degLongKm = 110.572833 * Math.cos(latRadian)
        val deltaLat = mDistanceInMeters / 1000.0 / degLatKm
        val deltaLong = mDistanceInMeters / 1000.0 / degLongKm
        hMinLat = hLocation.latitude - deltaLat
        hMinLong = hLocation.longitude - deltaLong
        hMaxLat = hLocation.latitude + deltaLat
        hMaxLong = hLocation.longitude + deltaLong
    }

    companion object {

        fun hInit(context: Context) {
            Places.initialize(context, context.getString(R.string.google_maps_key))
            val placesClient = Places.createClient(context)
        }

        fun hStartPlacesAutoComplete(context: Fragment): Intent {
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                    Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.VIEWPORT, Place.Field.ADDRESS_COMPONENTS)
            val intent = Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fields)
                    .build(context.requireContext())

            return intent
        }
    }


}
