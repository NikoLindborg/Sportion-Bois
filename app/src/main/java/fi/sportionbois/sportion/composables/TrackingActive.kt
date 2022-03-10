package fi.sportionbois.sportion.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.components.ButtonCHViolet
import fi.sportionbois.sportion.components.ProgressValue
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Tracking active view that is used for both location and gym-related tracking.
 **/

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackingActive(
    navController: NavController,
    locationHandler: LocationHandler,
    locationViewModel: LocationViewModel,
    accelerometerViewModel: AccelerometerViewModel,
    gymViewModel: GymViewModel
) {
    val value by locationViewModel.travelledDistance.observeAsState()
    val activityId = locationViewModel.getLatestActivityId().observeAsState()
    locationViewModel.updateCurrentActivityId(activityId.value ?: 0)
    accelerometerViewModel.listen()
    val currentId = locationViewModel.currentActivityId.observeAsState()
    val sportType = locationViewModel.sportType.observeAsState()
    val reps = gymViewModel.reps.observeAsState()
    val weight = gymViewModel.weight.observeAsState()

    CenteredColumnMaxWidthAndHeight {
        //  If the currently tracked sport is location-based, the current travelled distance
        //  is shown as a ProgressValue component, else a text "Tracking" is shown.
        if (locationViewModel.sportType.value === "Outdoor activity") {
            ProgressValue(value = "%.1f".format(value) + " m")
        } else {
            Text(stringResource(id = R.string.tracking), style = MaterialTheme.typography.h1)
        }
        Spacer(modifier = Modifier.padding(20.dp))
        ButtonCHViolet(
            text = stringResource(id = R.string.stop_tracking),
            true,
            onClick = {
                if (locationViewModel.sportType.value === "Outdoor activity") {
                    locationHandler.stopLocationTracking()
                    navController.navigate("LocationActivityDetails" + "/${currentId.value.toString()}") {
                        popUpTo("TrackingActive") {
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate(
                        "LiftDetails" + "/${sportType.value.toString()}"
                                + "/${reps.value.toString()}" + "/${weight.value.toString()}" + "/${currentId.value.toString()}"
                    ) {
                        popUpTo("TrackingActive") {
                            inclusive = true
                        }
                    }
                }

                if (currentId.value != null) {
                    gymViewModel.insertGymData(
                        GymData(currentId.value ?: 0, weight.value, reps.value, "")
                    )
                    gymViewModel.selected.value = false
                }
                accelerometerViewModel.stopListening()
                //Insert end time to activity
                val endTime = LocalDateTime.now(ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC)
                locationViewModel.insertEndTime(currentId.value ?: 0, endTime)
            })
    }
}
