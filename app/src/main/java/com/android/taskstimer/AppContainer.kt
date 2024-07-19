package com.android.taskstimer

import android.content.Context
import com.android.taskstimer.core.data.TasksTimerDatabase
import com.android.taskstimer.core.data.board.BoardsRepository
import com.android.taskstimer.core.data.board.OfflineBoardsRepository
import com.android.taskstimer.core.data.timer.OfflineTimersRepository
import com.android.taskstimer.core.data.timer.TimersRepository

interface AppContainer {
    val timersRepository: TimersRepository
    val boardsRepository: BoardsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val timersRepository: TimersRepository by lazy {
        OfflineTimersRepository(TasksTimerDatabase.getDatabase(context).timerDao())
    }

    override val boardsRepository: BoardsRepository by lazy {
        OfflineBoardsRepository(TasksTimerDatabase.getDatabase(context).boardDao())
    }
}