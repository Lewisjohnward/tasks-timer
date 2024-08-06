package com.android.taskstimer._other.service

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.android.taskstimer._other.mediaPlayer.Mp
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.model.formatTime
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimers
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@AndroidEntryPoint
class TasksTimerService : LifecycleService() {


    companion object {
        const val CHANNEL_ID = "Stopwatch_Notifications"

        // Service Actions
        const val START_TASKS_TIMER = "START"
        const val PAUSE = "PAUSE"
        const val RESET = "RESET"
        const val GET_STATUS = "GET_STATUS"
        const val MOVE_TO_FOREGROUND = "MOVE_TO_FOREGROUND"
        const val MOVE_TO_BACKGROUND = "MOVE_TO_BACKGROUND"

        // Intent Extras
        const val SERVICE_ACTION = "STOPWATCH_ACTION"
        var test = "Stopped"
    }


    var currentTimer = 0
    private var activeTimer: Timer? = null
    private lateinit var timers: List<TimerItem>
    private var isFgService: Boolean = false


    lateinit var updateTimer: UpdateTimer

    private lateinit var context: Context

    @Inject
    lateinit var getTimers: GetTimers

    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    @Inject
    lateinit var notification: NotificationCompat.Builder


    ////
    // TO DO: PASS BOARDID FROM UI TO SERVICE WITH INTENT TO GET CURRENT TIMERS
    // UPDATE TIMER ON EACH DECREMENT
    // CHANGE TIMERS TO FLOW ON FRONT END TO OBSERVE CHANGES
    // CHANGE TO FG SERVICE WHEN PAUSING UI
    // CHANGE FROM FG TO BG WHEN RESUMING UI
    // NOTIFICATION RUNTIME PERMISSION - BY DEFAULT DISABLED


    // Lets image it is board #0
    // Retrieve list of timers

//    var testBoard: List<TimerItem> = listOf(
//        TimerItem(
//            boardId = 0,
//            name = "Clean sheets",
//            presetTime = "5",
//            remainingTime = "5"
//        ),
//        TimerItem(
//            boardId = 0,
//            name = "Clean sheets",
//            presetTime = "3",
//            remainingTime = "3"
//        ),
//        TimerItem(
//            boardId = 0,
//            name = "Clean sheets",
//            presetTime = "4",
//            remainingTime = "4"
//        )
//    )

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra(SERVICE_ACTION)
        when (action) {
            START_TASKS_TIMER -> startTasksTimer()
//            PAUSE -> pauseStopwatch()
//            RESET -> resetStopwatch()
//            GET_STATUS -> sendStatus()
            MOVE_TO_FOREGROUND -> moveToForeground()
            MOVE_TO_BACKGROUND -> moveToBackground()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun moveToForeground() {
        if (activeTimer == null) return
        isFgService = true
        startForeground(1, buildNotification())
    }

    private fun moveToBackground() {
        if (activeTimer == null) return
        isFgService = false
        stopForeground(STOP_FOREGROUND_REMOVE)
    }


    private fun startTasksTimer() {
        lifecycleScope.launch {
            timers = getTimers(boardId = 0)
            if (timers.isEmpty()) return@launch
            if (activeTimer != null) return@launch
            test = "running"
            activeTimer = Timer()
            var timeElapsed = 0
            println(timers)

            activeTimer?.schedule(object : TimerTask() {
                override fun run() {
                    decrementTime()
                    if (remainingTimeIsZero()) {
                        if (currentTimer < timers.size - 1) {
                            currentTimer++
                        } else stopTimer()
                    }
                    println(timers)
                    if (isFgService) updateNotification()

                    if (timeElapsed == 5) Mp.play(context)
                    if (timeElapsed == 6) stopTimer()
                }
            }, 0, 1000)

        }
    }


    private fun stopTimer() {
        activeTimer?.cancel()
        activeTimer = null
    }

    private fun remainingTimeIsZero(): Boolean {
        return timers[currentTimer].remainingTime.toInt() == 0
    }

    private fun decrementTime() {
        timers = timers.mapIndexed() { index, timer ->
            if (index == currentTimer) {
                timer.copy(remainingTime = (timer.remainingTime.toInt() - 1).toString())
            } else timer
        }
    }

    @SuppressLint("MissingPermission")
    // TODO: HANDLE PERMISSIONS
    private fun updateNotification() {
        notificationManager.notify(
            1,
            buildNotification()
        )
    }

    private fun buildNotification(): Notification {
        val initialTime = timers[currentTimer].presetTime.toInt()
        val elapsedTime = initialTime - timers[currentTimer].remainingTime.toInt()
        return notification
            .setContentTitle("${timers[currentTimer].name} -${currentTimer}/${timers.size}")
            .setContentText(timers[currentTimer].formatTime())
            .setProgress(initialTime, elapsedTime, false)
            .build()
    }
}