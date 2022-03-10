package fi.sportionbois.sportion.components

import android.content.Context
import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import fi.sportionbois.sportion.R
import java.util.*

/**
 *  A graph component used in result views to display different sensor/location data.
 *
 *  Graph is created with MPAndroidChart. Copyright @ https://github.com/PhilJay/MPAndroidChart
 **/

@Composable
fun PlotChart(
    values: ArrayList<Entry>,
    title: String,
    graphYAxis: String,
    graphXAxis: String,
    description: String
) {
    Card(
        shape = RoundedCornerShape(10.dp), modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = title, color = MaterialTheme.colors.onBackground)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = graphYAxis,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground
                )
            }
            AndroidView(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth(0.9f),
                factory = { context: Context ->
                    val view = LayoutInflater.from(context)
                        .inflate(R.layout.graph, null, false)
                    val graph = view.findViewById<LineChart>(R.id.graph)

                    //  Get the entry values from the parameter array to LineDataSet
                    val lineDataSet = LineDataSet(values, description)

                    //  Can't use material theme coloring as MPAndroidChart requires Int-values
                    // and the toArgb() doesn't work with them.
                    val graphLineColor = Color(0xFF63C7B2).toArgb()

                    //  Getting rid of the small dots and unreadable numbers in the chart
                    //  Also used the theme color and changed the mode to display the line
                    //  in a smoother way
                    lineDataSet.setDrawValues(false)
                    lineDataSet.lineWidth = 3f
                    lineDataSet.setDrawCircles(false)
                    lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                    lineDataSet.setColors(graphLineColor)

                    //  Getting rig of unnecessary grids and texts
                    graph.axisRight.isEnabled = false
                    graph.description = null
                    graph.legend.isEnabled = false
                    graph.xAxis.apply {
                        setDrawGridLines(false)
                        position = XAxis.XAxisPosition.BOTTOM
                    }

                    //  Set the linedata to graph
                    val data = LineData(lineDataSet)
                    graph.data = data

                    view
                },
                update = { view ->
                    view.invalidate()
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = graphXAxis,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = description,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}