package com.android.taskstimer._other.service

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleService
import com.android.taskstimer.core.data.repository.UserPreferencesRepository
import com.android.taskstimer.core.domain.model.BoardItem
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.model.formatTime
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject


data class State(
    val active: RUNSTATE = RUNSTATE.STOPPED,
    val timers: List<TimerItem> = emptyList(),
    val boardItem: BoardItem = BoardItem(),
    val currentTimer: Int = 0,
)

// TODO THIS NEEDS TO BE IN TASKSTIMERMANAGER
enum class RUNSTATE {
    RUNNING,
    STOPPED,
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
        const val SCREEN_IN_BACKGROUND = "MOVE_TO_FOREGROUND"
        const val SCREEN_IN_FOREGROUND = "MOVE_TO_BACKGROUND"
        const val TIMER_INDEX = "TIMER_INDEX"
        const val LAUNCH_SERVICE = "LAUNCH SERVICE"
        const val STOP_SERVICE = "STOP SERVICE"

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
    lateinit var timersRepo: TimersRepository

    @Inject
    lateinit var boardsRepo: BoardsRepository

    @Inject
    lateinit var userPreferencesRepo: UserPreferencesRepository

    @Inject
    lateinit var tasksTimerManager: TasksTimerManager

    @Inject
    lateinit var notificationManager: NotificationManager

    private val serviceScope = CoroutineScope(SupervisorJob())


    // TODO: NEEDS TO GO INTO MAINACTIVITY
    // ONLY HEADS UP WHEN NOT IN FOREGROUND!
    private var silentNotification = true
    private var appOpen = false


    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        println("Intent recieved")
        val action = intent?.getStringExtra(SERVICE_ACTION)

        // TODO: CREATE ENUM TO REPRESENT?
        when (action) {
            SCREEN_IN_BACKGROUND -> println("screen moved to background")
            SCREEN_IN_FOREGROUND -> println("screen moved to foreground")
            LAUNCH_SERVICE -> launchService()
            STOP_SERVICE -> stopService()
        }
        return START_STICKY
    }

    private fun stopService(){
        stopSelf()
    }

    private fun launchService() {
        startForeground(
            // TODO: TIMER_SERVICE_NOTIFICATION_ID
            1,
            notificationManager.getBaseTimerServiceNotification().build()
        )

        serviceScope.launch {
            tasksTimerManager.state.collect { tasksTimerState ->
                if(tasksTimerState.active != RUNSTATE.RUNNING) {
                    return@collect
                }
                val timers = tasksTimerState.timers
                val currentTimerIndex = tasksTimerState.currentTimerIndex
                val timer = timers[currentTimerIndex]

                notificationManager.updateNotification(
                    silentNotification = silentNotification,
                    boardName = tasksTimerState.board,
                    timerName = timer.name,
                    totalTime = timer.presetTime,
                    totalTimers = timers.size.toString(),
                    currentTimerIndex = (tasksTimerState.currentTimerIndex + 1).toString(),
                    timerRemaining = timer.formatTime()
                )
            }
        }

    }


    //
    private fun moveToForeground() {
//        if (activeTimer == null) return
//        isFgService = true
//        startForeground(
//            1, buildNotification(
//                silent = silentNotification,
//                timer = timer
//            )
//        )
    }

    private fun moveToBackground() {
//        if (activeTimer == null) return
//        isFgService = false
        stopForeground(STOP_FOREGROUND_REMOVE)
    }
}
