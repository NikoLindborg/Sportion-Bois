package fi.sportionbois.sportion.composables
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.google.android.gms.fitness.FitnessOptions
import fi.sportionbois.sportion.GoogleFit.getFitApiData
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackingActive(
    navController: NavController,
    locationHandler: LocationHandler,
    locationViewModel: LocationViewModel,
    accelerometerViewModel: AccelerometerViewModel = viewModel(),
    context: Context,
    fitnessOptions: FitnessOptions
    ) {
    val value by locationViewModel.travelledDistance.observeAsState()
    val acc = accelerometerViewModel.acceleration.observeAsState()
    val testData = locationViewModel.getLatestLocationActivity("Biking").observeAsState()
    val latestLocationActivityId = locationViewModel.getLatestLocationActivity("Biking").observeAsState()
    locationViewModel.updateCurrentActivityId(testData.value?.toInt() ?: 0)
    accelerometerViewModel.listen()
    //Log.d("acc", acc.value.toString())

    //Add gymdata to room
    /*locationViewModel.insertGymData(
        GymData(locationViewModel.currentActivityId.value ?: 0,locationViewModel.weight.value,locationViewModel.reps.value, 0)
    )*/

    CenteredColumnMaxWidthAndHeight {
        RPEBar(rpeValue = "%.1f".format(value))
        Spacer(modifier = Modifier.padding(20.dp))
        ButtonCHViolet(
            text = "STOP TRACKING",
            onClick = {
                locationHandler.stopLocationTracking()
                navController.navigate("LocationActivityDetails")
                accelerometerViewModel.stopListening()

                //Insert end time to activity
                val endTime = LocalDateTime.now(ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC)
                locationViewModel.insertEndTime(latestLocationActivityId.value ?: 0, endTime)
            })
    }
}