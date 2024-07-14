package com.android.taskstimer.presentation.screens.timers

import com.android.taskstimer.data.timer.Timer
import com.android.taskstimer.presentation.screens.home.HomeScreenEvent

sealed interface TimerAddEvent{
    data object AddTimer : TimerAddEvent
}