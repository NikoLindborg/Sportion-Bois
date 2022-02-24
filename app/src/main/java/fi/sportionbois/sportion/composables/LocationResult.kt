package fi.sportionbois.sportion.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.components.DetailComponent
import fi.sportionbois.sportion.components.PlotChart
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import java.util.ArrayList

data class LonLat(val lat: Float, val lon: Float)

@Composable
fun LocationResult(locationViewModel: LocationViewModel) {
    val value by locationViewModel.travelledDistance.observeAsState()
    val locationData by locationViewModel.locationData.observeAsState()
    val latestLocationActivityId = locationViewModel.getLatestLocationActivity("Biking").observeAsState()
    val databaseDataPoints by locationViewModel.getDataPointsForId(latestLocationActivityId.value ?: 0).observeAsState()

    val lineEntrySpeed = ArrayList<Entry>()
    val lineEntryAltitude = ArrayList<Entry>()
    val geoPoints = mutableListOf<LonLat>()

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 32.dp, horizontal = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (databaseDataPoints != null) {
                databaseDataPoints?.forEach {
                    geoPoints.add(LonLat(it.lat, it.lon))
                }
                ShowMap(geoList = geoPoints)
            } else {
                Text(text = "No map data available")
            }
        }
        Text("Bike details", style = MaterialTheme.typography.body1)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //DetailComponent(firstValue = "%.1f".format(databaseData?.totalDistance), secondValue = "distance")
            DetailComponent(firstValue = "%.1f".format(value), secondValue = "distance")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (databaseDataPoints != null) {
                databaseDataPoints?.forEachIndexed { index, element ->
                    lineEntrySpeed.add(Entry(index.toFloat(), element.speed))
                    //  Using total distance for now, change to altitude once Room is updated
                    lineEntryAltitude.add(Entry(index.toFloat(), element.totalDistance))
                }
                if (lineEntrySpeed.count() > 0) {
                    PlotChart(lineEntrySpeed, "Description for chart")
                    Spacer(modifier = Modifier.padding(16.dp))
                    PlotChart(lineEntryAltitude, "Description for chart")
                } else {
                    Text(text = "No graph data available")
                }

            }
        }
        Column() {
            databaseDataPoints?.forEach {
                Row {
                    Text(text = it.lat.toString())
                    Text(text = it.lon.toString())
                    Text(text = it.speed.toString())
                    Text(text = "Distance ${it.totalDistance}")
                }
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}