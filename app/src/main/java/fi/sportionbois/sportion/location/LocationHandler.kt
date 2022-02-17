package fi.sportionbois.sportion.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import fi.sportionbois.sportion.viewmodels.LocationViewModel

class LocationHandler(context: Context, locationViewModel: LocationViewModel) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var totalDistance: Float = 0f
    private var previousLocation: Location? = null
    private var context = context
    private var locationViewModel = locationViewModel

    fun initializeLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                Log.d(
                    "GEOLOCATION",
                    "last location latitude: ${it?.latitude} and longitude: ${it?.longitude}"
                )
            }
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                if (previousLocation != null) {
                    totalDistance += p0.lastLocation.distanceTo(previousLocation)
                    locationViewModel.updateLocationData(totalDistance)
                }
                previousLocation = p0.lastLocation
                Log.d("PREVLOC", totalDistance.toString())
                for (location in p0.locations) {
                    Log.d(
                        "GEOLOCATION",
                        "new location latitude: ${location.latitude} and longitude: ${location.longitude}"
                    )
                }
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
