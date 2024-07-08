package com.android.taskstimer.presentation.screens.home

sealed interface HomeScreenEvent {
    data object ToggleTimer: HomeScreenEvent
}