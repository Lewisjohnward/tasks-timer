package com.android.taskstimer.edit_timer.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.edit_timer.domain.use_case.AddTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TimerAddUiState(
    val timer: TimerItem = TimerItem(),
    val isEntryValid: Boolean = false
)

@HiltViewModel
class TimerAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addTimer: AddTimer
) : ViewModel() {
    private val boardId: Int = checkNotNull(savedStateHandle[TimerAddDestination.boardIdArg])
    private val _uiState = MutableStateFlow(TimerAddUiState())
    val uiState = _uiState.asStateFlow()


    private fun addTimer(timer: TimerItem) {
        viewModelScope.launch {
            addTimer.invoke(timer)
        }
    }

    fun onEvent(event: TimerAddEvent) {
        when (event) {
            is TimerAddEvent.AddTimer -> {
                addTimer(
                    TimerItem(
                        boardId = boardId,
                        name = uiState.value.timer.name,
                        presetTime = "125",
                        remainingTime = "125"
                    )
                )

            }

            is TimerAddEvent.UpdateName -> {
                _uiState.update {
                    val updatedTimer = TimerItem(
                        name = event.name
                    )
                    it.copy(
                        timer = updatedTimer,
                        isEntryValid = validateInput(updatedTimer)

                    )
                }
                println(uiState.value.timer)
                println(validateInput(uiState.value.timer))
            }

        }
    }

    private fun validateInput(timer: TimerItem = _uiState.value.timer): Boolean {
        return with(timer) {
            name.isNotBlank() // && name.isNotBlank() && presetTime.isNotBlank()
        }
    }
}
