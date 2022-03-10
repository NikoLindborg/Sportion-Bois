package fi.sportionbois.sportion.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.R
import fi.sportionbois.sportion.viewmodels.UserViewModel
import kotlinx.coroutines.delay

/**
 * A simple splash screen to display name and logo at the launch.
 *
 * Used https://www.youtube.com/watch?v=EaVuHjCh6CU as a tutorial for this
 **/

@Composable
fun SplashScreen(userViewModel: UserViewModel) {

    var isSplashShown by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(2000L)
        isSplashShown = true
    }

    //  After splash has been shown the Create user will be shown (only if the user has not been inserted).
    if (!isSplashShown) {
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
                Text(
                    text = stringResource(id = R.string.app_name_lowercase),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    } else {
        CreateUser(userViewModel = userViewModel)
    }
}