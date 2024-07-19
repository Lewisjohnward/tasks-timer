package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.data.local.timer.Timer
import kotlinx.coroutines.flow.Flow

interface TimersRepository {
    fun getAllTimersStream(): Flow<List<Timer>>
    fun getTimerStream(id: Int): Flow<Timer?>
    suspend fun insertTimer(timer: Timer)
    suspend fun deleteTimer(timer: Timer)
    suspend fun updateTimer(timer: Timer)
}