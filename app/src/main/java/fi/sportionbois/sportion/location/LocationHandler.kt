package fi.sportionbois.sportion.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import fi.sportionbois.sportion.entities.LocationDataPoint
import fi.sportionbois.sportion.viewmodels.LocationViewModel

/**
 * LocationHandler for tracking the users location with Google Play API
 **/

class LocationHandler(context: Context, locationViewModel: LocationViewModel) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var totalDistance: Float = 0f
    private var previousLocation: Location? = null
    private var context = context
    private var locationViewModel = locationViewModel

    //  Initializes the first location of the user and has the LocationCallback to persist
    //  LocationDataPoints to Room database
    fun initializeLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        )
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                if (previousLocation != null) {
                    totalDistance += p0.lastLocation.distanceTo(previousLocation)
                    locationViewModel.updateLocationDistanceTo(totalDistance)
                    locationViewModel.updateLocationData(p0.lastLocation)
                    locationViewModel.insertLocationDataPoints(
                        LocationDataPoint(
                            locationViewModel.currentActivityId.value ?: 0,
                            p0.lastLocation.latitude.toFloat(),
                            p0.lastLocation.longitude.toFloat(),
                            p0.lastLocation.speed.toFloat(),
                            totalDistance
                        )
                    )
                }
                previousLocation = p0.lastLocation
            }
        }
    }

    fun startLocationTracking() {
        val locationRequest = LocationRequest
            .create()
            .setInterval(1000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun stopLocationTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
