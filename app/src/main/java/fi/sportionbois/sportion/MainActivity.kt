package fi.sportionbois.sportion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.mikephil.charting.data.Entry
import fi.sportionbois.sportion.components.*
import fi.sportionbois.sportion.navigation.BottomNavigationBar
import fi.sportionbois.sportion.navigation.NavigationGraph
import fi.sportionbois.sportion.navigation.TopBar
import fi.sportionbois.sportion.ui.theme.SportionTheme
import java.util.ArrayList

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
fun Home(navController: NavController) {
    CenteredColumnMaxWidthAndHeight {
        LazyColumn() {
            items(2) {
                HomeDataCard(
                    data = Message(
                        "Ville Valo", "Laulanta", "8",
                        "1.4s", "50kg", "3"
                    ), navController = navController
                )
                HomeDataCard(
                    data = Message(
                        "Ville Valo2", "Laulanta", "3",
                        "1.4s", "50kg", "3"
                    ), navController = navController
                )
            }
        }
    }
}

@Composable
fun StartTracking(navController: NavController) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        //  Placeholder list for different sports
        val listOfSports = listOf("Biking", "Squat", "Deadlift")
        LazyColumn() {
            items(listOfSports) {
                SportTypeButton(text = it) { }
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
        ButtonCHViolet(text = "START", onClick = { navController.navigate("LiftDetails") })
    }
}

@Composable
fun LiftResult(){
    val lineEntry = ArrayList<Entry>()
    lineEntry.add(Entry(0f, 0F))
    lineEntry.add(Entry(1f, 0F))
    lineEntry.add(Entry(2f, 1F))
    lineEntry.add(Entry(3f, 0F))
    lineEntry.add(Entry(4f, 0F))
    lineEntry.add(Entry(5f, 0F))
    lineEntry.add(Entry(6f, -4F))
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 32.dp, horizontal = 32.dp)) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            RPEBar("8")
        }
        Text("Lift details", style = MaterialTheme.typography.body1)
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            DetailComponent(firstValue = "0.23", secondValue = "Speed")
            DetailComponent(firstValue = "63", secondValue = "Weight")
            DetailComponent(firstValue = "28", secondValue = "Reps")
            DetailComponent(firstValue = "21", secondValue = "Repes")
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            PlotChart(lineEntry, "Description for chart")
            Spacer(modifier = Modifier.padding(16.dp))
            PlotChart(lineEntry, "Description for chart")
        }
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

