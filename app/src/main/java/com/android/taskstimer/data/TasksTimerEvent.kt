package com.android.taskstimer.data

sealed interface TasksTimerEvent {
    data object ToggleTimer: TasksTimerEvent
}