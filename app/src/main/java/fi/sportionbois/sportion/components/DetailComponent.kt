package fi.sportionbois.sportion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *  A component used in result views to display data
 **/

@Composable
fun DetailComponent(firstValue: String, secondValue: String) {
    Surface(
        elevation = 1.dp,
        modifier = Modifier
            .height(90.dp)
            .width(90.dp)
            .background(color = MaterialTheme.colors.background)
            .width(IntrinsicSize.Min)
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                shape = RoundedCornerShape(20.dp)
            )

    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .padding(0.dp)
                .padding(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = firstValue, color = MaterialTheme.colors.onBackground)
            Text(text = secondValue, color = MaterialTheme.colors.onBackground)
        }
    }

}

@Preview
@Composable
fun DefPrev() {
    DetailComponent(firstValue = "0.3", secondValue = "distance")
}