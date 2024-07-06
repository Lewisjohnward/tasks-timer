package com.android.taskstimer.presentation.screens.timers

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.android.taskstimer.presentation.navigation.NavigationDestination

object TimerAddDestination : NavigationDestination {
    override val route = "home"
    override val title = ""
}

@Composable
fun TimerAddScreen(navigateBack: () -> Boolean) {
    Column {
        Text(text = "Timer add screen")
    }
}
