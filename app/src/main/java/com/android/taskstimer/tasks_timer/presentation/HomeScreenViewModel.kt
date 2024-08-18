package com.android.taskstimer.tasks_timer.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteBoard
import com.android.taskstimer.tasks_timer.domain.use_case.GetBoardsFlow
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimersFlow
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiState(
    val editBoards: Boolean = false,
    val displayMenu: Boolean = false,
    val displayDialog: ConfirmDialog? = null,

    val boards: List<BoardItem> = listOf(),
)


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertBoard: InsertBoard,
    private val getBoardsFlow: GetBoardsFlow,
    private val deleteBoard: DeleteBoard
) : ViewModel() {

    private val _boards = getBoardsFlow()
    private val _currentBoard = MutableStateFlow(BoardItem(id = 1, name = ""))

    var boardDeleted = mutableStateOf(false)


    private val _uiState = MutableStateFlow(UiState())

    val uiState =
        combine(
            _uiState,
            _boards,
        ) { uiState, boards ->
            uiState.copy(
                boards = boards,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UiState()
        )


    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.EditBoards -> {
                _uiState.update { it.copy(editBoards = event.edit) }
            }

            is HomeScreenEvent.CreateBoard -> {
                viewModelScope.launch {
                    insertBoard(BoardItem(name = event.name))
                }
            }

            is HomeScreenEvent.DeleteBoard -> {
                _uiState.update {
                    it.copy(
                        displayDialog = ConfirmDialog(
                            message = "Are you sure you want to delete this board?",
                            boardItem = event.board
                        ),
                    )
                }
            }

            HomeScreenEvent.DialogConfirm -> {
                viewModelScope.launch {
                    println(uiState.value.displayDialog?.boardItem)
                    uiState.value.displayDialog?.let { deleteBoard.invoke(it.boardItem) }
                }
                _uiState.update { it.copy(
                    displayDialog = null,
                    displayMenu = false
                ) }
                boardDeleted.value = true
            }

            HomeScreenEvent.DialogCancel -> {
                _uiState.update { it.copy(displayDialog = null, displayMenu = false) }
            }

            is HomeScreenEvent.DisplayMenu -> {
                _uiState.update { it.copy(displayMenu = event.displayMenu) }
            }
        }
    }
}
