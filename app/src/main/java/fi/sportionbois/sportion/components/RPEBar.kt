package fi.sportionbois.sportion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.sportionbois.sportion.ButtonCHViolet
import fi.sportionbois.sportion.ui.theme.SportionTheme

@Composable
fun RPEBar(rpeValue: String) {
    OutlinedButton(
        onClick = { /*TODO Open info page / popup about RPE*/ },
        shape = CircleShape,
        modifier = Modifier
            .size(120.dp)
            .shadow(4.dp, CircleShape, clip = false)
            .border(BorderStroke(2.dp, MaterialTheme.colors.secondary), shape = CircleShape),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colors.onBackground,
            backgroundColor = MaterialTheme.colors.background,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(rpeValue, style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(4.dp))
            Text("RPE", style = MaterialTheme.typography.subtitle1)
        }

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