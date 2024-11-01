package com.android.taskstimer.tasks_timer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer._other.service.RUNSTATE
import com.android.taskstimer._other.service.TasksTimerManager
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import com.android.taskstimer.core.presentation.ui.IconKey
import com.android.taskstimer.tasks_timer.domain.data.DeleteDialog
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteBoard
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteTimer
import com.android.taskstimer.tasks_timer.domain.use_case.GetBoardsFlow
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.TimeMark
import kotlin.time.TimeSource


enum class CreateBoardDialog {
    NAME_BOARD,
    CHOOSE_ICON
}

enum class CreateBoardType {
    UPDATE,
    NEW
}

data class NewBoardDetails(
    val id: Int = 0,
    val name: String = "",
    val iconKey: IconKey = IconKey.DEFAULT
)


data class HomeScreenUiState(
    val editBoards: Boolean = false,
    val displayBoardMenu: Boolean = false,
    val displayConfirmDialog: DeleteDialog? = null,
    val boardMenuEnabled: Boolean = false,

    val createBoard: CreateBoardDialog? = null,
    val boardType: CreateBoardType = CreateBoardType.NEW,
    val newBoardDetails: NewBoardDetails = NewBoardDetails(),

    val timers: List<TimerItem> = listOf(),
    val boards: List<BoardItem> = listOf(),

    val boardTitle: String = "",
    val boardId: Int = 0,
    val currentBoardIndex: Int = 0,

    val active: RUNSTATE = RUNSTATE.STOPPED,
    val currentTimerIndex: Int = 0,

    val showSnackBarEvent: SnackBarEvent? = null,
    val fabVisible: Boolean = true,
    val deletedBoard: BoardItem? = null,
    val deletedTimers: List<TimerItem> = emptyList(),
    val deletedTimer: TimerItem? = null
)

data class SnackBarEvent(
    val timeMark: TimeMark? = null,
    val message: String = "",
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertBoard: InsertBoard,
    private val getBoardsFlow: GetBoardsFlow,
    private val deleteBoard: DeleteBoard,
    private val deleteTimer: DeleteTimer,
    private val tasksTimerManager: TasksTimerManager,
    // TODO: THIS NEEDS TO BE A USECASE
    private val boardsRepo: BoardsRepository,
    // TODO: THIS NEEDS TO BE A USECASE
    private val timersRepo: TimersRepository
) : ViewModel() {


    private val _boards = getBoardsFlow()
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    private val _tasksTimerManagerState = tasksTimerManager.state

    private val _showSnackBarEvent = MutableStateFlow<SnackBarEvent?>(null)

    val uiState =
        combine(
            _boards,
            _uiState,
            _tasksTimerManagerState,
            _showSnackBarEvent,
        ) { boards,
            uiState,
            tasksTimerMangerState,
            showSnackBarEvent
            ->
            val boardMenuEnabled = boards.isNotEmpty()
            uiState.copy(
                boardTitle = tasksTimerMangerState.board,
                boards = boards,
                boardMenuEnabled = boardMenuEnabled,
                timers = tasksTimerMangerState.timers,
                active = tasksTimerMangerState.active,
                currentTimerIndex = tasksTimerMangerState.currentTimerIndex,
                showSnackBarEvent = showSnackBarEvent
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
        if (tasksTimerManager.state.value.active != RUNSTATE.STOPPED) return
        viewModelScope.launch {
            // TODO: IS THERE A BETTER WAY THAN LOOP ON FIRST LAUNCH?
            // AVOIDS PROBLEM OF POPULATING ON FIRST LAUNCH
            var count = 0
            while (boardsRepo.getInitBoard() == null && count < 2) {
                delay(100)
                count++
            }
            val board = boardsRepo.getInitBoard()
            if (board != null) {
                // TODO: the timer manager should be the single source of board truth? why store id in vm?
                _uiState.update {
                    it.copy(
                        boardId = board.id,
                    )
                }
                tasksTimerManager.loadBoard(board.id)
            }
        }
    }

    // TODO: PUT THIS AS AN EVENT
    fun loadBoard() {
        if (tasksTimerManager.state.value.active != RUNSTATE.STOPPED) return
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

    // TODO: PUT IN EVENTS
    fun pauseTimer() {
        tasksTimerManager.stopTimer()
    }

    // TODO: PUT IN EVENTS
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
                        displayConfirmDialog = DeleteDialog.Board(uiState.value.boards[uiState.value.currentBoardIndex]),
                        displayBoardMenu = false
                    )
                }
            }

            HomeScreenEvent.RenameBoard -> {
                val boardToEdit = uiState.value.boards[uiState.value.currentBoardIndex]
                _uiState.update {
                    it.copy(
                        createBoard = CreateBoardDialog.NAME_BOARD,
                        boardType = CreateBoardType.UPDATE,
                        newBoardDetails = NewBoardDetails(
                            id = boardToEdit.id,
                            name = boardToEdit.name,
                            iconKey = boardToEdit.iconKey
                        ),
                    )
                }
                closeBoardMenu()
            }

            is HomeScreenEvent.DeleteTimer -> {
                deleteTimer(timer = event.timer)
                displaySnackBar("${event.timer.name} deleted")
            }

            is HomeScreenEvent.DialogConfirm -> {
                viewModelScope.launch {
                    uiState.value.displayConfirmDialog?.let { deleteDialog ->
                        // TODO: I DON'T THINK THIS IS NEEDED
                        when (deleteDialog) {
                            is DeleteDialog.Board -> {
                                deleteBoard(board = deleteDialog.board)
                                displaySnackBar("${deleteDialog.board.name} deleted")
                            }
                        }
                    }
                }
            }

            is HomeScreenEvent.DialogCancel -> {
                _uiState.update { it.copy(displayConfirmDialog = null) }
                closeBoardMenu()
            }

            is HomeScreenEvent.DisplayMenu -> {
                _uiState.update { it.copy(displayBoardMenu = event.displayMenu) }
            }

            is HomeScreenEvent.SelectBoard -> {
                tasksTimerManager.loadBoard(event.boardId)

                // TODO: the timer manager should be the single source of board truth? why store id in vm?
                _uiState.update {
                    it.copy(
                        currentBoardIndex = event.boardIndex,
                        boardId = event.boardId,
                        boardMenuEnabled = true
                    )
                }
            }

            is HomeScreenEvent.CreateNewBoard -> {
                _uiState.update {
                    it.copy(
                        createBoard = CreateBoardDialog.NAME_BOARD,
                        boardType = CreateBoardType.NEW
                    )
                }
            }

            is HomeScreenEvent.UpdateNewBoardName -> {
                _uiState.update {
                    it.copy(
                        newBoardDetails = it.newBoardDetails.copy(
                            name = event.name
                        )
                    )
                }
            }

            is HomeScreenEvent.NameNewBoard -> {
                _uiState.update {
                    it.copy(
                        createBoard = CreateBoardDialog.CHOOSE_ICON,
                    )
                }
            }

            is HomeScreenEvent.AssignIconNewBoard -> {
                _uiState.update {
                    it.copy(
                        newBoardDetails = it.newBoardDetails.copy(
                            iconKey = event.iconKey
                        )
                    )
                }
            }

            is HomeScreenEvent.AcceptNewBoard -> {
                viewModelScope.launch {
                    insertBoard(
                        BoardItem(
                            id = uiState.value.newBoardDetails.id,
                            name = uiState.value.newBoardDetails.name,
                            iconKey = uiState.value.newBoardDetails.iconKey
                        )
                    )
                    // If this is the first board created load into service/ui
                    if (uiState.value.boards.isEmpty()) {
                        if (getBoardsFlow().first().isNotEmpty()) {
                            loadBoard()
                        }
                    }
                    if (uiState.value.boardType == CreateBoardType.UPDATE) {
                        tasksTimerManager.loadBoard(uiState.value.newBoardDetails.id)
                    }
                    _uiState.update {
                        it.copy(
                            createBoard = null,
                            boardType = CreateBoardType.NEW,
                            newBoardDetails = NewBoardDetails()
                        )
                    }
                }
            }

            HomeScreenEvent.CancelCreateNewBoard -> {
                _uiState.update {
                    it.copy(
                        createBoard = null,
                        newBoardDetails = NewBoardDetails()
                    )
                }
            }

            HomeScreenEvent.ResetAllTimers -> {
                tasksTimerManager.resetAllTimers()
                closeBoardMenu()
            }

            HomeScreenEvent.UndoDelete -> {
                _showSnackBarEvent.update { null }
                // TODO: DELETE LOGIC COULD GO INTO A MANAGER CLASS?
                if (uiState.value.deletedTimer != null) {
                    viewModelScope.launch {
                        timersRepo.insertTimer(uiState.value.deletedTimer!!)
                        loadBoard()
                        _uiState.update {
                            it.copy(
                                deletedTimer = null,
                                fabVisible = true,
                            )
                        }
                    }
                    return
                }
                if (uiState.value.deletedBoard != null) {
                    viewModelScope.launch {
                        insertBoard.invoke(_uiState.value.deletedBoard!!)
                        _uiState.value.deletedTimers.forEach { timer ->
                            timersRepo.insertTimer(timer)
                        }
                        // TODO: WORKING HERE
                        println(_uiState.value.deletedBoard)
//                        println(boardsRepo.getAllBoardsFlow().first())
                        println(uiState.value.boards)
//                        loadBoard()
                        _uiState.update {
                            it.copy(
                                deletedBoard = null,
                                deletedTimers = emptyList(),
                                fabVisible = true,
                            )
                        }
                    }
                    return
                }
            }

            HomeScreenEvent.ClearDeleteItem -> {
                _uiState.update {
                    it.copy(
                        deletedTimer = null,
                        deletedBoard = null,
                        deletedTimers = emptyList(),
                        fabVisible = true
                    )
                }
                _showSnackBarEvent.update { null }
            }
        }
    }

    private fun closeBoardMenu() {
        _uiState.update {
            it.copy(
                displayBoardMenu = false
            )
        }
    }

    private fun deleteTimer(timer: TimerItem) = viewModelScope.launch {
        deleteTimer.invoke(timer)

        loadBoard()

        _uiState.update {
            it.copy(
                displayConfirmDialog = null,
                displayBoardMenu = false,
                deletedTimer = timer
            )
        }
    }


    private fun deleteBoard(board: BoardItem) =
        viewModelScope.launch {

            // TODO: SHOULD BE USE CASE
            val deletedTimers = timersRepo.getTimersFlow(board.id).first()


            deleteBoard.invoke(board)
            val boardIndexToLoad = determineBoardIndexToLoad(
                boardCount = _boards.first().size,
                deletedBoardIndex = uiState.value.currentBoardIndex
            )
            // TODO: THIS COULD BE CLEANED UP BECAUSE ALL THE CODE IS REPEATED BELOW
            if (boardIndexToLoad != null) {
                _uiState.update {
                    it.copy(
                        currentBoardIndex = boardIndexToLoad,
                        displayConfirmDialog = null,
                        displayBoardMenu = false,
                        deletedBoard = board,
                        deletedTimers = deletedTimers
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
                    displayBoardMenu = false,
                    deletedBoard = board,
                    deletedTimers = deletedTimers
                )
            }
            tasksTimerManager.unloadBoard()
        }

    private fun displaySnackBar(message: String) {
        _showSnackBarEvent.update {
            SnackBarEvent(
                timeMark = TimeSource.Monotonic.markNow(),
                message = message
            )
        }
        _uiState.update { it.copy(fabVisible = false) }
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


