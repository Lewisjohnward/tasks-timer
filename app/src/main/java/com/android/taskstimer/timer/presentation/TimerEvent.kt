package com.android.taskstimer.timer.presentation

import com.android.taskstimer.timer.presentation.components.Side

sealed interface TimerEvent{
    data object AddTimer : TimerEvent
    data class UpdateTimer(val name: String): TimerEvent



    data class ChangeFocus(val side: Side): TimerEvent
    data object Increment : TimerEvent
    data object Decrement : TimerEvent
    data object Delete : TimerEvent
    data class InputValue(val value: Int) : TimerEvent
    data object Add : TimerEvent


}