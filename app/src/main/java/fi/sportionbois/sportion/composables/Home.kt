package fi.sportionbois.sportion.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fi.sportionbois.sportion.CenteredColumnMaxWidthAndHeight
import fi.sportionbois.sportion.Message
import fi.sportionbois.sportion.components.HomeDataCard

@Composable
fun Home(navController: NavController) {
    CenteredColumnMaxWidthAndHeight {
        LazyColumn() {
            items(2) {
                HomeDataCard(
                    data = Message(
                        "Ville Valo", "Laulanta", "8",
                        "1.4s", "50kg", "3"
                    ), navController = navController
                )
                HomeDataCard(
                    data = Message(
                        "Ville Valo2", "Laulanta", "3",
                        "1.4s", "50kg", "3"
                    ), navController = navController
                )
            }
        }
    }
}