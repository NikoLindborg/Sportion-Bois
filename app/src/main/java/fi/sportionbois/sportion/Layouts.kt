package fi.sportionbois.sportion

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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


