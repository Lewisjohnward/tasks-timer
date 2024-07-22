package com.android.taskstimer.tasks_timer.presentation

import com.android.taskstimer.core.domain.model.BoardItem

sealed interface HomeScreenEvent {
    data object ToggleTimer: HomeScreenEvent
    data class SelectBoard(val board: BoardItem): HomeScreenEvent
    data class EditBoards(val edit: Boolean): HomeScreenEvent
    data class CreateBoard(val name: String) : HomeScreenEvent
    data class DeleteBoard(val board: BoardItem): HomeScreenEvent
}