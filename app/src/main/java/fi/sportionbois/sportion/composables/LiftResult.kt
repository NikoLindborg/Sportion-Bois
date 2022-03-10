package fi.sportionbois.sportion.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.components.ButtonCHViolet
import fi.sportionbois.sportion.components.DetailComponent
import fi.sportionbois.sportion.components.PlotChart
import fi.sportionbois.sportion.components.RPEBar
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import fi.sportionbois.sportion.viewmodels.GymViewModel
import java.util.*

/**
 * Composable for a lift result.
 *
 * Provides a RPE-inserting field, data from the lift in a Graph and other lift values in detail
 * components for the user
 **/

@Composable
fun LiftResult(
    sportType: String,
    weight: String,
    reps: String,
    accelerometerViewModel: AccelerometerViewModel,
    currentId: String,
    gymViewModel: GymViewModel
) {
    val lineEntry = ArrayList<Entry>()
    accelerometerViewModel.acceleration.forEachIndexed { index, element ->
        lineEntry.add(Entry(index.toFloat(), element))
    }

    var rpeValue by remember { mutableStateOf("") }
    var databaseRpeValue = gymViewModel.getRpe(currentId.toInt()).observeAsState()

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
            RPEBar(databaseRpeValue.value ?: "")
            TextField(
                value = rpeValue,
                onValueChange = { rpeValue = it },
                textStyle = MaterialTheme.typography.subtitle1,
                label = { Text(stringResource(id = R.string.set_rpe)) },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ButtonCHViolet(text = stringResource(id = R.string.save), isEnabled =(rpeValue != ""), onClick = {
                gymViewModel.updateRpe(rpeValue, currentId.toInt())
            })
        }
        Text(
            "${stringResource(id = R.string.details)} $sportType",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailComponent(firstValue = weight, secondValue = stringResource(id = R.string.weight))
            Spacer(modifier = Modifier.padding(10.dp))
            DetailComponent(firstValue = reps, secondValue = stringResource(id = R.string.reps))
        }
        if (lineEntry.count() > 0) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PlotChart(
                    lineEntry,
                    stringResource(id = R.string.lift_title),
                    stringResource(id = R.string.acceleration),
                    stringResource(id = R.string.time),
                    stringResource(id = R.string.lift_description)
                )
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }
}
