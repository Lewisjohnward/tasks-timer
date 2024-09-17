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
import androidx.lifecycle.lifecycleScope
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.model.formatTime
import com.android.taskstimer.core.domain.model.resetTimer
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
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

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService() = this@TasksTimerService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    fun selectBoard(boardId: Int? = null) {
        lifecycleScope.launch {
            if (boardId == null) {
                val board = boardsRepo.getInitBoard()
                if (board != null) {
                    state.value = state.value.copy(
                        boardItem = board,
                        timers = timersRepo.getTimers(board.id)
                    )
                } else {
                    state.value = state.value.copy(
                        boardItem = BoardItem(name = ""),
                        timers = emptyList()
                    )
                }
            } else {
                state.value = state.value.copy(
                    boardItem = boardsRepo.getBoard(boardId),
                    timers = timersRepo.getTimers(boardId)
                )
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        println("service: onCreate")
        selectBoard()
        context = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("Intent recieved")
        val action = intent?.getStringExtra(SERVICE_ACTION)
        val timerIndex = intent?.getStringExtra(TIMER_INDEX)?.toInt()

        when (action) {
            START -> timerIndex?.let { index -> startTasksTimer(index) }
            PAUSE -> stopTimer()
            RESET -> timerIndex?.let { index -> resetTimer(index) }
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

    private fun startTasksTimer(timerIndex: Int) {
        lifecycleScope.launch {
            if (state.value.timers.isEmpty()) return@launch
            if (activeTimer != null) return@launch


            activeTimer = Timer()
            setCurrentTimer(timerIndex)
            setTimerActiveState(RUNSTATE.RUNNING)

            if (remainingTimeOfCurrentTimerIsZero()) {
                incrementCurrentTimer()
            }

            activeTimer?.schedule(object : TimerTask() {
                override fun run() {
                    decrementTime()
                    if (remainingTimeOfCurrentTimerIsZero()) {
                        if (lastTimerInList()) {
                            incrementCurrentTimer()
                        } else {
                            stopTimer()
                            resetTimers()
                            resetCurrentTimer()
                        }
                    }
                    if (isFgService) updateNotification()

//                    if (timeElapsed == 5) Mp.play(context)
//                    if (timeElapsed == 6) stopTimer()
                }
            }, 0, 100)

        }
    }

    private fun resetTimer(index: Int) {
        state.value = state.value.copy(
            timers = state.value.timers.mapIndexed { i, timer ->
                if (index == i) {
                    timer.resetTimer()
                } else timer
            }
        )
    }

    private fun lastTimerInList(): Boolean {
        return state.value.currentTimer < state.value.timers.size - 1
    }

    private fun incrementCurrentTimer() {
        state.value = state.value.copy(
            currentTimer = state.value.currentTimer + 1
        )
    }

    private fun setCurrentTimer(index: Int) {
        state.value = state.value.copy(currentTimer = index)
    }

    private fun setTimerActiveState(runState: RUNSTATE) {
        state.value = state.value.copy(active = runState)
    }

    private fun resetCurrentTimer() {
        state.value = state.value.copy(currentTimer = 0)
    }

    private fun resetTimers() {
        state.value = state.value.copy(
            timers = state.value.timers.map { timer -> timer.resetTimer() }
        )
    }

    private fun stopTimer() {
        activeTimer?.cancel()
        activeTimer = null
        setTimerActiveState(RUNSTATE.STOPPED)
    }

    private fun remainingTimeOfCurrentTimerIsZero(): Boolean {
        return state.value.timers[state.value.currentTimer].remainingTime.toInt() <= 0
    }

    private fun decrementTime() {
        println(state.value.currentTimer)
        state.value = state.value.copy(
            timers = state.value.timers.mapIndexed { index, timer ->
                if (index == state.value.currentTimer) {
                    val updatedTimer =
                        timer.copy(remainingTime = (timer.remainingTime.toInt() - 1).toString())
                    updatedTimer
                } else timer
            }
        )
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
