package com.android.taskstimer.data

import kotlinx.coroutines.flow.Flow

interface TimersRepository {
    fun getAllTimersStream(): Flow<List<Timer>>
    fun getTimerStream(id: Int): Flow<Timer?>
    suspend fun insertTimer(timer: Timer)
    suspend fun deleteTimer(timer: Timer)
    suspend fun updateTimer(timer: Timer)
}