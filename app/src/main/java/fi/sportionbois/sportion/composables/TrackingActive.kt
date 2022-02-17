package fi.sportionbois.sportion.composables

import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight

@Composable
fun TrackingActive(navController: NavController) {
    CenteredColumnMaxWidthAndHeight {
        ButtonCHViolet(
            text = "STOP TRACKING",
            onClick = { navController.navigate("LocationActivityDetails") })
    }
}