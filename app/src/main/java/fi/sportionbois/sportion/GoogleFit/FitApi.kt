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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
fun getFitApiData(context: Context, fitnessOptions : FitnessOptions, startTime: LocalDate, endTime: LocalDate){

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
            .setTimeRange(startTime.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), endTime.atStartOfDay(
                ZoneId.systemDefault()).toEpochSecond(), TimeUnit.SECONDS)
            .build()

    fun dumpDataSet(dataSet: DataSet) {
        Log.i(ContentValues.TAG, "Data returned for Data type: ${dataSet.dataType.name}")
        Log.i(ContentValues.TAG, "Data returned for Data type: ${dataSet.dataPoints}")
        for (dp in dataSet.dataPoints) {
            Log.i(ContentValues.TAG,"Data point:")
            Log.i(ContentValues.TAG,"\tType: ${dp.dataType.name}")
            for (field in dp.dataType.fields) {
                Log.i(ContentValues.TAG,"\tField: ${field.name} Value: ${dp.getValue(field)}")
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
            Log.w(ContentValues.TAG,"There was an error reading data from Google Fit", e)
        }
}