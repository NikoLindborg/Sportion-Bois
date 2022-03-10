package fi.sportionbois.sportion

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import fi.sportionbois.sportion.composables.SplashScreen
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.navigation.BottomNavigationBar
import fi.sportionbois.sportion.navigation.NavigationGraph
import fi.sportionbois.sportion.ui.theme.SportionTheme
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import fi.sportionbois.sportion.viewmodels.UserViewModel
import org.osmdroid.config.Configuration

/**
 * MainActivity for setting the Application.
 */

class MainActivity : ComponentActivity() {

    companion object {
        private lateinit var locationViewModel: LocationViewModel
        private lateinit var accelerometerViewModel: AccelerometerViewModel
        private lateinit var gymViewModel: GymViewModel
        private lateinit var userViewModel: UserViewModel
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
            .build()

        locationViewModel = LocationViewModel(application)
        gymViewModel = GymViewModel(application)
        userViewModel = UserViewModel(application)
        accelerometerViewModel = AccelerometerViewModel(application)

        //  Ask for permissions that the appliation needs to be able to track activities
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.BODY_SENSORS,
                android.Manifest.permission.ACTIVITY_RECOGNITION
            ),
            0
        )
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        var locationHandler = LocationHandler(applicationContext, locationViewModel)
        setContent {
            val user = userViewModel.getAll().observeAsState()
            var userCount = user.value?.count() ?: 0
            SportionTheme {
                Surface(color = MaterialTheme.colors.background) {
                    if (userCount == 0) {
                        SplashScreen(userViewModel)
                    } else {
                        MainScreen(
                            locationHandler,
                            locationViewModel,
                            this,
                            this,
                            fitnessOptions,
                            accelerometerViewModel,
                            gymViewModel,
                            userViewModel
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(locationHandler: LocationHandler, locationViewModel: LocationViewModel, context: Context, activity: Activity,
               fitnessOptions: FitnessOptions, accelerometerViewModel: AccelerometerViewModel, gymViewModel: GymViewModel, userViewModel: UserViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        CenteredColumnMaxWidthAndHeight {
            NavigationGraph(
                navController,
                locationHandler,
                locationViewModel,
                accelerometerViewModel,
                context,
                activity,
                fitnessOptions,
                gymViewModel,
                userViewModel
            )
        }
    }
}
