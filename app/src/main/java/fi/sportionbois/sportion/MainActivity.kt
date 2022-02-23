package fi.sportionbois.sportion

import android.app.Activity
import androidx.core.content.ContextCompat
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import fi.sportionbois.sportion.GoogleFit.getFitApiData
import androidx.preference.PreferenceManager
import fi.sportionbois.sportion.entities.LocationActivity
import fi.sportionbois.sportion.entities.User
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.navigation.BottomNavigationBar
import fi.sportionbois.sportion.navigation.NavigationGraph
import fi.sportionbois.sportion.navigation.TopBar
import fi.sportionbois.sportion.ui.theme.SportionTheme
import java.time.LocalDate
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel
import fi.sportionbois.sportion.viewmodels.UserViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView

class MainActivity : ComponentActivity() {

    companion object {
        private lateinit var locationViewModel: LocationViewModel
        private lateinit var userViewModel: UserViewModel
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationViewModel = LocationViewModel(application)
        userViewModel = UserViewModel(application)
        userViewModel.insert(User("Koistine", "Juha", "Koistinen"))
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.BODY_SENSORS,
                android.Manifest.permission.ACTIVITY_RECOGNITION),
            0
        )
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        var locationHandler = LocationHandler(applicationContext, locationViewModel)
        locationHandler.initializeLocation()
        setContent {
            SportionTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(locationHandler, locationViewModel, this, this)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen(locationHandler: LocationHandler, locationViewModel: LocationViewModel, context: Context, activity: Activity) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        CenteredColumnMaxWidthAndHeight {
            NavigationGraph(navController = navController, locationHandler, locationViewModel, context, activity)
        }
    }
}
