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
import fi.sportionbois.sportion.components.RPEBar
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import java.util.ArrayList
import kotlin.math.absoluteValue

@Composable
fun LocationResult(locationViewModel: LocationViewModel) {
    val value by locationViewModel.travelledDistance.observeAsState()
    val locationData by locationViewModel.locationData.observeAsState()
    val lineEntrySpeed = ArrayList<Entry>()
    val lineEntryAltitude = ArrayList<Entry>()

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 32.dp, horizontal = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().height(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ShowMap(lat = 1.1, lon = 1.1, address = "")
        }
        Text("Bike details", style = MaterialTheme.typography.body1)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailComponent(firstValue = "%.1f".format(value), secondValue = "distance")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            locationData?.forEachIndexed { index, element ->
                lineEntrySpeed.add(Entry(index.toFloat(), element.speed.toFloat()))
                lineEntryAltitude.add(Entry(index.toFloat(), element.altitude.toFloat()))
            }
            PlotChart(lineEntrySpeed, "Description for chart")
            Spacer(modifier = Modifier.padding(16.dp))
            PlotChart(lineEntryAltitude, "Description for chart")
        }
    }
}