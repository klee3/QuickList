package com.analog.taskly.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.analog.taskly.ui.screens.inbox.InboxScreen
import com.analog.taskly.ui.screens.settings.SettingsScreen
import com.analog.taskly.ui.screens.today.TodayScreen
import com.analog.taskly.ui.screens.upcoming.UpcomingScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Inbox.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 2 }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 2 }, animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }) {
        composable(Screen.Inbox.route) { InboxScreen() }
        composable(Screen.Today.route) { TodayScreen() }
        composable(Screen.Upcoming.route) { UpcomingScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}