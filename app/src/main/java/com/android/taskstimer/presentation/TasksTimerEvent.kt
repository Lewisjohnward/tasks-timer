package com.android.taskstimer.presentation

sealed interface TasksTimerEvent {
    data object ToggleTimer: TasksTimerEvent
}