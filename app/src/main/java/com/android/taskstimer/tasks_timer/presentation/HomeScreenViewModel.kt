package com.android.taskstimer.tasks_timer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer._other.service.TasksTimerManager
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.presentation.ui.IconKey
import com.android.taskstimer.tasks_timer.domain.data.DeleteDialog
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteBoard
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteTimer
import com.android.taskstimer.tasks_timer.domain.use_case.GetBoardsFlow
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import javax.inject.Inject


enum class CreateBoardDialog {
    NAME_BOARD,
    CHOOSE_ICON
}

data class NewBoardDetails(
    val name: String = "",
    val iconKey: IconKey = IconKey.DEFAULT
)


data class HomeScreenUiState(
    val editBoards: Boolean = false,
    val displayBoardMenu: Boolean = false,
    val displayConfirmDialog: DeleteDialog? = null,
    val boardMenuEnabled: Boolean = false,

    val createBoard: CreateBoardDialog? = null,
    val newBoardDetails: NewBoardDetails = NewBoardDetails(),

    val boards: List<BoardItem> = listOf(),


    val currentBoardIndex: Int = 0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertBoard: InsertBoard,
    private val getBoardsFlow: GetBoardsFlow,
    private val deleteBoard: DeleteBoard,
    private val deleteTimer: DeleteTimer,
    private val tasksTimerManager: TasksTimerManager,
    // TODO: THIS NEEDS TO BE A USECASE
    private val boardsRepo: BoardsRepository
) : ViewModel() {


    private val _boards = getBoardsFlow()
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val tasksTimerManagerState = tasksTimerManager.state

    val uiState =
        combine(
            _uiState,
            _boards,
        ) { uiState, boards ->
            val boardMenuEnabled = boards.isNotEmpty()
            uiState.copy(
                boards = boards,
                boardMenuEnabled = boardMenuEnabled
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeScreenUiState()
        )


    init {
        loadInitialBoard()
    }

    // TODO: CREATE USECASE FOR INIT BOARD
    private fun loadInitialBoard() {
        viewModelScope.launch {
            // TODO: IS THERE A BETTER WAY THAN LOOP ON FIRST LAUNCH?
            var count = 0
            while(boardsRepo.getInitBoard() == null && count < 2){
                delay(100)
                count++
            }
            val board = boardsRepo.getInitBoard()
                if (board != null) {
                    tasksTimerManager.loadBoard(board.id)
            }
        }
    }

    // TODO: PUT THIS AS AN EVENT
    fun loadBoard() {
        if (uiState.value.boards.isNotEmpty()) {
            // TODO: THIS IS UGLY
            val board = uiState.value.boards[uiState.value.currentBoardIndex]
            tasksTimerManager.loadBoard(board.id)
        }
    }

    // TODO: PUT IN EVENTS
    fun startTimer(index: Int) {
        tasksTimerManager.startTimer(index)
    }

    fun pauseTimer() {
        tasksTimerManager.stopTimer()
    }

    fun resetTimer(timerIndex: Int) {
        tasksTimerManager.resetTimer(timerIndex)
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.EditBoards -> {
                _uiState.update { it.copy(editBoards = event.edit) }
            }

            is HomeScreenEvent.DeleteBoard -> {
                _uiState.update {
                    it.copy(
                        displayConfirmDialog = DeleteDialog.Board(uiState.value.boards[uiState.value.currentBoardIndex])
                    )
                }
            }

            is HomeScreenEvent.DeleteTimer -> {
                _uiState.update {
                    it.copy(
                        displayConfirmDialog = DeleteDialog.Timer(timer = event.timer)
                    )
                }
            }

            is HomeScreenEvent.DialogConfirm -> {
                viewModelScope.launch {
                    uiState.value.displayConfirmDialog?.let { deleteDialog ->
                        when (deleteDialog) {
                            is DeleteDialog.Board -> deleteBoard(board = deleteDialog.board)
                            is DeleteDialog.Timer -> deleteTimer(timer = deleteDialog.timer)
                        }
                    }
                }
            }

            is HomeScreenEvent.DialogCancel -> {
                _uiState.update { it.copy(displayConfirmDialog = null, displayBoardMenu = false) }
            }

            is HomeScreenEvent.DisplayMenu -> {
                _uiState.update { it.copy(displayBoardMenu = event.displayMenu) }
            }

            is HomeScreenEvent.SelectBoard -> {
                tasksTimerManager.loadBoard(event.boardId)

                _uiState.update {
                    it.copy(
                        currentBoardIndex = event.boardIndex,
                        boardMenuEnabled = true
                    )
                }
            }

            is HomeScreenEvent.CreateNewBoard -> {
                _uiState.update {
                    it.copy(createBoard = CreateBoardDialog.NAME_BOARD)
                }
            }

            is HomeScreenEvent.NameNewBoard -> {
                _uiState.update {
                    it.copy(
                        createBoard = CreateBoardDialog.CHOOSE_ICON,
                        newBoardDetails = it.newBoardDetails.copy(
                            name = event.name
                        )
                    )
                }
            }

            is HomeScreenEvent.AssignIconNewBoard -> {
                _uiState.update {
                    it.copy(
                        createBoard = null,
                        newBoardDetails = it.newBoardDetails.copy(
                            iconKey = event.iconKey
                        )
                    )
                }

                viewModelScope.launch {
                    insertBoard(
                        BoardItem(
                            name = _uiState.value.newBoardDetails.name,
                            iconKey = _uiState.value.newBoardDetails.iconKey
                        )
                    )
                    // If this is the first board created load into service/ui
                    if (uiState.value.boards.isEmpty()) {
                        if (getBoardsFlow().first().isNotEmpty()) {
                            loadBoard()
                        }
                    }
                }
            }

            HomeScreenEvent.CancelCreateNewBoard -> {
                _uiState.update { it.copy(createBoard = null) }
            }
        }
    }

    private fun deleteTimer(timer: TimerItem) = viewModelScope.launch {
        deleteTimer.invoke(timer)

        loadBoard()

        _uiState.update {
            it.copy(
                displayConfirmDialog = null,
                displayBoardMenu = false
            )
        }
    }


    private fun deleteBoard(board: BoardItem) = viewModelScope.launch {
        deleteBoard.invoke(board)
        val boardIndexToLoad = determineBoardIndexToLoad(
            boardCount = _boards.first().size,
            deletedBoardIndex = uiState.value.currentBoardIndex
        )
        if (boardIndexToLoad != null) {
            _uiState.update {
                it.copy(
                    currentBoardIndex = boardIndexToLoad,
                    displayConfirmDialog = null,
                    displayBoardMenu = false
                )
            }
            loadBoard()
            return@launch
        }

        _uiState.update {
            it.copy(
                currentBoardIndex = 0,
                boardMenuEnabled = false,
                displayConfirmDialog = null,
                displayBoardMenu = false
            )
        }
        tasksTimerManager.unloadBoard()
    }
}

private fun determineBoardIndexToLoad(boardCount: Int, deletedBoardIndex: Int): Int? {
    // First board
    if (deletedBoardIndex == 0 && boardCount > 0) return 0
    // Middle board
    if (boardCount != deletedBoardIndex) return boardCount - 1
    // Last board
    if (boardCount > 0) return boardCount - 1
    // All boards deleted
    return null
}


