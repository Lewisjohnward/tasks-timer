package com.android.taskstimer.tasks_timer.presentation

sealed interface HomeScreenEvent {
    data object ToggleTimer: HomeScreenEvent
    data class SelectBoard(val boardIndex: Int): HomeScreenEvent
    data object ToggleRearrangeBoards: HomeScreenEvent
    data class CreateBoard(val name: String) : HomeScreenEvent
}