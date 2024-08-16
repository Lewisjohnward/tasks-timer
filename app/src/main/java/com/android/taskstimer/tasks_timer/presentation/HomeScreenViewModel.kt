package com.android.taskstimer.tasks_timer.presentation

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
    val running: Boolean = false,
    val finished: Boolean = false,
    val coroutineId: Job? = null,
    val currentTimerIndex: Int = 0,

    val editBoards: Boolean = false,

    val displayDialog: ConfirmDialog? = null,

    val boards: List<BoardItem> = listOf(),
    val timers: List<TimerItem> = listOf(),
    val selectedBoard: BoardItem = BoardItem(name = ""),
)


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertBoard: InsertBoard,
    private val getTimersFlow: GetTimersFlow,
    private val getBoardsFlow: GetBoardsFlow,
    private val deleteBoard: DeleteBoard
) : ViewModel() {

    private val _boards = getBoardsFlow()
    private val _currentBoard = MutableStateFlow(BoardItem(id = 1, name = ""))
    private val _timers = _currentBoard.flatMapLatest { board ->
        getTimersFlow.invoke(boardId = board.id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    private val _uiState = MutableStateFlow(UiState())

    val uiState =
        combine(
            _timers,
            _uiState,
            _boards,
            _currentBoard
        ) { timers, uiState, boards, currentBoard ->
            uiState.copy(
                timers = timers,
                boards = boards,
                selectedBoard = currentBoard

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UiState()
        )

    init {
        viewModelScope.launch {
            println(getBoardsFlow().first())
            val board = getBoardsFlow().first()
            _currentBoard.update { if (board.isEmpty()) BoardItem() else board[0] }
        }

        println("Init view model")
    }

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
                        )
                    )
                }
            }

            HomeScreenEvent.DialogConfirm -> {
                viewModelScope.launch {
                    deleteBoard.invoke(_currentBoard.value)
                }
                _uiState.update { it.copy(displayDialog = null) }
            }

            HomeScreenEvent.DialogCancel -> {
                _uiState.update { it.copy(displayDialog = null) }
            }
        }
    }
}
