package com.android.taskstimer.data

import android.content.Context
import com.android.taskstimer.data.board.BoardsRepository
import com.android.taskstimer.data.board.OfflineBoardsRepository
import com.android.taskstimer.data.timer.OfflineTimersRepository
import com.android.taskstimer.data.timer.TimersRepository

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