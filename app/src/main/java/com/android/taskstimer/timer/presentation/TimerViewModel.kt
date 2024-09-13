package com.android.taskstimer.timer.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.timer.domain.use_case.AddTimer
import com.android.taskstimer.timer.domain.use_case.GetTimerStream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class ScreenTitle(val title: String){
    ADD_TIMER("Add timer"),
    EDIT_TIMER("Edit timer")
}

data class TimerUiState(
    val title: String = ScreenTitle.ADD_TIMER.title,
    val timer: TimerItem = TimerItem(),
    val isEntryValid: Boolean = false
)

@HiltViewModel
class TimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addTimer: AddTimer,
    private val getTimerStream: GetTimerStream
) : ViewModel() {

    private val boardId: Int = checkNotNull(savedStateHandle[TimerDestination.boardIdArg])
    private val timerId: Int = checkNotNull(savedStateHandle[TimerDestination.timerIdArg])

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()



    init {
        if (timerId != 0){
            _uiState.update { it.copy(title = ScreenTitle.EDIT_TIMER.title) }
            viewModelScope.launch {
                val timer = getTimerStream.invoke(timerId).first()
                _uiState.update {
                    it.copy(
                        timer = timer,
                        isEntryValid = true
                    )
                }
            }
        }
    }

    private fun addTimer(timer: TimerItem) {
        viewModelScope.launch {
            addTimer.invoke(timer)
        }
    }

    fun onEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.AddTimer -> {
                addTimer(
                    TimerItem(
                        id = timerId,
                        boardId = boardId,
                        name = uiState.value.timer.name,
                        presetTime = "125",
                        remainingTime = "125"
                    )
                )
            }

            is TimerEvent.UpdateName -> {
                _uiState.update {
                    val updatedTimer = TimerItem(
                        name = event.name
                    )
                    it.copy(
                        timer = updatedTimer,
                        isEntryValid = validateInput(updatedTimer)
                    )
                }
            }

        }
    }

    private fun validateInput(timer: TimerItem = _uiState.value.timer): Boolean {
        return with(timer) {
            name.isNotBlank() // && name.isNotBlank() && presetTime.isNotBlank()
        }
    }
}
