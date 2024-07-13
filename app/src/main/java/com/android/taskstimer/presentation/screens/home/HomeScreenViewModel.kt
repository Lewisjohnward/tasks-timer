package com.android.taskstimer.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.data.board.Board
import com.android.taskstimer.data.board.BoardsRepository
import com.android.taskstimer.data.board.BoardsWithTimers
import com.android.taskstimer.data.timer.Timer
import com.android.taskstimer.data.timer.TimersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class UiState(
    val running: Boolean = false,
    val finished: Boolean = false,
    val coroutineId: Job? = null,
    val currentTimerIndex: Int = 0,


    val currentBoard: Int = 0,
    val boardsWithTimers: List<BoardsWithTimers> = listOf()
)


fun Timer.formatTime(): String {
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

fun Timer.resetTimer(): Timer {
    return this.copy(remainingTime = presetTime)
}

class HomeViewModel(
    private val timersRepository: TimersRepository,
    private val boardsRepository: BoardsRepository,
) : ViewModel() {
    //
//    private val _timers: Flow<List<Timer>> = timersRepository.getAllTimersStream()
//    private val _boards: Flow<List<Board>> = boardsRepository.getAllBoardsStream()
    private val _boardsWithTimers: Flow<List<BoardsWithTimers>> =
        boardsRepository.getBoardsWithTimers()
    private val _uiState = MutableStateFlow(UiState())


    val uiState =
        combine(_boardsWithTimers, _uiState) {boardsWithTimers, uiState->
            uiState.copy(
                boardsWithTimers = boardsWithTimers
            )
        }
//    val uiState = _boardsWithTimers.map {
//        UiState(boardsWithTimers = it)
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), UiState())


    fun selectBoard(boardIndex: Int) {
        println(boardIndex)


    }
//    val uiState = _boardsWithTimers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), listOf())
//
//    private val _uiState = MutableStateFlow(UiState())
//
//
//    val uiState =
//        combine(_timers, _uiState, _boards) { timers, uiState, boards ->
//            uiState.copy(
//                timers = timers,
//                boards = boards
//
//            )
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), UiState())
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
//                if (uiState.value.currentTimerIndex >= uiState.value.timers.size) {
//                    stopTimer()
//                    resetTimer()
//                }
//            }
//        }
//    }
//
//    private fun decrementTimer() {
//        val currentTimerIndex = uiState.value.currentTimerIndex
//        val currentTimer = uiState.value.timers[uiState.value.currentTimerIndex]
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
////        }
//
//        _uiState.update { it.copy(currentTimerIndex = updatedCurrentTimerIndex) }
//
//        viewModelScope.launch {
//            timersRepository.updateTimer(currentTimer.copy(remainingTime = updatedRemainingTime.toString()))
//        }
//    }

    init {
//        addTimer()
//        addBoard()
    }

    private fun addBoard() {
        viewModelScope.launch {
            boardsRepository.insertBoard(Board(name = "Basic productivity"))
        }
        viewModelScope.launch {
            boardsRepository.insertBoard(Board(name = "House work"))
        }
    }

    fun addTimer() {
        viewModelScope.launch {
            timersRepository.insertTimer(
                Timer(
                    name = "Learn about services",
                    board = "Basic productivity",
                    presetTime = "105",
                )
            )
        }
        viewModelScope.launch {
            timersRepository.insertTimer(
                Timer(
                    name = "Develop awesome app",
                    board = "Basic productivity",
                    presetTime = "185",
                )
            )
        }
        viewModelScope.launch {
            timersRepository.insertTimer(
                Timer(
                    name = "Reflect on day",
                    board = "Basic productivity",
                    presetTime = "345",
                )
            )
        }
        viewModelScope.launch {
            timersRepository.insertTimer(
                Timer(
                    name = "Clean the floor",
                    board = "House work",
                    presetTime = "105",
                )
            )
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ToggleTimer -> {
                println("toggle")
//                if (_uiState.value.coroutineId == null) startTimer() else stopTimer()
            }

            is HomeScreenEvent.SelectBoard -> {
                _uiState.update { it.copy(currentBoard = event.boardIndex) }
            }
        }
    }
//
//    private fun stopTimer() {
//        _uiState.value.coroutineId?.cancel()
//        _uiState.update {
//            it.copy(
//                coroutineId = null,
//                running = false
//            )
//        }
//    }
//
//    private fun resetTimer() {
//        val resetTimers: List<Timer> =
//            uiState.value.timers.map { timer -> timer.copy(remainingTime = timer.presetTime) }
//        resetTimers.forEach { timer: Timer ->
//            viewModelScope.launch {
//                timersRepository.updateTimer(timer.resetTimer())
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


}
