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


@Composable
fun HomeDataCard(data: Message, navController: NavController){
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(16.dp)
            .clickable { navController.navigate("LiftDetails") }
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
                    Text(text = data.name, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.onBackground)
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(text = data.activity, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
                }
            }
            //Body
            Row(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                    RPEBar(data.body)
            }
            //Bottom Info
            Row(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly){
                Text(data.info1, color = MaterialTheme.colors.onBackground)
                Text(data.info2, color = MaterialTheme.colors.onBackground)
                Text(data.info3, color = MaterialTheme.colors.onBackground)
            }
        }
    }
}