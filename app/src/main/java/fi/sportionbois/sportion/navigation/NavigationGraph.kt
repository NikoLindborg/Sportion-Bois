package fi.sportionbois.sportion.navigation

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fi.sportionbois.sportion.*
import fi.sportionbois.sportion.composables.*

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            Home(navController = navController)
        }
        composable(BottomNavItem.StartTracking.screen_route) {
            StartTracking(navController = navController)
        }
        composable(BottomNavItem.Settings.screen_route) {
            Settings(name = "Settings")
        }
        composable("TrackingActive") {
            TrackingActive(navController = navController)
        }
        composable("LocationActivityDetails") {
            LocationResult()
        }
        composable("LiftDetails") {
            LiftResult()
        }
    }
}