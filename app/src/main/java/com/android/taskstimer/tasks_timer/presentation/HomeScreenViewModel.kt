package com.android.taskstimer.tasks_timer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.BoardsWithTimersItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimers
import com.android.taskstimer.tasks_timer.domain.use_case.InsertBoard
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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


    val currentBoardName: String = "",
    val currentBoardId: Int = 0,
    val currentBoard: List<TimerItem> = listOf(),

    val currentBoardIndex: Int = 0,
)


fun TimerItem.formatTime(): String {
    val seconds = remainingTime.toInt()

    if (seconds == 0) {
        return "00:00"
    } else if (seconds < 60) {
        return if (seconds < 10) ("00:0${seconds}")
        else ("00:${seconds}")
    } else {
        val remainingSeconds = seconds % 60
        val remainingMinutes = (seconds - remainingSeconds) / 60

        val secondsString =
            if (remainingSeconds < 10) ("0$remainingSeconds") else remainingSeconds.toString()
        val minutesString =
            if (remainingMinutes < 10) ("0$remainingMinutes") else remainingMinutes.toString()

        return ("$minutesString:$secondsString")
    }
}

fun TimerItem.resetTimer(): TimerItem {
    return this.copy(remainingTime = presetTime)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateTimer: UpdateTimer,
    private val insertBoard: InsertBoard,
    private val getTimers: GetTimers
) : ViewModel() {
    //
//    private val _boardsWithTimers: Flow<List<BoardsWithTimersItem>> =
//        getAllBoardsWithTimers.invoke()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun loadTimers(){
        viewModelScope.launch {
            val timers = getTimers.invoke(1)
            println(timers)
        }
    }





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

    private fun addBoard(name: String) {
        viewModelScope.launch {
            insertBoard.invoke(BoardItem(name = name))
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ToggleTimer -> {
                if (uiState.value.coroutineId == null) startTimer() else stopTimer()
            }

            is HomeScreenEvent.SelectBoard -> {
                _uiState.update {
                    it.copy(
//                        currentBoardName = uiState.value.boardsWithTimers[event.boardIndex].board.name,
//                        currentBoardIndex = event.boardIndex,
//                        currentBoard = uiState.value.boardsWithTimers[event.boardIndex].timers
                    )
                }
            }

            is HomeScreenEvent.EditBoards -> {
                _uiState.update { it.copy(editBoards = event.edit) }
            }

            is HomeScreenEvent.CreateBoard -> {
                addBoard(event.name)
            }

        }
    }


    private fun startTimer() {
        if (uiState.value.running) stopTimer()
        else _uiState.update { it.copy(coroutineId = launchTimer()) }
    }

    private fun launchTimer(): Job {
        return viewModelScope.launch {
            while (true) {
                delay(10)
                decrementTimer()
                if (uiState.value.currentTimerIndex >= uiState.value.currentBoard.size) {
                    stopTimer()
                    resetTimer()
                }
            }
        }
    }

    private fun decrementTimer() {
        val currentTimerIndex = uiState.value.currentTimerIndex
        val currentTimer = uiState.value.currentBoard[uiState.value.currentTimerIndex]
        val currentTimerValue = currentTimer.remainingTime.toInt()
        val updatedRemainingTime: Int =
            if (currentTimerValue - 1 < 0) currentTimerValue else currentTimerValue - 1

        val updatedCurrentTimerIndex: Int =
            if (updatedRemainingTime == 0) currentTimerIndex + 1 else currentTimerIndex

//        if(updatedRemainingTime == 0) {
//            stopTimer()
//            playAlarm(callback = {startTimer()})
//        }

        _uiState.update { it.copy(currentTimerIndex = updatedCurrentTimerIndex) }

        viewModelScope.launch {
            updateTimer.invoke(currentTimer.copy(remainingTime = updatedRemainingTime.toString()))
        }
    }

    private fun stopTimer() {
        uiState.value.coroutineId?.cancel()
        _uiState.update {
            it.copy(
                coroutineId = null,
                running = false
            )
        }
    }

    private fun resetTimer() {
        val resetTimers: List<TimerItem> =
            uiState.value.currentBoard.map { timer -> timer.copy(remainingTime = timer.presetTime) }
        resetTimers.forEach { timer: TimerItem ->
            viewModelScope.launch {
                updateTimer.invoke(timer.resetTimer())
            }
        }
        _uiState.update {
            it.copy(
                running = false,
                coroutineId = null,
                currentTimerIndex = 0,
            )
        }
    }


}
