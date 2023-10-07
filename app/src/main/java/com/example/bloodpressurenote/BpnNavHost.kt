package com.example.bloodpressurenote

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bloodpressurenote.components.screens.InputScreen.InputScreen
import com.example.bloodpressurenote.components.screens.StatisticsScreen

@Composable
fun BpnNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Input.route,
        modifier = modifier
    ) {
        composable(route = Input.route) {
            InputScreen(
                message = "This is message from NavHost"
            )
        }
        composable(route = Statistics.route) {
            StatisticsScreen(
                message = "This is message from NavHost"
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
