package com.android.taskstimer.core.domain.repository

import com.android.taskstimer.core.domain.model.TimerItem
import kotlinx.coroutines.flow.Flow

interface TimersRepository {
    fun getAllTimersStream(): Flow<List<TimerItem>>
    fun getTimersFlow(boardId: Int): Flow<List<TimerItem>>

    suspend fun getTimers(boardId: Int): List<TimerItem>

    suspend fun insertTimer(timer: TimerItem)

    suspend fun updateTimer(timer: TimerItem)

    suspend fun updateTimers(timers: List<TimerItem>)

    suspend fun deleteTimer(timer: TimerItem)

    suspend fun deleteAllTimersFromBoard(boardId: Int)
}