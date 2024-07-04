package com.android.taskstimer.data

import kotlinx.coroutines.flow.Flow

class OfflineTimersRepository(private val timerDao: TimerDao) : TimersRepository {
    override fun getAllTimersStream(): Flow<List<Timer>> = timerDao.getAllTimers()

    override fun getTimerStream(id: Int): Flow<Timer?> = timerDao.getTimer(id)

    override suspend fun insertTimer(item: Timer) = timerDao.insert(item)

    override suspend fun deleteTimer(timer: Timer) = timerDao.delete(timer)

    override suspend fun updateTimer(timer: Timer) = timerDao.update(timer)
}