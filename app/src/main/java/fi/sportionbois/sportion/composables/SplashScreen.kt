package fi.sportionbois.sportion.composables

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import fi.sportionbois.sportion.R


@Composable

fun SplashScreen(userViewModel: UserViewModel) {

    //  https://www.youtube.com/watch?v=EaVuHjCh6CU used as tutorial for splash
    //  TODO: Mod so that sportion logo will be shown, copypasted for now from the tutorial

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    var hasAnimated by remember { mutableStateOf(false) }
    
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(1500L)
        hasAnimated = true
    }

    if (!hasAnimated) {
        Box(
            modifier = Modifier
                .padding()
                .fillMaxSize()
        ) {
           CenteredColumnMaxWidthAndHeight {
               Image(
                   painterResource(R.drawable.sportion_logo),
                   contentDescription = ""
               )
               Text(text = "sportion", style = MaterialTheme.typography.h1)
           }
        }
    } else {
        CreateUser(userViewModel = userViewModel)
    }
}