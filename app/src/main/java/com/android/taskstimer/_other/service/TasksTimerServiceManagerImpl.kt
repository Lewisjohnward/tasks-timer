package com.android.taskstimer._other.service

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksTimerServiceManagerImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
): TasksTimerServiceManager{
    override fun startTasksTimerService() {
        val serviceIntent = Intent(applicationContext, TasksTimerService::class.java)
        ContextCompat.startForegroundService(applicationContext, serviceIntent)
    }

    override fun stopTasksTimerService() {
        val serviceIntent = Intent(applicationContext, TasksTimerService::class.java)
        applicationContext.stopService(serviceIntent)
    }
}