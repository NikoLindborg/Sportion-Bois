package fi.sportionbois.sportion.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.Home
import fi.sportionbois.sportion.LiftResult
import fi.sportionbois.sportion.Settings
import fi.sportionbois.sportion.StartTracking
import fi.sportionbois.sportion.components.DetailComponent
import fi.sportionbois.sportion.components.PlotChart
import fi.sportionbois.sportion.components.RPEBar
import java.util.ArrayList

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
        composable("LiftDetails") {
            LiftResult()
        }
    }
}