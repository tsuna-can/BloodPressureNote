package com.example.bloodpressurenote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bloodpressurenote.ui.screens.StatisticsScreen
import com.example.bloodpressurenote.ui.screens.calendar.CalendarScreen
import com.example.bloodpressurenote.ui.screens.input.InputScreen

@Composable
fun BpnNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Input.route,
        modifier = modifier,
    ) {
        composable(route = Input.route) {
            InputScreen()
        }
        composable(route = Statistics.route) {
            StatisticsScreen(
                message = "This is message from NavHost",
            )
        }
        composable(route = Calendar.route) {
            CalendarScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
