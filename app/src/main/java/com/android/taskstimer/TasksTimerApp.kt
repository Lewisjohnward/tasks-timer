package com.android.taskstimer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.taskstimer.presentation.navigation.TasksTimerNavHost
import com.android.taskstimer.ui.theme.TasksTimerTheme


@Composable
fun TasksTimerApp(navController: NavHostController = rememberNavController()) {
    TasksTimerTheme {
        TasksTimerNavHost(navController = navController)
    }
}