package fi.sportionbois.sportion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fi.sportionbois.sportion.ui.theme.SportionTheme

@Composable
fun SportTypeButton(
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
            contentAlignment = Alignment.Center,
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
        }
    }
}

@Composable
@Preview
fun SportTypeCardPreview() {
    SportionTheme {
        Surface(color = MaterialTheme.colors.background) {
            SportTypeButton(text = "Biking") {

            }
        }
    }
}