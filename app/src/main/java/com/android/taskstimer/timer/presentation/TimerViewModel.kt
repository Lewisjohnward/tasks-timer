package com.android.taskstimer.timer.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.timer.TimerStateManager
import com.android.taskstimer.timer.domain.use_case.AddTimer
import com.android.taskstimer.timer.domain.use_case.GetTimerStream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class ScreenTitle(val title: String) {
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
    private val getTimerStream: GetTimerStream,
    private val timerStateManager: TimerStateManager
) : ViewModel() {


    // TODO: TURN MAP TOSTRING() HERE
    val timerState = timerStateManager.state.asStateFlow()

    private val boardId: Int = checkNotNull(savedStateHandle[TimerDestination.boardIdArg])
    private val timerId: Int = checkNotNull(savedStateHandle[TimerDestination.timerIdArg])

    private val navigationChannel = Channel<NavigationEvent>()
    val navigationChannelFlow = navigationChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()


    init {
        timerStateManager.reset()
        if (timerId != 0) {
            _uiState.update { it.copy(title = ScreenTitle.EDIT_TIMER.title) }
            viewModelScope.launch {
                val timer = getTimerStream.invoke(timerId).first()
                _uiState.update {
                    it.copy(
                        timer = timer,
                        isEntryValid = true
                    )
                }
                timerStateManager.setTime(timer.presetTime.toInt())
            }
        }
    }


    fun onEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.AddTimer ->  addTimer()
            is TimerEvent.UpdateTimerName -> updateTimer(event.name)
            is TimerEvent.ChangeFocus -> timerStateManager.changeFocus(event.side)
            TimerEvent.Increment -> timerStateManager.increment()
            TimerEvent.Decrement -> timerStateManager.decrement()
            is TimerEvent.InputValue -> timerStateManager.inputValue(event.value)
            TimerEvent.Delete -> timerStateManager.delete()
        }
        updateTimer(_uiState.value.timer.name)
    }

    private fun updateTimer(name: String) {
        val time: String = timerStateManager.getInputValueAsSeconds()
        _uiState.update {
            val updatedTimer = TimerItem(
                name = name,
                presetTime = time,
                remainingTime = time
            )
            it.copy(
                timer = updatedTimer,
                isEntryValid = validateInput(updatedTimer)
            )
        }
    }

    private fun addTimer() {
        val time: String = timerStateManager.getInputValueAsSeconds()
        val newTimerItem = TimerItem(
            id = timerId,
            boardId = boardId,
            name = uiState.value.timer.name,
            presetTime = time,
            remainingTime = time,
            lastEndedAt = uiState.value.timer.lastEndedAt
        )
        viewModelScope.launch {
            addTimer.invoke(newTimerItem)
            navigationChannel.send(NavigationEvent.NavigateBack)
        }
    }

    private fun validateInput(timer: TimerItem = _uiState.value.timer): Boolean {
        var validTime = false
        if (timer.presetTime != ""){
            validTime = timer.presetTime.toInt() != 0
        }
        return timer.name.isNotBlank() && validTime
    }
}


sealed interface NavigationEvent {
    data object NavigateBack : NavigationEvent
}