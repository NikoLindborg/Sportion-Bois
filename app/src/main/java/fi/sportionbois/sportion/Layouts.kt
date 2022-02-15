package fi.sportionbois.sportion

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.sportionbois.sportion.ui.theme.SportionTheme

@Composable
@Preview
fun RoundButtonPrev() {
    SportionTheme {
        Surface(color = MaterialTheme.colors.background) {
            ButtonCHViolet(text = "START") {

            }
        }
    }
}

@Composable
fun ButtonCHViolet(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(90.dp)
            .width(325.dp),
        contentPadding = PaddingValues(0.dp, 0.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary,
                            Color.Black.copy(0.4f)
                        )
                    )
                )
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.h1
            )
        }
    }
}

@Composable
fun CenteredColumnMaxWidthAndHeight(children: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        children()
    }
}

data class Message(val name: String, val activity: String,
                   val body: String, val info1: String,
                    val info2: String, val info3: String)

@Composable
@Preview
fun HomeDataCardRev() {
    SportionTheme {
        Surface(color = MaterialTheme.colors.background) {
            HomeDataCard(Message("Ville Valo", "Laulanta", "REPEMAX",
            "1.4s", "50kg", "3"))
        }
    }
}

@Composable
fun HomeDataCard(data: Message){
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(16.dp),
        border = BorderStroke(1.5.dp, MaterialTheme.colors.primary)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            //Header
            Row(modifier = Modifier.padding(horizontal = (32.dp), vertical = (16.dp))
                .fillMaxWidth() .weight(1f)) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(){
                    Text(text = data.name, style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(text = data.activity, style = MaterialTheme.typography.body2)
                }
            }
            //Body
            Row(modifier = Modifier.padding(8.dp) .fillMaxWidth() .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text(data.body)
            }
            //Bottom Info
            Row(modifier = Modifier.padding(8.dp) .fillMaxWidth() .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly){
                Text(data.info1)
                Text(data.info2)
                Text(data.info3)
            }
        }
    }
}