package fi.sportionbois.sportion.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.sportionbois.sportion.Message
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.composables.LonLat
import fi.sportionbois.sportion.composables.ShowMap
import fi.sportionbois.sportion.entities.GymData
import fi.sportionbois.sportion.entities.LocationDataPoint
import fi.sportionbois.sportion.entities.SportActivity


@Composable
fun HomeDataCard(activity: SportActivity, gymData: GymData?, navController: NavController, databaseDataPoints: List<LocationDataPoint>?){
    val geoPoints = mutableListOf<LonLat>()

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(16.dp)
            .clickable { navController.navigate("LiftDetails" + "/${activity.sportType}"
                    + "/${gymData?.reps}" + "/${gymData?.weight} ") }
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(MaterialTheme.colors.primaryVariant),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            //Header
            Row(modifier = Modifier
                .padding(horizontal = (32.dp), vertical = (16.dp))
                .fillMaxWidth()
                .weight(1f)) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = activity.user, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.onBackground)
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(text = activity.sportType, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
                }
            }
            //Body
            Row(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                if(activity.sportType == "Squat" || activity.sportType == "Deadlift") {
                    if (gymData != null) {
                        RPEBar(gymData.rpe.toString(), null)
                    }
                }
                if(activity.sportType == "Biking") {
                    if (databaseDataPoints != null) {
                        databaseDataPoints.forEach {
                            geoPoints.add(LonLat(it.lat, it.lon))
                        }
                        ShowMap(geoList = geoPoints)
                    } else {
                        Text(text = "No map data available", color = MaterialTheme.colors.onBackground)
                    }
                }
            }
            //Bottom Info
            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    Text(if(activity.sportType == "Biking"){""} else {"Weight"} , color = MaterialTheme.colors.onBackground)
                    Text("Reps", color = MaterialTheme.colors.onBackground)
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly){
                    if(activity.sportType == "Squat" || activity.sportType == "Deadlift") {
                        if (gymData != null) {
                            Text(
                                gymData.weight.toString(),
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        if (gymData != null) {
                            Text(gymData.reps.toString(), color = MaterialTheme.colors.onBackground)
                        }
                    }
                }
                //Text(data.info3, color = MaterialTheme.colors.onBackground)
            }
        }
    }
}