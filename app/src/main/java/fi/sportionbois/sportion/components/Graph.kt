package fi.sportionbois.sportion.components

import android.content.Context
import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.ArrayList
import fi.sportionbois.sportion.R

@Composable
fun PlotChart(values: ArrayList<Entry>, description: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .fillMaxWidth(),
        factory = { context: Context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.graph, null, false)
            val graph = view.findViewById<LineChart>(R.id.graph)
            val data = LineData(LineDataSet(values, description))
            graph.data = data
            view
        },
        update = { view ->
            view.invalidate()
        }
    )
}


