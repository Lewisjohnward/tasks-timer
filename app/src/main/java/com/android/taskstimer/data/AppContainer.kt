package com.android.taskstimer.data

import android.content.Context

interface AppContainer {
    val timersRepository: TimersRepository
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
}