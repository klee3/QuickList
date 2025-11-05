package com.analog.taskly.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Inbox : Screen("inbox", "Inbox", Lucide.House)
    object Today : Screen("today", "Today", Lucide.House)
    object Upcoming : Screen("upcoming", "Upcoming", Lucide.House)
    object Settings : Screen("settings", "Settings", Lucide.House)
}

