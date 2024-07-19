package com.android.taskstimer

import android.app.Application
import com.android.taskstimer._other.mediaPlayer.Mp

class TasksTimerApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        Mp.setContext(this)
    }
}