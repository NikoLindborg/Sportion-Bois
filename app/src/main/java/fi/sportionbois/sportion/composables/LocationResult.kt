package fi.sportionbois.sportion.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.components.DetailComponent
import fi.sportionbois.sportion.components.PlotChart
import fi.sportionbois.sportion.components.RPEBar
import java.util.ArrayList

@Composable
fun LocationResult() {
    val lineEntry = ArrayList<Entry>()
    lineEntry.add(Entry(0f, 0F))
    lineEntry.add(Entry(1f, 0F))
    lineEntry.add(Entry(2f, 1F))
    lineEntry.add(Entry(3f, 0F))
    lineEntry.add(Entry(4f, 0F))
    lineEntry.add(Entry(5f, 0F))
    lineEntry.add(Entry(6f, -4F))
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
            RPEBar("8")
        }
        Text("Bike details", style = MaterialTheme.typography.body1)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailComponent(firstValue = "0.23", secondValue = "Speed")
            DetailComponent(firstValue = "63", secondValue = "Weight")
            DetailComponent(firstValue = "28", secondValue = "Reps")
            DetailComponent(firstValue = "21", secondValue = "Repes")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PlotChart(lineEntry, "Description for chart")
            Spacer(modifier = Modifier.padding(16.dp))
            PlotChart(lineEntry, "Description for chart")
        }
    }
}