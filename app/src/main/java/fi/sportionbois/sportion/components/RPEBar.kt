package fi.sportionbois.sportion.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RPEBar(rpeValue: String){
    OutlinedButton(onClick = { /*TODO Open info page / popup about RPE*/ },
        modifier= Modifier
            .size(120.dp)
            .shadow(4.dp, CircleShape, clip = false),
        shape = CircleShape,
        border= BorderStroke(4.dp, MaterialTheme.colors.primary),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Black)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Text(rpeValue, style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(4.dp))
            Text("RPE", style = MaterialTheme.typography.subtitle1)
        }

    }
}