package fi.sportionbois.sportion.GoogleFit

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import fi.sportionbois.sportion.entities.SportActivity
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import java.util.concurrent.TimeUnit

/**
 * Handles fetching data from googlefit, inserts average heart rate to database
 * Done with developers.google guide. Copyright @ https://developers.google.com/fit/android/get-started
 **/

@RequiresApi(Build.VERSION_CODES.O)
fun getFitApiData(
    context: Context, fitnessOptions: FitnessOptions, startTime: Long, endTime: Long,
    currentActivity: SportActivity?, locationViewModel: LocationViewModel
) {
    val readRequest =
        DataReadRequest.Builder()
            // The data request can specify multiple data types to return,
            // effectively combining multiple data queries into one call.
            // This example demonstrates aggregating only one data type.
            .aggregate(DataType.TYPE_HEART_RATE_BPM)
            // Analogous to a "Group By" in SQL, defines how data should be
            // aggregated.
            // bucketByTime allows for a time span, whereas bucketBySession allows
            // bucketing by <a href="/fit/android/using-sessions">sessions</a>.
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(
                startTime,
                endTime, TimeUnit.SECONDS
            )
            .build()

    fun dumpDataSet(dataSet: DataSet) {
        var avg: Float = 0.0F
        for (dp in dataSet.dataPoints) {
            for (field in dp.dataType.fields) {
                if (field.name == "average") {
                    avg = dp.getValue(field).asFloat()
                }
            }
        }
        if (currentActivity != null) {
            if (currentActivity?.avgHeartRate == null) {
                if (avg != 0.0F) {
                    locationViewModel.insertAvgHeartRate(currentActivity?.activityId, avg)
                }
            }
        }
    }

    Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
        .readData(readRequest)
        .addOnSuccessListener { response ->
            // The aggregate query puts datasets into buckets, so flatten into a
            // single list of datasets
            for (dataSet in response.buckets.flatMap { it.dataSets }) {
                dumpDataSet(dataSet)
            }
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "There was an error reading data from Google Fit", e)
        }
}