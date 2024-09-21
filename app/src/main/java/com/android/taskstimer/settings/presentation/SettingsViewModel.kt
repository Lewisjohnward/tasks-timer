package com.android.taskstimer.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taskstimer.core.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SettingsUiState(
    val ignoreSilentMode: Boolean = true,
    val timeIntervalBetweenTimers: Int = 5,
    val useMediaVolumeHeadphones: Boolean = true
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
): ViewModel() {

    val uiState = userPreferencesRepository.userPreferencesFlow.map{
        SettingsUiState(
            ignoreSilentMode = it.ignoreSilentMode,
            timeIntervalBetweenTimers = it.timeIntervalBetweenTimers,
            useMediaVolumeHeadphones = it.useMediaVolumeHeadphones
        )
    }

    fun onEvent(event: SettingsEvent){
        when (event){
            is SettingsEvent.SetIgnoreSilentMode -> {
                viewModelScope.launch {
                    userPreferencesRepository.updateIgnoreSilentMode(event.active)
                }
            }
            is SettingsEvent.SetTimeInterval -> {
                viewModelScope.launch { userPreferencesRepository.updateTimerInterval(event.time) }
            }
            is SettingsEvent.SetUseMediaVolumeHeadphones -> {
                viewModelScope.launch {
                    userPreferencesRepository.updateUseMediaVolumeHeadphones(event.active)
                }
            }
        }
    }

}