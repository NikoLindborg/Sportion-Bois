package fi.sportionbois.sportion.composables

import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel

@Composable
fun TrackingActive(navController: NavController, accelerometerViewModel: AccelerometerViewModel = viewModel()) {
    val acc = accelerometerViewModel.acceleration.observeAsState()
    accelerometerViewModel.listen()
    Log.d("acc", acc.value.toString())
    CenteredColumnMaxWidthAndHeight {
        ButtonCHViolet(
            text = "STOP TRACKING",
            onClick = {
                navController.navigate("LocationActivityDetails")
                accelerometerViewModel.stopListening()
            })
    }
}