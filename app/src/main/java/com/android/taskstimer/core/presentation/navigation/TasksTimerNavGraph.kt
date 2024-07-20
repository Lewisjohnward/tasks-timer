package com.android.taskstimer.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.taskstimer.tasks_timer.presentation.HomeDestination
import com.android.taskstimer.tasks_timer.presentation.HomeScreen
import com.android.taskstimer.edit_timer.presentation.TimerAddDestination
import com.android.taskstimer.edit_timer.presentation.TimerAddScreen


@Composable
fun TasksTimerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToAddTimer = {
                    navController.navigate("${TimerAddDestination.route}/${it}")
                }
            )
        }
        composable(
            route = TimerAddDestination.routeWithArgs,
            arguments = listOf(navArgument(name = "boardId"){type = NavType.IntType})
        ) {
            TimerAddScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}
