package com.android.taskstimer.edit_timer.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TimerAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val timersRepository: TimersRepository
) : ViewModel() {
    private val boardId: Int = checkNotNull(savedStateHandle[TimerAddDestination.boardIdArg])
    private val _uiState = MutableStateFlow("Put uiState here")
    val uiState = _uiState.asStateFlow()


    private fun addTimer(timer: TimerItem) {
        viewModelScope.launch {
            timersRepository.insertTimer(timer)
        }
    }

    fun onEvent(event: TimerAddEvent) {
        when (event) {
            is TimerAddEvent.AddTimer -> {
                val dummyData = TimerItem(
                    boardId = boardId,
                    name = "My banging new timer",
                    presetTime = "143",
                )
                addTimer(dummyData)

            }
        }
    }

}
