package fi.sportionbois.sportion.composables

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import fi.sportionbois.sportion.GoogleFit.getFitApiData
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LocationResult(
    locationViewModel: LocationViewModel,
    activityId: String,
    context: Context,
    fitnessOptions: FitnessOptions
) {
    val lineEntrySpeed = ArrayList<Entry>()
    val geoPoints = mutableListOf<LonLat>()

    val databaseDataPoints by locationViewModel.getDataPointsForId(activityId.toInt())
        .observeAsState()
    val avgSpeed by locationViewModel.getLocationAvgSpeed(activityId.toInt()).observeAsState()

    val currentActivity by locationViewModel.getActivityById(activityId.toInt()).observeAsState()

    val avgHeartRate by locationViewModel.getAvgHeartRateById(activityId.toInt()).observeAsState()

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(id = R.string.details),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            if (GoogleSignIn.getLastSignedInAccount(context) != null) {
                Button(onClick = {
                    if (currentActivity != null) {
                        getFitApiData(
                            context,
                            fitnessOptions,
                            currentActivity?.startTime ?: 0,
                            currentActivity?.endTime ?: 0,
                            currentActivity ?: null,
                            locationViewModel
                        )
                    }
                }) {
                    Text(stringResource(id = R.string.sync_heart_rate))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (databaseDataPoints != null) {
                DetailComponent(
                    firstValue = ("%.2f".format(
                        databaseDataPoints!![databaseDataPoints!!.size - 1].totalDistance?.times(
                            0.001
                        )
                    )) + "km", secondValue = stringResource(id = R.string.distance)
                )
            }

            DetailComponent(
                firstValue = "${String.format("%.2f", avgSpeed)} ",
                secondValue = stringResource(id = R.string.avg)
            )

            if (avgHeartRate != null && avgHeartRate != 0.0F) {
                DetailComponent(
                    firstValue = "${String.format("%.2f", avgHeartRate)} ",
                    secondValue = stringResource(id = R.string.avg_heart_rate)
                )
            }
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