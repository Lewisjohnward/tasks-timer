package com.android.taskstimer.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.taskstimer._other.service.TasksTimerService
import com.android.taskstimer.tasks_timer.presentation.HomeDestination
import com.android.taskstimer.tasks_timer.presentation.HomeScreen
import com.android.taskstimer.timer.add_timer.presentation.TimerAddDestination
import com.android.taskstimer.timer.add_timer.presentation.TimerAddScreen
import com.android.taskstimer.settings.presentation.SettingsDestination
import com.android.taskstimer.settings.presentation.SettingsScreen
import com.android.taskstimer.timer.edit_timer.presentation.TimerEditDestination
import com.android.taskstimer.timer.edit_timer.presentation.TimerEditScreen


@Composable
fun TasksTimerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    tasksTimerService: TasksTimerService,
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
                },
                navigateToEditTimer = {
                    navController.navigate("${TimerEditDestination.route}/${it}")
                },
                tasksTimerService = tasksTimerService
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

        composable(
            route = TimerEditDestination.routeWithArgs,
            arguments = listOf(navArgument(name = "timerId"){type = NavType.IntType})
        ) {
            TimerEditScreen(
                navigateBack = { navController.popBackStack() },
            )
        }



        composable(route = SettingsDestination.route) {
            SettingsScreen { navController.popBackStack() }
        }
    }
}

