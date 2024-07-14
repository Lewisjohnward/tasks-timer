package com.android.taskstimer.presentation.screens.home

import com.android.taskstimer.data.timer.Timer

sealed interface HomeScreenEvent {
    data object ToggleTimer: HomeScreenEvent
    data class SelectBoard(val boardIndex: Int): HomeScreenEvent

    data object ToggleRearrangeBoards: HomeScreenEvent

    data class CreateBoard(val name: String) : HomeScreenEvent

}