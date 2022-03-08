package fi.sportionbois.sportion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.ui.theme.SportionTheme

@Composable
fun ProgressValue(value: String) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colors.secondary), shape = CircleShape),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(value, style = MaterialTheme.typography.h2)
            Text(stringResource(id = R.string.distance))
        }
    }
}


@Composable
@Preview
fun ProgressDef() {
    SportionTheme {
        Surface(color = MaterialTheme.colors.background) {
            ProgressValue(value = "1.2")
        }
    }
}