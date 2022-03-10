package fi.sportionbois.sportion.navigation

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.fitness.FitnessOptions
import fi.sportionbois.sportion.composables.*
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import fi.sportionbois.sportion.viewmodels.UserViewModel

/**
 * NavigationGraph to have all needed Composables in the NavHost
 */

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun NavigationGraph(
    navController: NavHostController,
    locationHandler: LocationHandler,
    locationViewModel: LocationViewModel,
    accelerometerViewModel: AccelerometerViewModel,
    context: Context,
    activity: Activity,
    fitnessOptions: FitnessOptions,
    gymViewModel: GymViewModel,
    userViewModel: UserViewModel
) {
    //  Set's the home as start route
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            Home(navController = navController, locationViewModel, gymViewModel)
        }
        composable(BottomNavItem.StartTracking.screen_route) {
            StartTracking(navController = navController, locationHandler, locationViewModel, gymViewModel, userViewModel)
        }
        composable(BottomNavItem.Settings.screen_route) {
            Settings(name = "Settings", context, activity)
        }
        composable("TrackingActive") {
            TrackingActive(navController = navController, locationHandler, locationViewModel, accelerometerViewModel, gymViewModel)
        }
        composable("LocationActivityDetails" + "/{activityId}") { navBackStack ->
            val activityId = navBackStack.arguments?.getString("activityId")
            LocationResult(locationViewModel, activityId ?: "", context, fitnessOptions)
        }

        composable("LiftDetails" + "/{sportType}" + "/{reps}" + "/{weight}" + "/{currentId}") { navBackStack ->
            val sportType = navBackStack.arguments?.getString("sportType")
            val reps = navBackStack.arguments?.getString("reps")
            val weight = navBackStack.arguments?.getString("weight")
            val currentId = navBackStack.arguments?.getString("currentId")
            LiftResult(sportType ?: "", weight ?: "", reps ?: "", accelerometerViewModel, currentId ?: "", gymViewModel)
        }
    }
}