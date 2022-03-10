package fi.sportionbois.sportion.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Objects to use for BottomNavigation routes
 */

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screen_route: String) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object StartTracking : BottomNavItem("Start Tracking", Icons.Filled.Add, "start_tracking")
    object Settings : BottomNavItem("Settings", Icons.Filled.Settings, "settings")
}