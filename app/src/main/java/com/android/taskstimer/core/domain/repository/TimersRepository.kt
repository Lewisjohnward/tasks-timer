package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.domain.model.TimerItem
import kotlinx.coroutines.flow.Flow

interface TimersRepository {
    fun getAllTimersStream(): Flow<List<TimerItem>>
    suspend fun getTimers(id: Int): List<TimerItem>
    suspend fun insertTimer(timer: TimerItem)
//    suspend fun deleteTimer(timer: Timer)
    suspend fun updateTimer(timer: TimerItem)
}