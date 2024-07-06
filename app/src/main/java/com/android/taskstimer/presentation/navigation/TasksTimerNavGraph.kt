package com.android.taskstimer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = TimerAddDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen()
        }
        composable(route = TimerAddDestination.route) {
            TimerAddScreen { navController.popBackStack() }
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
