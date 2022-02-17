package fi.sportionbois.sportion

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import fi.sportionbois.sportion.location.LocationHandler
import fi.sportionbois.sportion.navigation.BottomNavigationBar
import fi.sportionbois.sportion.navigation.NavigationGraph
import fi.sportionbois.sportion.navigation.TopBar
import fi.sportionbois.sportion.ui.theme.SportionTheme
import fi.sportionbois.sportion.viewmodels.LocationViewModel
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel

class MainActivity : ComponentActivity() {

    companion object {
        private lateinit var locationViewModel: LocationViewModel
    }
    
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationViewModel = LocationViewModel(Application())
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            0
        )
        var locationHandler = LocationHandler(applicationContext, locationViewModel)
        locationHandler.initializeLocation()
        setContent {
            SportionTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(locationHandler, locationViewModel)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen(locationHandler: LocationHandler, locationViewModel: LocationViewModel) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        CenteredColumnMaxWidthAndHeight {
            NavigationGraph(navController = navController, locationHandler, locationViewModel)
        }
    }
}

