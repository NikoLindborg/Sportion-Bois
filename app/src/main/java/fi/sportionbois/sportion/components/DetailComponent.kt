package fi.sportionbois.sportion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun DetailComponent(firstValue: String, secondValue: String) {
    Surface(
        elevation = 1.dp,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp)
                .padding(7.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            Text(text = firstValue)
            Text(text = secondValue)
        }
    }

}