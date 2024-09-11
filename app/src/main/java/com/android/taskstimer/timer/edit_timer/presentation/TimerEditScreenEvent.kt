package com.android.taskstimer.timer.edit_timer.presentation

interface TimerEditScreenEvent {
    data object UpdateTimer : TimerEditScreenEvent
    data class UpdateName(val name: String) : TimerEditScreenEvent
    data class UpdateTimerTime(val time: String) : TimerEditScreenEvent
}