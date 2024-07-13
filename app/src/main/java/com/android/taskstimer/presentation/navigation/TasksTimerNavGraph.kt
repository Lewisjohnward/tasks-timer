package com.android.taskstimer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.taskstimer.presentation.screens.board.Board
import com.android.taskstimer.presentation.screens.board.BoardDestination
import com.android.taskstimer.presentation.screens.home.HomeDestination
import com.android.taskstimer.presentation.screens.home.HomeScreen
import com.android.taskstimer.presentation.screens.timers.TimerAddDestination
import com.android.taskstimer.presentation.screens.timers.TimerAddScreen


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
//                navigateToBoard = {
//                    navController.navigate("${BoardDestination.route}/${it}")
//                },
                navigateToAddTimer = { navController.navigate(TimerAddDestination.route) }
            )
        }
        composable(
            route = BoardDestination.routeWithArgs,
            // "board/itemId"
            arguments = listOf(navArgument(BoardDestination.boardName) {
                // "itemId"
                type = NavType.StringType
            })
        ) {
            Board()
        }

        composable(route = TimerAddDestination.route) {
            TimerAddScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}

