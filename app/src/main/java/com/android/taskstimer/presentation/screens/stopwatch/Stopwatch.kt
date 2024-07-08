package com.android.taskstimer.presentation.screens.stopwatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.taskstimer.presentation.navigation.NavigationDestination


object StopwatchDestination : NavigationDestination {
    override val route: String = "stopwatch"
    override val title: String = "Stopwatch"
}

@Composable
fun Stopwatch() {
    Scaffold(
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Stopwatch composable ")
        }
    }
}

@Preview
@Composable
fun StopwatchPreview(){
    Stopwatch()
}