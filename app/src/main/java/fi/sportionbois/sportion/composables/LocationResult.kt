package fi.sportionbois.sportion.composables

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.components.DetailComponent
import fi.sportionbois.sportion.components.PlotChart
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import java.util.*

/**
 * Composable for a location result.
 *
 * Provides a map, data from the route in a Graph and other values in detail components for the user
 **/

@Composable
fun LocationResult(locationViewModel: LocationViewModel, activityId: String) {
    val lineEntrySpeed = ArrayList<Entry>()
    val geoPoints = mutableListOf<LonLat>()

    val databaseDataPoints by locationViewModel.getDataPointsForId(activityId.toInt())
        .observeAsState()
    val avgSpeed by locationViewModel.getLocationAvgSpeed(activityId.toInt()).observeAsState()

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
                //  Receive the routes lat and lon values that are inserted as GeoPoints to Map
                databaseDataPoints?.forEach {
                    geoPoints.add(LonLat(it.lat, it.lon))
                }
                ShowMap(geoList = geoPoints)
            } else {
                Text(
                    text = stringResource(id = R.string.no_map),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
        Text(
            stringResource(id = R.string.details),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (databaseDataPoints != null) {
                if (databaseDataPoints!![databaseDataPoints!!.size - 1].totalDistance != null) {
                    DetailComponent(
                        firstValue = ("%.2f".format(
                            databaseDataPoints!![databaseDataPoints!!.size - 1].totalDistance?.times(
                                0.001
                            )
                        )) + "km", secondValue = stringResource(id = R.string.distance)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            DetailComponent(
                firstValue = "${String.format("%.2f", avgSpeed)} ",
                secondValue = stringResource(id = R.string.avg)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (databaseDataPoints != null) {
                /*  Insert the speed values from database to Graph with an Indexed value to have
                    both y and x-axis values */
                databaseDataPoints?.forEachIndexed { index, element ->
                    lineEntrySpeed.add(Entry(index.toFloat(), element.speed))
                }
                if (lineEntrySpeed.count() > 0) {
                    PlotChart(
                        lineEntrySpeed,
                        stringResource(id = R.string.route_speed),
                        stringResource(id = R.string.m_s),
                        stringResource(id = R.string.m),
                        stringResource(id = R.string.route_description)
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.no_graph),
                        color = MaterialTheme.colors.onBackground
                    )
                }

            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
    }
}

data class LonLat(val lat: Float, val lon: Float)