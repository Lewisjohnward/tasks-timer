package com.android.taskstimer.tasks_timer.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteBoard
import com.android.taskstimer.tasks_timer.domain.use_case.GetBoardsFlow
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface BoardToLoad {
    data class BoardId(val int: Int) : BoardToLoad
    data object NullBoard : BoardToLoad
}


data class HomeScreenUiState(
    val editBoards: Boolean = false,
    val displayMenu: Boolean = false,
    val displayDialog: ConfirmDialog? = null,

    val boards: List<BoardItem> = listOf(),
    val currentBoardIndex: Int = 0
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertBoard: InsertBoard,
    private val getBoardsFlow: GetBoardsFlow,
    private val deleteBoard: DeleteBoard
) : ViewModel() {


    var boardToLoad: MutableState<BoardToLoad?> = mutableStateOf(null)


    private val _boards = getBoardsFlow()
    private val _uiState = MutableStateFlow(HomeScreenUiState())

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
            initialValue = HomeScreenUiState()
        )

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.EditBoards -> {
                _uiState.update { it.copy(editBoards = event.edit) }
            }

            is HomeScreenEvent.CreateBoard -> {
                viewModelScope.launch {
                    insertBoard(BoardItem(name = event.name))
                    // If this is the first board created load into service/ui
                    if (uiState.value.boards.isEmpty()) {
                        if (getBoardsFlow().first().isNotEmpty()) {
                            boardToLoad.value = BoardToLoad.BoardId(getBoardsFlow().first()[0].id)
                        }
                    }
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

            is HomeScreenEvent.DialogConfirm -> {
                viewModelScope.launch {
                    uiState.value.displayDialog?.let { deleteBoard.invoke(it.boardItem) }

                    _uiState.update {
                        it.copy(
                            displayDialog = null,
                            displayMenu = false
                        )
                    }

                    val boardIndexToLoad = boardIndexToLoad(
                        boardCount = getBoardsFlow().first().size,
                        deletedBoard = uiState.value.currentBoardIndex
                    )


                    if (boardIndexToLoad != null) {
                        boardToLoad.value =
                            BoardToLoad.BoardId(uiState.value.boards[boardIndexToLoad].id)
                        _uiState.update { it.copy(currentBoardIndex = boardIndexToLoad) }
                    } else {
                        boardToLoad.value = BoardToLoad.NullBoard
                        _uiState.update { it.copy(currentBoardIndex = 0) }
                    }

                }


            }

            is HomeScreenEvent.DialogCancel -> {
                _uiState.update { it.copy(displayDialog = null, displayMenu = false) }
            }

            is HomeScreenEvent.DisplayMenu -> {
                _uiState.update { it.copy(displayMenu = event.displayMenu) }
            }

            is HomeScreenEvent.SelectBoard -> {
                _uiState.update { it.copy(currentBoardIndex = event.boardIndex) }
            }
        }
    }

    private fun boardIndexToLoad(boardCount: Int, deletedBoard: Int): Int? {
        // First board
        if (deletedBoard == 0 && boardCount > 0) return 0
        // Middle board
        if (boardCount != deletedBoard) return boardCount - 1
        // Last board
        if (boardCount > 0) return boardCount - 1
        // All boards deleted
        return null
    }
}

