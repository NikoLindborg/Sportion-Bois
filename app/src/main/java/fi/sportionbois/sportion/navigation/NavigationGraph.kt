package fi.sportionbois.sportion.navigation

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.fitness.FitnessOptions
import fi.sportionbois.sportion.*
import fi.sportionbois.sportion.composables.*
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.LocationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun NavigationGraph(
    navController: NavHostController,
    locationHandler: LocationHandler,
    locationViewModel: LocationViewModel,
    context: Context,
    activity: Activity,
    fitnessOptions: FitnessOptions,
    accelometerViewModel: AccelerometerViewModel,
    gymViewModel: GymViewModel
) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            Home(navController = navController, locationViewModel, gymViewModel)
        }
        composable(BottomNavItem.StartTracking.screen_route) {
            StartTracking(navController = navController, locationHandler, locationViewModel, gymViewModel)
        }
        composable(BottomNavItem.Settings.screen_route) {
            Settings(name = "Settings", context, activity)
        }
        composable("TrackingActive") {
            TrackingActive(navController = navController, locationHandler, locationViewModel, accelometerViewModel, context, fitnessOptions, gymViewModel)
        }
        composable("LocationActivityDetails") {
            LocationResult(locationViewModel)
        }
        composable("LiftDetails" + "/{sportType}" + "/{reps}" + "/{weight}") { navBackStack ->
            val sportType = navBackStack.arguments?.getString("sportType")
            val reps = navBackStack.arguments?.getString("reps")
            val weight = navBackStack.arguments?.getString("weight")
            LiftResult(sportType ?: "", weight ?: "", reps ?: "", locationViewModel)
        }
    }
}