package com.android.taskstimer.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


data class Timer(
    val name: String = "Timer 1",
    val remainingTime: String = "60.00",
    val time: String = "60.00"
)

val timers: List<Timer> = listOf(
    Timer(
        name = "Timer 1",
        remainingTime = "60.00",
        time = "60.00"
    ),
    Timer(
        name = "Timer 2",
        remainingTime = "60.00",
        time = "60.00"
    ),
    Timer(
        name = "Timer 3",
        remainingTime = "60.00",
        time = "60.00"
    )
)

@Composable
fun Timer(
    timer: Timer = timers[0]
) {
    Column() {
        Text(
            text = timer.name,
            color = Color.White
        )
        Text(
            text = timer.remainingTime,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    Timer()
}