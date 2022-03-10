package fi.sportionbois.sportion.composables


import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import fi.sportionbois.sportion.components.ButtonCHViolet
import fi.sportionbois.sportion.R

/**
 * Composable for connecting to google account to be able to use google fit data in application.
 * In future could also handle user settings etc.
 **/

@Composable
fun Settings(name: String, context: Context, activity: Activity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(R.string.connect_to_google_account),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(32.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        ButtonCHViolet( text = stringResource(id = R.string.connect_to_googlefit), true, onClick = { FitApiLogin(context, activity) })
    }
}


//Login to google fit
fun FitApiLogin(context: Context, activity: Activity) {
    val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
        .build()

    val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

    if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
        GoogleSignIn.requestPermissions(
            activity, // your activity
            1, // e.g. 1
            account,
            fitnessOptions
        )
    } else {
        Toast.makeText(context, "Google sign in was succesfull", Toast.LENGTH_LONG).show()
    }
}
