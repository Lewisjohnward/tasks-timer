package com.android.taskstimer.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.android.taskstimer.core.domain.repository.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.preferencesDataStore: DataStore<Preferences>
    by preferencesDataStore("settings")


data class UserPreferences(
    val ignoreSilentMode: Boolean = true,
    val timeIntervalBetweenTimers: Int = 5,
    val useMediaVolumeHeadphones: Boolean = true
)


@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext context: Context
): UserPreferencesRepository {
    private val preferencesDataStore = context.preferencesDataStore

    private object PreferencesKeys {
        val IGNORE_SILENT_MODE_KEY = booleanPreferencesKey("ignore_silent_mode")
        val TIME_INTERVAL_KEY = intPreferencesKey("timer_interval")
        val USE_MEDIA_VOLUME_HEADPHONES_KEY = booleanPreferencesKey("use_media_volume_headphones")
    }

    override val userPreferencesFlow: Flow<UserPreferences> = preferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val ignoreSilentMode =
                preferences[PreferencesKeys.IGNORE_SILENT_MODE_KEY]
                ?: IGNORE_SILENT_MODE_DEFAULT
            val timeIntervalBetweenTimers =
                preferences[PreferencesKeys.TIME_INTERVAL_KEY]
                ?: TIME_INTERVAL_DEFAULT
            val useMediaVolumeHeadphones =
                preferences[PreferencesKeys.USE_MEDIA_VOLUME_HEADPHONES_KEY]
                ?: USE_MEDIA_VOLUME_HEADPHONES_DEFAULT
            UserPreferences(ignoreSilentMode, timeIntervalBetweenTimers, useMediaVolumeHeadphones)
        }

    override suspend fun updateIgnoreSilentMode(ignore: Boolean) {
        preferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.IGNORE_SILENT_MODE_KEY] = ignore
        }
    }

    override suspend fun updateTimerInterval(interval: Int) {
        preferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.TIME_INTERVAL_KEY] = interval
        }
    }

    override suspend fun updateUseMediaVolumeHeadphones(useMediaVolume: Boolean) {
        preferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.USE_MEDIA_VOLUME_HEADPHONES_KEY] = useMediaVolume
        }
    }
}

private const val IGNORE_SILENT_MODE_DEFAULT = true
private const val TIME_INTERVAL_DEFAULT = 0
private const val USE_MEDIA_VOLUME_HEADPHONES_DEFAULT = true