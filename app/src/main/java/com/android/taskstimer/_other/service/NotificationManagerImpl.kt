package com.android.taskstimer._other.service

import android.annotation.SuppressLint
import android.app.Notification.FOREGROUND_SERVICE_IMMEDIATE
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.taskstimer.R
import com.android.taskstimer._other.alarm.TasksTimerBroadcastReceiver
import com.android.taskstimer.core.presentation.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

const val NOTIFICATION_COLOR = "#FFFFFF"

@Singleton
class NotificationManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationManager {
    private val notificationManager = NotificationManagerCompat.from(context)

    override fun getBaseTimerServiceNotification(): NotificationCompat.Builder {
        return createNotification()
    }

    init {
        createNotificationChannels()
    }


    @SuppressLint("MissingPermission")
    override fun updateNotification(
        silentNotification: Boolean,
        boardName: String,
        timerName: String,
        totalTime: String,
        totalTimers: String,
        currentTimerIndex: String,
        timerRemaining: String
    ) {
        val notification = createNotification(
            silent = silentNotification,
            boardName = boardName,
            timerName = timerName,
            timeLeft = timerRemaining,
            currentTimerIndex = currentTimerIndex,
            totalTimers = totalTimers
        ).build()

        notificationManager.notify(
            1,
            notification,
        )

    }

    override fun removeNotification() {
        // TODO: TIMER SERVICE NOTIFICATION ID
        notificationManager.cancel(1)
    }

    private fun createNotification(
        silent: Boolean = true,
        boardName: String = "board name placeholder",
        timerName: String = "",
        timeLeft: String = "",
        totalTimers: String = "4",
        currentTimerIndex: String = "1"
    ): NotificationCompat.Builder {

        val broadcastIntent = Intent(context, TasksTimerBroadcastReceiver::class.java)
            .putExtra(TasksTimerBroadcastReceiver.BROADCAST_ACTION, TasksTimerBroadcastReceiver.PAUSE_TIMER)

        val actionPausePendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val contentText: String = createContentText(
            timerName = timerName,
            timeLeft = timeLeft
        )

        val titleText: String = createTitleText(
            boardName = boardName,
            currentTimerIndex = currentTimerIndex,
            totalTimers = totalTimers
        )

        return baseNotification()
            .setContentTitle(titleText)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setProgress(initialTime, elapsedTime, false)
//            .setFullScreenIntent(pendingIntent, true)
            .addAction(R.drawable.timer_add, "Pause", actionPausePendingIntent)
            .setSilent(silent)
    }


    private fun baseNotification(): NotificationCompat.Builder {
        val intent = Intent(context, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, TasksTimerService.CHANNEL_ID)
            .setOngoing(true)
            .setColorized(true)
            .setShowWhen(false)
            .setColor(Color.parseColor(NOTIFICATION_COLOR))
            .setSmallIcon(R.drawable.timer_add)
            .setOnlyAlertOnce(true)
            .setContentIntent(pIntent)
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
            .setAutoCancel(true)
    }

    private fun createNotificationChannels() {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            TasksTimerService.CHANNEL_ID,
            "Counter",
            android.app.NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Used for the increment counter notifications"
        notificationManager.createNotificationChannel(channel)
    }

    private fun createTitleText(boardName: String, currentTimerIndex: String, totalTimers: String): String {
        return "$boardName - timer $currentTimerIndex/$totalTimers"
    }

    private fun createContentText(timerName: String, timeLeft: String): String{
        val formattedTimerName = if(timerName.length > 25){
            timerName.slice(0..25) + "..."
        }else timerName
        return "$formattedTimerName\n$timeLeft"
    }
}