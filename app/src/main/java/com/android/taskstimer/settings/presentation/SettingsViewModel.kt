package com.android.taskstimer.settings.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class SettingsUiState(
    val ignoreSilentMode: Boolean = true,
    val timeIntervalBetweenTimers: Int = 5,
    val useMediaVolumeHeadphones: Boolean = true
)

//@HiltViewModel
class SettingsViewModel: ViewModel() {

    private var _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()


    fun onEvent(event: SettingsEvent){
        when (event){
            is SettingsEvent.SetIgnoreSilentMode -> _uiState.update {it.copy(ignoreSilentMode = event.active) }
            is SettingsEvent.SetTimeInterval -> _uiState.update { it.copy(timeIntervalBetweenTimers = event.time) }
            is SettingsEvent.SetUseMediaVolumeHeadphones -> _uiState.update { it.copy(useMediaVolumeHeadphones = event.active) }
        }
    }

}