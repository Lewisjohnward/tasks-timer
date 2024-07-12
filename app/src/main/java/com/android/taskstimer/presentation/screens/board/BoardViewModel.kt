package com.android.taskstimer.presentation.screens.board

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.data.board.BoardWithTimers
import com.android.taskstimer.data.board.BoardsRepository
import com.android.taskstimer.data.timer.Timer
import com.android.taskstimer.data.timer.TimersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class uiState(
    val boardName: String = "?",
    val timers: List<Timer> = listOf()
)

class BoardViewModel(
    savedStateHandle: SavedStateHandle,
    private val timersRepository: TimersRepository,
    private val boardsRepository: BoardsRepository,
) : ViewModel() {

    private val boardName: String = checkNotNull(savedStateHandle[BoardDestination.boardName])

    /**
     * Holds the item details ui state. The data is retrieved from [ItemsRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<uiState> =
        boardsRepository.getBoardsWithTimers(boardName)
            .filterNotNull()
            .map{it ->
                uiState(boardName = boardName, timers = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = uiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}