package fi.sportionbois.sportion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = ChineseViolet,
    primaryVariant = MiddleBlue,
    secondary = MediumTurquoise,
    background = SpaceCrayola,
    onBackground = LightGray,
    onPrimary = CustomGreen,
)

private val LightColorPalette = lightColors(
    primary = ChineseViolet,
    primaryVariant = Color.White,
    secondary = MediumTurquoise,
    background = Color.White,
    onBackground = SpaceCrayola,
    onPrimary = MiddleBlue

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SportionTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    //  Checks the system theme and sets the right color pallettes
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}