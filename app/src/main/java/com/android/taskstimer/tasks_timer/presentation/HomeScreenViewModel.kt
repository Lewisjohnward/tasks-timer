package com.android.taskstimer.tasks_timer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.tasks_timer.domain.use_case.DeleteBoard
import com.android.taskstimer.tasks_timer.domain.use_case.GetBoardsFlow
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimersFlow
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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
    val selectedBoard: BoardItem = BoardItem(name = "untitled"),

//    val currentBoardIndex: Int = 0,
//    val currentBoardName: String = "",
//    val currentBoardId: Int = 0,
)


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateTimer: UpdateTimer,
    private val insertBoard: InsertBoard,
    private val getTimersFlow: GetTimersFlow,
    private val getBoardsFlow: GetBoardsFlow,
    private val deleteBoard: DeleteBoard
) : ViewModel() {

    private val _boards = getBoardsFlow()
    private val _currentBoard = MutableStateFlow(BoardItem(id = 1, name = "untitled"))
    private val _timers = _currentBoard.flatMapLatest {
        board ->
        getTimersFlow.invoke(boardId = board.id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    private val _uiState = MutableStateFlow(UiState())

    val uiState =
        combine(_timers, _uiState, _boards, _currentBoard) { timers, uiState, boards, currentBoard ->
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


//    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
//    private val _contacts = _sortType
//        .flatMapLatest { sortType ->
//            when(sortType) {
//                SortType.FIRST_NAME -> dao.getContactsOrderedByFirstName()
//                SortType.LAST_NAME -> dao.getContactsOrderedByLastName()
//                SortType.PHONE_NUMBER -> dao.getContactsOrderedByPhoneNumber()
//            }
//        }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//    private val _state = MutableStateFlow(ContactState())
//    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
//        state.copy(
//            contacts = contacts,
//            sortType = sortType
//        )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())



//        .combine()
//        .stateIn
//        .filterNotNull()


    // Maybe call get tasks?
//    fun loadBoards() {
//        viewModelScope.launch {
//            val boards = getBoards.invoke()
//            _uiState.update { it.copy(boards = boards) }
//            if (boards.isNotEmpty()) {
//                val timers = getTimers(boardId = boards[0].id)
//                _uiState.update {
//                    it.copy(
//                        timers = timers,
//                        selectedBoard = boards[0]
//                    )
//                }
//            } else {
//                _uiState.update { it.copy(
//                    selectedBoard = BoardItem(name = "Untitled"),
//                    timers = listOf()
//                ) }
//            }
//        }
//    }

    fun loadTimers() {
        println("hello")
//        viewModelScope.launch {
//            val timers = getTimers.invoke(1)
//            println(timers)
//        }
    }

//    fun loadBoards(){
//        viewModelScope.launch {
//            val boards =
//            println(timers)
//        }
//    }


    //    val uiState: StateFlow<UiState> =
//        combine(_boardsWithTimers, _uiState) { boardsWithTimers, uiState ->
//            uiState.copy(
//                boardsWithTimers = boardsWithTimers,
//
//                // TODO: Clean this up into it's own data class let's not pollute the uiState
//                // TODO: Sort out display if user has no boards/timers
//                currentBoard = if (boardsWithTimers.isNotEmpty()) boardsWithTimers[uiState.currentBoardIndex].timers else listOf(),
//                currentBoardName = if (boardsWithTimers.isNotEmpty()) boardsWithTimers[uiState.currentBoardIndex].board.name else "",
//                currentBoardId = if (boardsWithTimers.isNotEmpty()) boardsWithTimers[uiState.currentBoardIndex].board.id else 0
//
//            )
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = UiState()
//        )
//
//
//
//    private fun addBoard(name: String) {
//        viewModelScope.launch {
//            insertBoard.invoke(BoardItem(name = name))
//        }
//    }
//
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.ToggleTimer -> {
//                if (uiState.value.coroutineId == null) startTimer() else stopTimer()
            }

            is HomeScreenEvent.SelectBoard -> {
                _currentBoard.value = event.board
            }

            is HomeScreenEvent.EditBoards -> {
                _uiState.update { it.copy(editBoards = event.edit) }
            }

            is HomeScreenEvent.CreateBoard -> {
                viewModelScope.launch {
                    insertBoard(BoardItem(name = event.name))
//                    loadBoards()
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
                    deleteBoard.invoke(_uiState.value.selectedBoard)
//                    loadBoards()
                }
                _uiState.update { it.copy(displayDialog = null) }
            }

            HomeScreenEvent.DialogCancel -> {
                _uiState.update { it.copy(displayDialog = null) }
            }
        }
    }
//
//
//    private fun startTimer() {
//        if (uiState.value.running) stopTimer()
//        else _uiState.update { it.copy(coroutineId = launchTimer()) }
//    }
//
//    private fun launchTimer(): Job {
//        return viewModelScope.launch {
//            while (true) {
//                delay(10)
//                decrementTimer()
//                if (uiState.value.currentTimerIndex >= uiState.value.currentBoard.size) {
//                    stopTimer()
//                    resetTimer()
//                }
//            }
//        }
//    }
//
//    private fun decrementTimer() {
//        val currentTimerIndex = uiState.value.currentTimerIndex
//        val currentTimer = uiState.value.currentBoard[uiState.value.currentTimerIndex]
//        val currentTimerValue = currentTimer.remainingTime.toInt()
//        val updatedRemainingTime: Int =
//            if (currentTimerValue - 1 < 0) currentTimerValue else currentTimerValue - 1
//
//        val updatedCurrentTimerIndex: Int =
//            if (updatedRemainingTime == 0) currentTimerIndex + 1 else currentTimerIndex
//
////        if(updatedRemainingTime == 0) {
////            stopTimer()
////            playAlarm(callback = {startTimer()})
////        er.invoke(currentTimer.copy(remainingTime = updatedRemainingTime.toString()))
//        }
//    }
//
//    private fun stopTimer() {
//        uiState.value.coroutineId?.cancel()
//        _uiState.update {
//            it.copy(
//                coroutineId = null,
//                running = false
//            )
//        }
//    }
//
//    private fun resetTimer() {
//        val resetTimers: List<TimerItem> =
//            uiState.value.currentBoard.map { timer -> timer.copy(remainingTime = timer.presetTime) }
//        resetTimers.forEach { timer: TimerItem ->
//            viewModelScope.launch {
//                updateTimer.invoke(timer.resetTimer())
//            }
//        }
//        _uiState.update {
//            it.copy(
//                running = false,
//                coroutineId = null,
//                currentTimerIndex = 0,
//            )
//        }
//    }
//}
//
//        _uiState.update { it.copy(currentTimerIndex = updatedCurrentTimerIndex) }
//
//        viewModelScope.launch {
//            updateTimer.invoke(currentTimer.copy(remainingTime = updatedRemainingTime.toString()))
//        }
//    }
//
//    private fun stopTimer() {
//        uiState.value.coroutineId?.cancel()
//        _uiState.update {
//            it.copy(
//                coroutineId = null,
//                running = false
//            )
//        }
//    }
//
//    private fun resetTimer() {
//        val resetTimers: List<TimerItem> =
//            uiState.value.currentBoard.map { timer -> timer.copy(remainingTime = timer.presetTime) }
//        resetTimers.forEach { timer: TimerItem ->
//            viewModelScope.launch {
//                updateTimer.invoke(timer.resetTimer())
//            }
//        }
//        _uiState.update {
//            it.copy(
//                running = false,
//                coroutineId = null,
//                currentTimerIndex = 0,
//            )
//        }
//    }
//

}
