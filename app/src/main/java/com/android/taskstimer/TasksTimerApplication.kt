package com.android.taskstimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.android.taskstimer._other.mediaPlayer.Mp
import com.android.taskstimer._other.service.TasksTimerService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TasksTimerApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TasksTimerService.CHANNEL_ID,
                "Counter",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for the increment counter notifications"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}