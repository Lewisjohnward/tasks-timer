package com.android.taskstimer.core.repository

import com.android.taskstimer.core.data.local.TasksTimerDatabase
import com.android.taskstimer.core.data.mapper.toTimerEntity
import com.android.taskstimer.core.data.mapper.toTimerEntityForInsert
import com.android.taskstimer.core.data.mapper.toTimerItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeTimersRepositoryImpl(
    private val tasksTimerDb: TasksTimerDatabase
) : TimersRepository {

    private val timerDao = tasksTimerDb.timerDao

    override fun getAllTimersStream(): Flow<List<TimerItem>> =
        timerDao.getAllTimers().map { it -> it.map { it.toTimerItem() } }

    override fun getTimersFlow(boardId: Int): Flow<List<TimerItem>> =
        timerDao.getTimersFlow(boardId).map { it -> it.map { it.toTimerItem() } }

    override suspend fun getTimers(boardId: Int): List<TimerItem> =
        timerDao.getTimers(boardId).map { it.toTimerItem() }

    override fun getTimerStream(timerId: Int): Flow<TimerItem> =
        timerDao.getTimerStream(timerId).map { it.toTimerItem() }

    override suspend fun insertTimer(timer: TimerItem) =
        timerDao.insert(timer.toTimerEntityForInsert())


    override suspend fun updateTimer(timer: TimerItem) =
        timerDao.update(timer.toTimerEntity())

    override suspend fun updateTimers(timers: List<TimerItem>) =
        timerDao.updateTimers(timers.map { timer -> timer.toTimerEntity() })

    override suspend fun deleteTimer(timer: TimerItem) {
        timerDao.delete(timer.toTimerEntity())

    }

    override suspend fun deleteAllTimersFromBoard(boardId: Int) {
        return timerDao.deleteAllTimersFromBoard(boardId)
    }
}