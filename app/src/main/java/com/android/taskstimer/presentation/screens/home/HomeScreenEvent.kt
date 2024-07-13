package com.android.taskstimer.presentation.screens.home

sealed interface HomeScreenEvent {
    data object ToggleTimer: HomeScreenEvent
    data class SelectBoard(val boardIndex: Int): HomeScreenEvent
}