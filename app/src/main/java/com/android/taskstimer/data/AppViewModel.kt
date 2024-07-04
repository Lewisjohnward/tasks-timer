package com.android.taskstimer.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class TasksTimer(
    val running: Boolean = false,
    val finished: Boolean = false,
    val coroutineId: Job? = null,
    val currentTimer: Int = 0,
    val timers: List<Timer> = listOf(
        Timer(
            id = 0,
            name = "Do the dishes",
            remainingTime = "65",
        ),
        Timer(
            id = 1,
            name = "Clean the floor",
            remainingTime = "140",
        ),
    )
)

data class Timer(
    val id: Int,
    val name: String,
    val remainingTime: String,
    val presetTime: String = remainingTime
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

class AppViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(TasksTimer())
    val uiState = _uiState.asStateFlow()


    private fun startTimer() {
        _uiState.update { it.copy(running = true) }
        val id: Job = viewModelScope.launch {
            while (true) {
                delay(10)
                decrementTimer()

                if (_uiState.value.currentTimer >= _uiState.value.timers.size) {
                    stopTimer()
                    resetTimer()
                }
            }
        }
        _uiState.update { it.copy(coroutineId = id) }
    }


    fun onEvent(event: TasksTimerEvent) {
        when (event) {
            is TasksTimerEvent.ToggleTimer -> {
                if (_uiState.value.coroutineId == null) startTimer() else stopTimer()
            }
        }
    }

    private fun stopTimer() {
        _uiState.value.coroutineId?.cancel()
        _uiState.update {
            it.copy(
                coroutineId = null,
                running = false
            )
        }
    }

    private fun resetTimer() {
        _uiState.update { currentState ->
            val updatedState =
                currentState.timers.map { timer ->
                    timer.copy(
                        remainingTime = timer.presetTime,
                    )
                }
            currentState.copy(
                running = false,
                coroutineId = null,
                currentTimer = 0,
                timers = updatedState
            )
        }

    }


    private fun decrementTimer() {
        _uiState.update { currentState ->
            val currentTimer: Int = _uiState.value.currentTimer
            val currentTimerValue = _uiState.value.timers[currentTimer].remainingTime.toInt()

            val updatedTimerValue: Int =
                if (currentTimerValue - 1 < 0) currentTimerValue else currentTimerValue - 1
            val updatedCurrentTimer = if (updatedTimerValue == 0) currentTimer + 1 else currentTimer

            val updatedTimer: Timer = _uiState.value.timers[currentTimer].copy(
                remainingTime = updatedTimerValue.toString(),
            )

            val updatedTimers: List<Timer> =
                _uiState.value.timers.mapIndexed() { index: Int, timer: Timer ->
                    if (index == currentTimer) updatedTimer else timer
                }

            currentState.copy(
                timers = updatedTimers,
                currentTimer = updatedCurrentTimer
            )
        }
    }


}