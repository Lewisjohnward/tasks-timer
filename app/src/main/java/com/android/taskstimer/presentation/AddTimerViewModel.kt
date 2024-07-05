package com.android.taskstimer.presentation

import androidx.lifecycle.ViewModel
import com.android.taskstimer.data.TimersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow




class AddTimerViewModel(
    private val timersRepository: TimersRepository
): ViewModel(){
    private val _uiState = MutableStateFlow("hello")
    val uiState = _uiState.asStateFlow()

    fun addTimer(){
        println("add timer")
    }
}
