package com.android.taskstimer.core.data.repository

import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.local.timer.Timer
import com.android.taskstimer.core.data.local.timer.TimerDao
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.Flow

class TimersRepositoryImpl(
    private val tasksTimerDb: TasksTimerDatabase
) : TimersRepository {
    private val timerDao = tasksTimerDb.timerDao
    override fun getAllTimersStream(): Flow<List<Timer>> = timerDao.getAllTimers()

    override fun getTimerStream(id: Int): Flow<Timer?> = timerDao.getTimer(id)

    override suspend fun insertTimer(item: Timer) = timerDao.insert(item)

    override suspend fun deleteTimer(timer: Timer) = timerDao.delete(timer)

    override suspend fun updateTimer(timer: Timer) = timerDao.update(timer)
}