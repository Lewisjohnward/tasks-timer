package com.android.taskstimer.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.taskstimer.settings.presentation.SettingsDestination
import com.android.taskstimer.settings.presentation.SettingsScreen
import com.android.taskstimer.tasks_timer.presentation.HomeDestination
import com.android.taskstimer.tasks_timer.presentation.HomeScreen
import com.android.taskstimer.timer.presentation.TimerDestination
import com.android.taskstimer.timer.presentation.TimerScreen


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
                navigateToTimer = { boardId, timerId ->
                    navController.navigate(
                        "${TimerDestination.route}/${boardId}/${timerId}"
                    )
                },
                navigateToSettings = {
                    navController.navigate(
                        SettingsDestination.route
                    )
                },
            )
        }

        composable(
            route = TimerDestination.routeWithArgs,
            arguments = listOf(
                navArgument(name = "boardId") { type = NavType.IntType },
                navArgument(name = "timerId") { type = NavType.IntType },
            )
        ) {
            TimerScreen(
                navigateBack = { navController.popBackStack() },
            )
        }

        composable(route = SettingsDestination.route) {
            SettingsScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

