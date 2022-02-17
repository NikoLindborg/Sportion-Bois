package fi.sportionbois.sportion.composables
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.components.RPEBar
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel

@Composable
fun TrackingActive(
    navController: NavController,
    locationHandler: LocationHandler,
    locationViewModel: LocationViewModel,
    accelerometerViewModel: AccelerometerViewModel = viewModel()
) {
    val value by locationViewModel.travelledDistance.observeAsState()
    val acc = accelerometerViewModel.acceleration.observeAsState()
    accelerometerViewModel.listen()
    Log.d("acc", acc.value.toString())

    CenteredColumnMaxWidthAndHeight {
        RPEBar(rpeValue = "%.1f".format(value))
        Spacer(modifier = Modifier.padding(20.dp))
        ButtonCHViolet(
            text = "STOP TRACKING",
            onClick = {
                locationHandler.stopLocationTracking()
                navController.navigate("LocationActivityDetails")
                accelerometerViewModel.stopListening()
            })
    }
}