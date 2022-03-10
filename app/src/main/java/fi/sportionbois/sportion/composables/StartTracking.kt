package fi.sportionbois.sportion.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.components.ButtonCHViolet
import fi.sportionbois.sportion.components.SportTypeCardButton
import fi.sportionbois.sportion.entities.SportActivity
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import fi.sportionbois.sportion.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Start tracking composable lets the user start a new sport to track.
 **/

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun StartTracking(
    navController: NavController,
    locationHandler: LocationHandler,
    locationViewModel: LocationViewModel,
    gymViewModel: GymViewModel,
    userViewModel: UserViewModel
) {

    val sportType = locationViewModel.sportType.observeAsState().value
    val isSelected = locationViewModel.selected.observeAsState()
    val user = userViewModel.getInsertedUser().observeAsState()
    var currentTime by remember { mutableStateOf(0L) }
    var timerValue by remember { mutableStateOf("3") }
    var isTimerRunning by remember { mutableStateOf(false) }

    //  LaunchEffect used to display the Counter
    //  Used https://www.youtube.com/watch?v=2mKhmMrt2Ok as a tutorial for this feature
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(1000L)
            currentTime -= 1000L
            //  Calculate the milliseconds to seconds. Used "extra second" so that at 0 there
            //  will be a "Go!"-value.
            if (currentTime > 1000L) {
                timerValue = (currentTime / 1000).minus(1).toString()
            } else {
                timerValue = "Go!"
            }
            if (currentTime == 0L) {
                if (sportType == "Outdoor activity") {
                    locationHandler.startLocationTracking()
                }
                navController.navigate("TrackingActive")
                //  Reset the selected item count so the button will be disabled if navigated
                //  back to this view
                gymViewModel.resetItemCount()
                //get start time in epoch seconds to get as accurate timestamp as possible
                val startTime = LocalDateTime.now(ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC)
                if (user.value != null) {
                    locationViewModel.insert(
                        SportActivity(
                            user.value.toString(),
                            sportType.toString(),
                            startTime,
                            null,
                            0
                        )
                    )
                }
            }
        }
    }

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        //  A quick fix to disabling the start button if more than one item is selected.
        val itemCount = gymViewModel.selectedItemCount.observeAsState(0)
        if (!isTimerRunning) {
            val listOfSports = listOf("Outdoor activity", "Squat", "Deadlift")
            LazyColumn {
                items(listOfSports) {
                    SportTypeCardButton(
                        text = it,
                        modifier = Modifier,
                        locationViewModel,
                        gymViewModel
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
            ButtonCHViolet(
                text = stringResource(id = R.string.start),
                isSelected.value ?: false && itemCount.value != 0 && itemCount.value <= 1,
                onClick = {
                    locationHandler.initializeLocation()
                    currentTime = 4000
                    isTimerRunning = true
                })
        } else {
            Text(timerValue, style = MaterialTheme.typography.h1)
        }
    }
}