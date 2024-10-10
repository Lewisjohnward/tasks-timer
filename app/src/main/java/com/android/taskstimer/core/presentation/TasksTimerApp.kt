package com.android.taskstimer.core.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.taskstimer.core.presentation.navigation.TasksTimerNavHost
import com.android.taskstimer.core.presentation.ui.theme.TasksTimerTheme


@Composable
fun TasksTimerApp(
    navController: NavHostController = rememberNavController(),
) {
    TasksTimerTheme {
        TasksTimerNavHost(
            navController = navController,
        )
    }
}