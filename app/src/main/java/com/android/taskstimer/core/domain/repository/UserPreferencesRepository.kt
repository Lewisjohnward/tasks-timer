package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.data.repository.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val userPreferencesFlow: Flow<UserPreferences>

    suspend fun updateIgnoreSilentMode(ignore: Boolean)

    suspend fun updateTimerInterval(interval: Int)

    suspend fun updateUseMediaVolumeHeadphones(useMediaVolume: Boolean)

}