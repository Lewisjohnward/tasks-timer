package com.android.taskstimer.timer.add_timer.presentation

sealed interface TimerAddEvent{
    data object AddTimer : TimerAddEvent
    data class UpdateName(val name: String): TimerAddEvent

}