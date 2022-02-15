package fi.sportionbois.sportion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.Home
import fi.sportionbois.sportion.Settings
import fi.sportionbois.sportion.StartTracking
import fi.sportionbois.sportion.components.PlotChart
import java.util.ArrayList

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            Home()
        }
        composable(BottomNavItem.StartTracking.screen_route) {
            StartTracking(navController = navController)
        }
        composable(BottomNavItem.Settings.screen_route) {
            Settings(name = "Settings")
        }
        composable("LiftDetails") {
            val lineEntry = ArrayList<Entry>()
            lineEntry.add(Entry(0f, 0F))
            lineEntry.add(Entry(1f, 0F))
            lineEntry.add(Entry(2f, 1F))
            lineEntry.add(Entry(3f, 0F))
            lineEntry.add(Entry(4f, 0F))
            lineEntry.add(Entry(5f, 0F))
            lineEntry.add(Entry(6f, -4F))
            PlotChart(lineEntry, "Description for chart")
        }
    }
}