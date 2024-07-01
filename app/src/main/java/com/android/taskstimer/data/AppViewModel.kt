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
    val coroutineId: Job? = null,
    val currentTimer: Int = 0,
    val timers: List<Timer> = listOf(
        Timer(
            id = 0,
            name = "Timer 1",
            remainingTime = "5",
            time = "5"
        ),
        Timer(
            id = 1,
            name = "Timer 2",
            remainingTime = "5",
            time = "5"
        ),
    )
)

data class Timer(
    val id: Int,
    val name: String,
    val remainingTime: String,
    val time: String
)

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TasksTimer())
    val uiState = _uiState.asStateFlow()


    private fun startTimer() {
        val id: Job = viewModelScope.launch {
            while (true) {
                delay(100)
                updateState()

                if (_uiState.value.currentTimer >= _uiState.value.timers.size) {
                    _uiState.value.coroutineId?.cancel()
                }
            }
        }

        _uiState.update { currentState ->
            currentState.copy(
                coroutineId = id
            )
        }
    }

    fun onEvent(event: TasksTimerEvent) {
        when (event) {
            is TasksTimerEvent.StartTimer -> {
                startTimer()
            }
        }
    }


    private fun updateState() {
        _uiState.update { currentState ->
            val currentTimer: Int = _uiState.value.currentTimer
            val currentTimerValue = _uiState.value.timers[currentTimer].remainingTime.toInt()

            val updatedTimerValue: Int =
                if (currentTimerValue - 1 < 0) currentTimerValue else currentTimerValue - 1
            val updatedCurrentTimer = if (updatedTimerValue == 0) currentTimer + 1 else currentTimer

            val updatedTimer: Timer = _uiState.value.timers[currentTimer].copy(
                remainingTime = updatedTimerValue.toString()
            )

            val updatedTimers: List<Timer> = _uiState.value.timers.mapIndexed() { index: Int, timer: Timer ->
                if (index == currentTimer) updatedTimer else timer
            }

            currentState.copy(
                timers = updatedTimers,
                currentTimer = updatedCurrentTimer
            )
        }
    }

}