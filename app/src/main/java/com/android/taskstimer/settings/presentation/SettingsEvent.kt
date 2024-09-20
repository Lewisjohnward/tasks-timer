package com.android.taskstimer.settings.presentation

sealed interface SettingsEvent {
    data class SetIgnoreSilentMode(val active: Boolean): SettingsEvent
    data class SetTimeInterval(val time: Int): SettingsEvent
    data class SetUseMediaVolumeHeadphones(val active: Boolean): SettingsEvent
}