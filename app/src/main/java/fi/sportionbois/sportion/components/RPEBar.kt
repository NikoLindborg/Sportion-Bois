package fi.sportionbois.sportion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.sportionbois.sportion.ui.theme.SportionTheme

@Composable
fun RPEBar(rpeValue: String) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    OutlinedButton(
        onClick = { setExpanded(true) },
        shape = CircleShape,
        modifier = Modifier
            .size(120.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colors.secondary), shape = CircleShape),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colors.onBackground,
            backgroundColor = Color.Transparent
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(rpeValue, style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(4.dp))
            Text("RPE", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(14.dp))
            Icon(Icons.Filled.Info, contentDescription = "Info Icon")

        }
        ShowRPEInfo(expanded = expanded, setExpanded = setExpanded)

    }
}

//https://coflutter.com/jetpack-compose-how-to-show-dialog/
@Composable
fun ShowRPEInfo(expanded: Boolean, setExpanded: (Boolean) -> Unit) {
    if (expanded) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Info")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setExpanded(false)
                    },
                ) {
                    Text("OK")
                }
            },
            text = {
                Text(
                    "RPE stands for Rate of Perceived Exertion, and is tool to measure the intensity of any set in your training subjectively.\n\n" +
                            "RPE gives a concrete way to measure intensity. The RPE scale is a scale of 1 to 10, with 10 being maximal effort.\n\n" +
                            "For example, if you are programmed a top set of 5 @ RPE7, that means after completing the fifth rep on the top set, " +
                            "you could have completed three more reps if you had to."
                )
            },
        )
    }
}

@Composable
@Preview
fun RPEBarPreview() {
    SportionTheme {
        Surface(color = MaterialTheme.colors.background) {
            RPEBar(rpeValue = "1.2")
        }
    }
}