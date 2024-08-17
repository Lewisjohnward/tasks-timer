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
    val timers: List<TimerItem> = emptyList(),
    val boardItem: BoardItem = BoardItem()
)

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
//    var timers: List<TimerItem> = listOf(
//
//    )

    var timers = mutableStateOf(
        listOf(
            TimerItem(
                id = 1836, boardId = 7423, name = "Kason", presetTime = "10", remainingTime = "10"
            ),
            TimerItem(
                id = 1836,
                boardId = 7423,
                name = "Clean something",
                presetTime = "20",
                remainingTime = "20"
            ),

            )
    )


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

    init {
//        selectBoard()
        println("init")
    }


    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService() = this@TasksTimerService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    fun setRunning() {
        running.value = !running.value
    }

    var running = mutableStateOf(false)

    fun selectBoard(boardId: Int? = null) {
        lifecycleScope.launch {
            if (boardId == null) {
                val board = boardsRepo.getInitBoard()
                if (board != null) {
                    state.value = state.value.copy(
                        boardItem = board,
                        timers = timersRepo.getTimers(board.id)
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

    fun reloadBoard(){
        lifecycleScope.launch {
            state.value = state.value.copy(
                timers = timersRepo.getTimers(state.value.boardItem.id)
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        selectBoard()
        context = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("Intent recieved")
        val action = intent?.getStringExtra(SERVICE_ACTION)
        println(action)
        when (action) {
            START_TASKS_TIMER -> startTasksTimer()
//            PAUSE -> pauseStopwatch()
//            RESET -> resetStopwatch()
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


    private fun startTasksTimer() {
        lifecycleScope.launch {
//            timers = timersRepo.getTimers(1)
            println(timers)
            if (timers.value.isEmpty()) return@launch
            if (activeTimer != null) return@launch
            activeTimer = Timer()
            activeTimer?.schedule(object : TimerTask() {
                override fun run() {
                    decrementTime()
                    if (remainingTimeOfCurrentTimerIsZero()) {
                        if (currentTimer < timers.value.size - 1) {
                            currentTimer++
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

    fun isRunning(): Boolean {
        return activeTimer != null
    }

    private fun resetCurrentTimer() {
        currentTimer = 0
    }

    private fun resetTimers() {
        timers.value = timers.value.map { timer -> timer.resetTimer() }
    }

    private fun stopTimer() {
        activeTimer?.cancel()
        activeTimer = null
    }

    private fun remainingTimeOfCurrentTimerIsZero(): Boolean {
        return timers.value[currentTimer].remainingTime.toInt() <= 0
    }

    private fun decrementTime() {
        timers.value = timers.value.mapIndexed() { index, timer ->
            if (index == currentTimer) {
                val updatedTimer =
                    timer.copy(remainingTime = (timer.remainingTime.toInt() - 1).toString())
                updatedTimer
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
        val initialTime = timers.value[currentTimer].presetTime.toInt()
        val elapsedTime = initialTime - timers.value[currentTimer].remainingTime.toInt()
        return notification
            .setContentTitle("${timers.value[currentTimer].name} -${currentTimer}/${timers.value.size}")
            .setContentText(timers.value[currentTimer].formatTime())
            .setProgress(initialTime, elapsedTime, false)
            .build()
    }
}
