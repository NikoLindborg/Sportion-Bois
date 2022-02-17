package fi.sportionbois.sportion

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
import fi.sportionbois.sportion.viewmodels.AccelerometerViewModel

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            0
        )
        var locationHandler = LocationHandler()
        locationHandler.initializeLocation(applicationContext)
        setContent {
            SportionTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        CenteredColumnMaxWidthAndHeight {
            NavigationGraph(navController = navController)
        }
    }
}

