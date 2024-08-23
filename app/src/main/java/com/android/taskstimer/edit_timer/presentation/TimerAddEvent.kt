package com.android.taskstimer.edit_timer.presentation

sealed interface TimerAddEvent{
    data object AddTimer : TimerAddEvent
    data class UpdateName(val name: String): TimerAddEvent
}