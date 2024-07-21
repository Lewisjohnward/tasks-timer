package com.android.taskstimer.core.data.repository

import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.mapper.toTimerEntityForInsert
import com.android.taskstimer.core.data.mapper.toTimerItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimersRepositoryImpl(
    private val tasksTimerDb: TasksTimerDatabase
) : TimersRepository {
    private val timerDao = tasksTimerDb.timerDao
    override fun getAllTimersStream(): Flow<List<TimerItem>> =
        timerDao.getAllTimers().map { it -> it.map { it.toTimerItem() } }

    override suspend fun getTimers(id: Int): List<TimerItem> =
        timerDao.getTimers(id).map { it.toTimerItem() }

    override suspend fun insertTimer(timer: TimerItem) = timerDao.insert(timer.toTimerEntityForInsert())

//    override suspend fun deleteTimer(timer: Timer) = timerDao.delete(timer)

    override suspend fun updateTimer(timer: TimerItem) = timerDao.update(timer.toTimerEntityForInsert())
}