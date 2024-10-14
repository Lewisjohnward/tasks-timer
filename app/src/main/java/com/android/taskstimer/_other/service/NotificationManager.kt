package com.android.taskstimer._other.service

import androidx.core.app.NotificationCompat

interface NotificationManager {
    fun getBaseTimerServiceNotification(): NotificationCompat.Builder
    fun updateNotification(
        silentNotification: Boolean,
        boardName: String,
        timerName: String,
        totalTime: String,
        totalTimers: String,
        currentTimerIndex: String,
        timerRemaining: String
    )

    fun removeNotification()

}