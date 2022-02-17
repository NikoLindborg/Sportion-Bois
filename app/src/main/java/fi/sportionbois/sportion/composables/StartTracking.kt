package fi.sportionbois.sportion.composables

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.components.SportTypeButton

@Composable
fun StartTracking(navController: NavController) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        //  Placeholder list for different sports
        val listOfSports = listOf("Biking", "Squat", "Deadlift")
        LazyColumn() {
            items(listOfSports) {
                SportTypeButton(text = it) { }
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
        ButtonCHViolet(text = "START", onClick = {
            navController.navigate("TrackingActive")
        })
    }
}