package com.analog.taskly.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.analog.taskly.ui.navigation.BottomNavBar
import com.analog.taskly.ui.navigation.BottomNavItems
import com.analog.taskly.ui.navigation.NavGraph
import com.analog.taskly.ui.navigation.Screen


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var selectedRoute by remember { mutableStateOf(Screen.Inbox.route) }

    Column {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            NavGraph(navController)
        }
//
//        BottomNavBar(
//            navigationItems = BottomNavItems,
//            selectedRoute = selectedRoute,
//            onItemSelected = { route ->
//                selectedRoute = route
//                navController.navigate(route)
//            }
//        )
    }
}


