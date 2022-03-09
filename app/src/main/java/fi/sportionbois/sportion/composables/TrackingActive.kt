package fi.sportionbois.sportion.composables
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.gms.fitness.FitnessOptions
import fi.sportionbois.sportion.components.ProgressValue
import fi.sportionbois.sportion.database.ActivityDB
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import fi.sportionbois.sportion.viewmodels.GymViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.chrono.HijrahChronology.INSTANCE

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackingActive(
    navController: NavController,
    locationHandler: LocationHandler,
    locationViewModel: LocationViewModel,
    accelerometerViewModel: AccelerometerViewModel,
    context: Context,
    fitnessOptions: FitnessOptions,
    gymViewModel: GymViewModel
    ) {
    val value by locationViewModel.travelledDistance.observeAsState()
    val activityId = locationViewModel.getLatestActivityId().observeAsState()
    Log.d("trueai", activityId.value.toString())
    locationViewModel.updateCurrentActivityId(activityId.value ?: 0)
    accelerometerViewModel.listen()
    var currentId = locationViewModel.currentActivityId.observeAsState()
    val sportType = locationViewModel.sportType.observeAsState()
    val reps = gymViewModel.reps.observeAsState()
    val weight = gymViewModel.weight.observeAsState()

    CenteredColumnMaxWidthAndHeight {
        if (locationViewModel.sportType.value === "Biking") {
            ProgressValue(value = "%.1f".format(value) + " m")
        } else {
            Text("Tracking", style = MaterialTheme.typography.h1)
        }
        Spacer(modifier = Modifier.padding(20.dp))
        ButtonCHViolet(
            text = "STOP TRACKING",
            true,
            onClick = {
                locationHandler.stopLocationTracking()
                if(locationViewModel.sportType.value === "Biking"){
                    navController.navigate("LocationActivityDetails" + "/${currentId.value.toString()}") {
                        popUpTo("TrackingActive") {
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate("LiftDetails" + "/${sportType.value.toString()}"
                            + "/${reps.value.toString()}" + "/${weight.value.toString()}" + "/${currentId.value.toString()}" ) {
                        popUpTo("TrackingActive") {
                            inclusive = true
                        }
                    }
                }

                if(currentId.value != null){
                    Log.d("truein", currentId.value.toString())
                        gymViewModel.insertGymData(
                            GymData(currentId.value ?: 0,weight.value,reps.value,"")
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
