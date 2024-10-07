package com.android.taskstimer._other.service

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import com.android.taskstimer.core.data.repository.UserPreferencesRepository
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.model.formatTime
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import javax.inject.Inject


data class State(
    val active: RUNSTATE = RUNSTATE.STOPPED,
    val timers: List<TimerItem> = emptyList(),
    val boardItem: BoardItem = BoardItem(),
    val currentTimer: Int = 0,
)

enum class RUNSTATE {
    RUNNING,
    STOPPED
}

@AndroidEntryPoint
class TasksTimerService : LifecycleService() {


    companion object {
        const val CHANNEL_ID = "Stopwatch_Notifications"

        // Service Actions
        const val START = "START"
        const val PAUSE = "PAUSE"
        const val RESET = "RESET"
        const val GET_STATUS = "GET_STATUS"
        const val MOVE_TO_FOREGROUND = "MOVE_TO_FOREGROUND"
        const val MOVE_TO_BACKGROUND = "MOVE_TO_BACKGROUND"
        const val TIMER_INDEX = "TIMER_INDEX"

        // Intent Extras
        const val SERVICE_ACTION = "STOPWATCH_ACTION"
    }


    private var activeTimer: Timer? = null

    var state = mutableStateOf(State())
    private var isFgService: Boolean = false

    private lateinit var context: Context

    @Inject
    lateinit var updateTimer: UpdateTimer

    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    @Inject
    lateinit var notification: NotificationCompat.Builder

    @Inject
    lateinit var timersRepo: TimersRepository

    @Inject
    lateinit var boardsRepo: BoardsRepository

    @Inject
    lateinit var userPreferencesRepo: UserPreferencesRepository

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService() = this@TasksTimerService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("Intent recieved")
        val action = intent?.getStringExtra(SERVICE_ACTION)
        val timerIndex = intent?.getStringExtra(TIMER_INDEX)?.toInt()

        when (action) {
//            START -> timerIndex?.let { index -> startTasksTimer(index) }
//            PAUSE -> stopTimer()
//            RESET -> timerIndex?.let { index -> resetTimer(index) }
            MOVE_TO_FOREGROUND -> moveToForeground()
            MOVE_TO_BACKGROUND -> moveToBackground()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    //
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

    @SuppressLint("MissingPermission")
    // TODO: HANDLE PERMISSIONS
    private fun updateNotification() {
        notificationManager.notify(
            1,
            buildNotification()
        )
    }

    private fun buildNotification(): Notification {
        val currentTimer = state.value.currentTimer

        val initialTime = state.value.timers[currentTimer].presetTime.toInt()
        val elapsedTime = initialTime - state.value.timers[currentTimer].remainingTime.toInt()
        return notification
            .setContentTitle("${state.value.timers[currentTimer].name} -${currentTimer}/${state.value.timers.size}")
            .setContentText(state.value.timers[currentTimer].formatTime())
            .setProgress(initialTime, elapsedTime, false)
            .build()
    }
}
