package com.android.taskstimer.timer.presentation

sealed interface TimerEvent{
    data object AddTimer : TimerEvent
    data class UpdateName(val name: String): TimerEvent

}