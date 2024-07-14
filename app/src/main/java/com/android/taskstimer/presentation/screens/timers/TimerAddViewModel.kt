package com.android.taskstimer.presentation.screens.timers

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.data.timer.Timer
import com.android.taskstimer.data.timer.TimersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class TimerAddViewModel(
    savedStateHandle: SavedStateHandle,
    private val timersRepository: TimersRepository
) : ViewModel() {
    private val boardId: Int = checkNotNull(savedStateHandle[TimerAddDestination.boardIdArg])
    private val _uiState = MutableStateFlow("hello")
    val uiState = _uiState.asStateFlow()


    fun addTimer(timer: Timer) {
        viewModelScope.launch {
            timersRepository.insertTimer(timer)
        }
        println("add timer")
        println(boardId)
    }

    fun onEvent(event: TimerAddEvent) {
        when (event) {
            is TimerAddEvent.AddTimer -> {
                val dummyData = Timer(
                    boardId = boardId,
                    name = "My banging new timer",
                    presetTime = "143",
                )
                addTimer(dummyData)
//                addTimer(event.timer)

            }
        }
    }

}
