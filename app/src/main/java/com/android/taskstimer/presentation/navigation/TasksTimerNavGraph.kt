package com.android.taskstimer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.taskstimer.presentation.screens.home.HomeDestination
import com.android.taskstimer.presentation.screens.home.HomeScreen
import com.android.taskstimer.presentation.screens.simpletimer.SimpleTimer
import com.android.taskstimer.presentation.screens.simpletimer.SimpleTimerDestination
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
                navController = navController
            )
        }

        composable(route = SimpleTimerDestination.route) {
            SimpleTimer(
                navController = navController
            )
        }
        composable(route = TimerAddDestination.route) {
            TimerAddScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
//        composable(
//            route = ItemDetailsDestination.routeWithArgs,
//            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
//                type = NavType.IntType
//            })
//        ) {
//            ItemDetailsScreen(
//                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
//                navigateBack = { navController.navigateUp() }
//            )
//        }
//        composable(
//            route = ItemEditDestination.routeWithArgs,
//            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
//                type = NavType.IntType
//            })
//        ) {
//            ItemEditScreen(
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
//            )
//        }
    }
}
