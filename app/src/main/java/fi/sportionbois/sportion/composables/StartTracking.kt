package fi.sportionbois.sportion.composables


import android.os.Build

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.MainActivity
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import fi.sportionbois.sportion.components.SportTypeCardButton
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.entities.SportActivity
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.UserViewModel
import java.time.*

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

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        //  Placeholder list for different sports
        val listOfSports = listOf("Biking", "Squat", "Deadlift")
        LazyColumn {
            items(listOfSports) {
                SportTypeCardButton(text = it, modifier = Modifier, locationViewModel, gymViewModel)
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
        ButtonCHViolet(text = "START", isSelected.value ?: false, onClick = {
            locationHandler.startLocationTracking()
            navController.navigate("TrackingActive")
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
        })
    }
}