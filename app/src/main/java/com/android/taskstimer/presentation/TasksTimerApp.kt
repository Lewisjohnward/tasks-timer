package com.android.taskstimer.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.taskstimer.presentation.navigation.TasksTimerNavHost


@Composable
fun TasksTimerApp(navController: NavHostController = rememberNavController()){
    TasksTimerNavHost(navController = navController)
}