package fi.sportionbois.sportion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fi.sportionbois.sportion.navigation.BottomNavigationBar
import fi.sportionbois.sportion.navigation.NavigationGraph
import fi.sportionbois.sportion.navigation.TopBar
import fi.sportionbois.sportion.ui.theme.SportionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportionTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun Home() {
    CenteredColumnMaxWidthAndHeight {
        LazyColumn() {
            items(2) {
                HomeDataCard(
                    data = Message(
                        "Ville Valo", "Laulanta", "REPEMAX",
                        "1.4s", "50kg", "3"
                    )
                )
                HomeDataCard(
                    data = Message(
                        "Ville Valo2", "Laulanta", "REPEMAX",
                        "1.4s", "50kg", "3"
                    )
                )
            }
        }
    }
}

@Composable
fun StartTracking(navController: NavController) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ButtonCHViolet(text = "START", onClick = {
            navController.navigate("LiftDetails")
        })
    }
}

@Composable
fun Settings(name: String) {
    Text(text = name)
}

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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SportionTheme {
        Home()
    }
}
