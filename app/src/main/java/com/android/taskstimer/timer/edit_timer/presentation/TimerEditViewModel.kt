package com.android.taskstimer.timer.edit_timer.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.timer.edit_timer.domain.use_case.GetTimerStream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TimerEditUiState(
    val timer: TimerItem = TimerItem(),
    val isEntryValid: Boolean = false
)

@HiltViewModel
class TimerEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTimerStream: GetTimerStream
) : ViewModel() {
    private val timerId: Int = checkNotNull(savedStateHandle[TimerEditDestination.timerIdArg])
    private val _uiState = MutableStateFlow(TimerEditUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    timer = getTimerStream.invoke(timerId).filterNotNull().first()
                )
            }
        }
    }


//    private fun addTimer(timer: TimerItem) {
//        viewModelScope.launch {
//            addTimer.invoke(timer)
//        }
//    }

    fun onEvent(event: TimerEditScreenEvent) {
        when (event) {
            is TimerEditScreenEvent.UpdateTimer -> {}
            is TimerEditScreenEvent.UpdateName -> {}
            is TimerEditScreenEvent.UpdateTimerTime -> {}

        }
    }
//    private fun validateInput(timer: TimerItem = _uiState.value.timer): Boolean {
//        return with(timer) {
//            name.isNotBlank() // && name.isNotBlank() && presetTime.isNotBlank()
//        }
//    }
}
