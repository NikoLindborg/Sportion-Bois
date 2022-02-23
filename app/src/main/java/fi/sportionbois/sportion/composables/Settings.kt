package fi.sportionbois.sportion.composables

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import fi.sportionbois.sportion.GoogleFit.getFitApiData
import fi.sportionbois.sportion.MainActivity
import java.time.LocalDate

@Composable
fun Settings(name: String, context: Context, activity: Activity) {
    Text(text = name)
    Button(onClick = { FitApiLogin(context, activity)}){
        Text("Connect to GoogleFit")
    }
}

//Login to google fit
fun FitApiLogin(context: Context, activity: Activity) {
    val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
        .build()

    val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

    val endTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val startTime = endTime.minusWeeks(1)

    if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
        GoogleSignIn.requestPermissions(
            activity, // your activity
            1, // e.g. 1
            account,
            fitnessOptions)
    } else {
        //Load heart rate data
        //getFitApiData(context, fitnessOptions, startTime, endTime)
    }
}
