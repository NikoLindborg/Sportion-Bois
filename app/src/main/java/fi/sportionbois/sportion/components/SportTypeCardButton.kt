package fi.sportionbois.sportion.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fi.sportionbois.sportion.viewmodels.GymViewModel
import fi.sportionbois.sportion.viewmodels.LocationViewModel

@ExperimentalMaterialApi
@Composable
fun SportTypeCardButton(
    text: String,
    modifier: Modifier,
    locationViewModel: LocationViewModel,
    gymViewModel: GymViewModel
) {
    var expandedState by remember { mutableStateOf(false)}
    var repsInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .width(325.dp)
                //need to correct colors on border
            .then(if (expandedState && !locationViewModel.selected.value!!) modifier.border(BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),  RoundedCornerShape(10.dp))
            else modifier)
            .animateContentSize(
                animationSpec = tween(delayMillis = 300, easing = LinearOutSlowInEasing)
            )
        ,
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
                    locationViewModel.selected.value = true
                    if(text != "Biking") {
                        Row(
                            modifier = Modifier.padding(top = 64.dp).fillMaxWidth()
                                .padding(horizontal = 32.dp)
                        ) {
                            TextField(value = repsInput,
                                onValueChange = { repsInput = it },
                                label = { Text("Reps") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                        Row(
                            modifier = Modifier.padding(top = 128.dp, bottom = 32.dp).fillMaxWidth()
                                .padding(horizontal = 32.dp)
                        ) {
                            TextField(value = weightInput,
                                onValueChange = { weightInput = it },
                                label = { Text("Weight") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                        if (repsInput != "" && weightInput != "") {
                            gymViewModel.reps.value = repsInput.toLong()
                            gymViewModel.weight.value = weightInput.toLong()
                            locationViewModel.sportType.value = text
                        }
                    } else if(text === "Biking" && locationViewModel.selected.value!!) {
                        locationViewModel.sportType.value = text
                    }
                }
            }
        }
    }



/*
@ExperimentalMaterialApi
@Composable
@Preview
fun Preview(){
    SportTypeCardButton(text = "Bowling", )
}
*/


