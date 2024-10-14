package com.android.taskstimer.tasks_timer.presentation

import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.presentation.ui.IconKey

sealed interface HomeScreenEvent {
    data class EditBoards(val edit: Boolean) : HomeScreenEvent
    data object DeleteBoard : HomeScreenEvent
    data class DeleteTimer(val timer: TimerItem) : HomeScreenEvent
    data class SelectBoard(
        val boardIndex: Int,
        val boardId: Int
    ) : HomeScreenEvent

    data object DialogConfirm : HomeScreenEvent
    data object DialogCancel : HomeScreenEvent
    data class DisplayMenu(val displayMenu: Boolean) : HomeScreenEvent
    data object CreateNewBoard : HomeScreenEvent
    data object CancelCreateNewBoard : HomeScreenEvent
    data class NameNewBoard(val name: String) : HomeScreenEvent
    data class AssignIconNewBoard(val iconKey: IconKey) : HomeScreenEvent
    data object AcceptNewBoard: HomeScreenEvent

}