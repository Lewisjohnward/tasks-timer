package com.android.taskstimer.presentation.screens.timers

import androidx.lifecycle.ViewModel
import com.android.taskstimer.data.timer.TimersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow




class TimerAddViewModel(
    private val timersRepository: TimersRepository
): ViewModel(){
    private val _uiState = MutableStateFlow("hello")
    val uiState = _uiState.asStateFlow()

    fun addTimer(){
        println("add timer")
    }
}
