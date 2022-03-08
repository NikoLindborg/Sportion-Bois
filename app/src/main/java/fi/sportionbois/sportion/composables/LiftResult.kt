package fi.sportionbois.sportion.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.components.DetailComponent
import fi.sportionbois.sportion.components.PlotChart
import fi.sportionbois.sportion.components.RPEBar
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel

import androidx.compose.runtime.livedata.observeAsState
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import java.util.ArrayList

@Composable
fun LiftResult(
    sportType: String,
    weight: String,
    reps: String,
    locationViewModel: LocationViewModel,
    accelerometerViewModel: AccelerometerViewModel
) {

    val lineEntry = ArrayList<Entry>()
    accelerometerViewModel.acceleration.forEachIndexed { index, element ->
        lineEntry.add(Entry(index.toFloat(), element))
    }
    var rpeValue by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 32.dp, horizontal = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RPEBar(rpeValue.toString())
            TextField(
                value = rpeValue.toString(),
                onValueChange = { rpeValue = it.toInt() },
                textStyle = MaterialTheme.typography.subtitle1,
                label = { Text("Set RPE")},
                modifier= Modifier.padding(32.dp, 32.dp)
            )
            Button(onClick = { /*TODO save data to room*/ }) {
                Text(text = "Save")
            }
        }
        Text("Lift details - $sportType", style = MaterialTheme.typography.body1)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailComponent(firstValue = weight, secondValue = "Weight")
            DetailComponent(firstValue = reps, secondValue = "Reps")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PlotChart(lineEntry, "Description for chart", "", "", "")
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}