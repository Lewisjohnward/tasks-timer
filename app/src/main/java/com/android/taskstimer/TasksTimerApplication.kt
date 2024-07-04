package com.android.taskstimer

import android.app.Application
import com.android.taskstimer.data.AppContainer
import com.android.taskstimer.data.AppDataContainer

class TasksTimerApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}