package com.android.taskstimer._other.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.android.taskstimer.R
import com.android.taskstimer._other.mediaPlayer.Mp
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.presentation.MainActivity
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
    }


    var currentTimer = 0
    private var activeTimer: Timer? = null
    private lateinit var timers: List<TimerItem>
    private var isFgService: Boolean = false


    @Inject
    lateinit var getTimers: GetTimers
    lateinit var updateTimer: UpdateTimer
    private lateinit var notificationManager: NotificationManager

    private lateinit var context: Context


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

        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        getNotificationManager()
//
//        val action = intent?.getStringExtra(STOPWATCH_ACTION)!!
//
//        Log.d("Stopwatch", "onStartCommand Action: $action")
//

//
//        return START_STICKY
        val action = intent?.getStringExtra(SERVICE_ACTION)
        when (action) {
            START_TASKS_TIMER -> startTasksTimer()
//            PAUSE -> pauseStopwatch()
//            RESET -> resetStopwatch()
//            GET_STATUS -> sendStatus()
            MOVE_TO_FOREGROUND -> moveToForeground()
            MOVE_TO_BACKGROUND -> moveToBackground()
        }


        println("onStartCommand")
        println(intent)
//        when (intent.action) {
//            is "start" -> start()
//            is "stop" -> stopSelf()
//        }
//        scopeTest()
//        getTimers()
//        updateNotification()
//        startTimer()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun moveToForeground() {
        if(activeTimer == null) return
        isFgService = true
        startForeground(1, buildNotification())
    }

    private fun moveToBackground(){
        if(activeTimer == null) return
        isFgService = false
        stopForeground(STOP_FOREGROUND_REMOVE)
    }



//    private fun getNotificationManager() {
//        notificationManager = ContextCompat.getSystemService(
//            this,
//            NotificationManager::class.java
//        ) as NotificationManager
//    }

    private fun startTasksTimer() {
        lifecycleScope.launch {
            timers = getTimers(boardId = 0)
            if (timers.isEmpty()) return@launch
//            println(timers)
            if (activeTimer != null) return@launch
            activeTimer = Timer()
            var timeElapsed = 0
            println(timers)

            activeTimer?.schedule(object : TimerTask() {
                override fun run() {
//                val stopwatchIntent = Intent()
//                stopwatchIntent.action = "STOPWATCH_TICK"

                    decrementTime()
                    if (remainingTimeIsZero()) {
                        if (currentTimer < timers.size - 1) {
                            currentTimer++
                        } else stopTimer()
                    }

                    println(timers)
//                testBoard[currentTimer] = testBoard[currentTimer].copy(remainingTime = (testBoard[currentTimer].remainingTime.toInt() - 1).toString())
//                lifecycleScope.launch {
//                    updateTimer.invoke(updatedTimer)
//                }
//                printTimers()

//                updateTimer()

//                println(timeElapsed)
//                timeElapsed++
//
//                stopwatchIntent.putExtra("TIME_ELAPSED", timeElapsed)
//                sendBroadcast(stopwatchIntent)
                    if(isFgService) updateNotification()

                    if (timeElapsed == 5) Mp.play(context)
                    if (timeElapsed == 6) stopTimer()
                }
            }, 0, 1000)

        }
    }

    private fun printTimers() {
        lifecycleScope.launch {
            println(getTimers(boardId = 0))
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

    private fun updateNotification() {
        notificationManager.notify(
            1,
            buildNotification()
        )
    }

    private fun buildNotification(): Notification {
//        val title = if (isStopWatchRunning) {
//            "Stopwatch is running!"
//        } else {
//            "Stopwatch is paused!"
//        }
//
//        val hours: Int = timeElapsed.div(60).div(60)
//        val minutes: Int = timeElapsed.div(60)
//        val seconds: Int = timeElapsed.rem(60)

        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        println("Building notification")

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("${timers[currentTimer].name} ${currentTimer}/${timers.size}")
            .setOngoing(true)
            .setContentText(
                timers[currentTimer].remainingTime.toString()
//                "${"%02d".format(hours)}:${"%02d".format(minutes)}:${
//                    "%02d".format(
//                        seconds
//                    )
//                }"
            )
            .setColorized(true)
            .setShowWhen(false)
            .setColor(Color.parseColor("#BEAEE2"))
            .setSmallIcon(R.drawable.timer_add)
            .setOnlyAlertOnce(true)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .build()
    }

}