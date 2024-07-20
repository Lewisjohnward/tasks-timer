package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.domain.model.TimerItem
import kotlinx.coroutines.flow.Flow

interface TimersRepository {
    fun getAllTimersStream(): Flow<List<TimerItem>>
    fun getTimerStream(id: Int): Flow<TimerItem?>
    suspend fun insertTimer(timer: TimerItem)
//    suspend fun deleteTimer(timer: Timer)
    suspend fun updateTimer(timer: TimerItem)
}