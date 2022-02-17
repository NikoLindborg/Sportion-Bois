package fi.sportionbois.sportion.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun SportTypeCardButton(
    text: String,
    modifier: Modifier = Modifier,
) {
    var expandedState by remember { mutableStateOf(false)}
    var repsInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }
    Card(
        modifier = modifier
            .width(325.dp)
            .animateContentSize(
                animationSpec = tween(delayMillis = 300, easing = LinearOutSlowInEasing)
            ),
        shape = RoundedCornerShape(10.dp),
        onClick = { expandedState = !expandedState }
    ) {
        Box(
            modifier = Modifier
                //  Background twice to override surface color
                .background(MaterialTheme.colors.onPrimary)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary.copy(0.8f),
                            Color.Black.copy(0.2f)
                        )
                    )
                )
                .then(modifier),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
                //  Placeholder for sport icons
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
            if(expandedState){
                Row(modifier = Modifier .padding(top = 64.dp).fillMaxWidth().padding(horizontal = 32.dp)){
                    TextField(value = repsInput, onValueChange = {repsInput = it}, label = { Text("Reps") })
                }
                Row(modifier = Modifier .padding(top = 128.dp, bottom = 32.dp).fillMaxWidth().padding(horizontal = 32.dp)){
                    TextField(value = weightInput, onValueChange = {weightInput = it}, label = { Text("Weight") })
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun Preview(){
    SportTypeCardButton(text = "Bowling")
}

