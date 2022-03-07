package fi.sportionbois.sportion.composables

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.components.HomeDataCard
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.LocationViewModel

@Composable
fun Home(navController: NavController, locationViewModel: LocationViewModel, gymViewModel: GymViewModel) {

    var allData = locationViewModel.getAllData().observeAsState().value

    Log.d("home", allData.toString())

    CenteredColumnMaxWidthAndHeight (){
        LazyColumn {
            if (allData != null) {
                items(allData) { activity ->
                    if(activity.sportType == "Squat" || activity.sportType == "Deadlift"){
                        val gymData = gymViewModel.getGymDataById(activity.activityId).observeAsState().value
                        if (gymData != null) {
                            HomeDataCard(activity, gymData, navController = navController, null)
                        }
                    }
                    if(activity.sportType == "Biking") {
                        val databaseDataPoints by locationViewModel.getDataPointsForId(activity.activityId).observeAsState()
                        if(databaseDataPoints != null) {
                            HomeDataCard(activity, null, navController = navController, databaseDataPoints)
                        }
                    }
                }
            }
        }
    }
}