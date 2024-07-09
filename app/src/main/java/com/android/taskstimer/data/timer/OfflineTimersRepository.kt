package com.android.taskstimer.data.timer

import com.android.taskstimer.data.timer.Timer
import com.android.taskstimer.data.timer.TimerDao
import com.android.taskstimer.data.timer.TimersRepository
import kotlinx.coroutines.flow.Flow

class OfflineTimersRepository(private val timerDao: TimerDao) : TimersRepository {
    override fun getAllTimersStream(): Flow<List<Timer>> = timerDao.getAllTimers()

    override fun getTimerStream(id: Int): Flow<Timer?> = timerDao.getTimer(id)

    override suspend fun insertTimer(item: Timer) = timerDao.insert(item)

    override suspend fun deleteTimer(timer: Timer) = timerDao.delete(timer)

    override suspend fun updateTimer(timer: Timer) = timerDao.update(timer)
}