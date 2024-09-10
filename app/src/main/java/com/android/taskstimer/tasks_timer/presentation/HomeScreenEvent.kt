package com.android.taskstimer.tasks_timer.presentation

import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem

sealed interface HomeScreenEvent {
    data class EditBoards(val edit: Boolean): HomeScreenEvent
    data class CreateBoard(val name: String) : HomeScreenEvent
    data class DeleteBoard(val board: BoardItem): HomeScreenEvent
    data class DeleteTimer(val timer: TimerItem) : HomeScreenEvent
    data class SelectBoard(val boardIndex: Int): HomeScreenEvent
    data object DialogConfirm: HomeScreenEvent
    data object DialogCancel: HomeScreenEvent

    data class DisplayMenu(val displayMenu: Boolean): HomeScreenEvent

}