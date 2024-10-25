package com.android.taskstimer._other.service

import com.android.taskstimer._other.mediaPlayer.MediaPlayerManager
import com.android.taskstimer._other.mediaPlayer.VibrateManager
import com.android.taskstimer.core.di.ApplicationScope
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.core.domain.model.resetTimer
import com.android.taskstimer.core.domain.repository.BoardsRepository
import com.android.taskstimer.core.domain.repository.TimersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton


data class TasksTimerManagerState(
    val active: RUNSTATE = RUNSTATE.STOPPED,
    val board: String = "",
    val timers: List<TimerItem> = emptyList(),
    val currentTimerIndex: Int = 0
)

@Singleton
class TasksTimerManager @Inject constructor(
    private val boardsRepo: BoardsRepository,
    private val timersRepo: TimersRepository,
    @ApplicationScope private val coroutineScope: CoroutineScope,
    private val singleThreadDispatcher: ExecutorCoroutineDispatcher,
    private val mediaPlayer: MediaPlayerManager,
    private val vibrator: VibrateManager,
    private val tasksTimerServiceManager: TasksTimerServiceManager
) {

    private val _timers = MutableStateFlow<List<TimerItem>>(emptyList())
    private val _currentTimerIndex = MutableStateFlow(0)
    private val _board = MutableStateFlow<String>("")
    private val _active = MutableStateFlow(RUNSTATE.STOPPED)
    private var timer: Job? = null

    // TODO: PUT IN PREFERENCES DATASTORE
    private val playSoundAtTimerFinish = true

    fun alertUserTimerFinished() {
        vibrator.vibrate()
        mediaPlayer.play()
    }

    val state = combine(
        _timers,
        _currentTimerIndex,
        _board,
        _active
    ) { timers, currentTimerIndex, board, active ->
        TasksTimerManagerState(
            active = active,
            board = board,
            timers = timers,
            currentTimerIndex = currentTimerIndex
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TasksTimerManagerState()
    )

    fun unloadBoard() {
        _timers.update { emptyList() }
        _board.update { "" }
    }

    fun loadBoard(boardId: Int) {
        coroutineScope.launch(
            context = singleThreadDispatcher
        ) {
            val timers = timersRepo.getTimers(boardId)
            val board = boardsRepo.getBoard(boardId)
            _timers.update { timers }
            _board.update { board.name }
        }
    }

    fun startTimer(timerIndex: Int) {
        if (state.value.timers.isEmpty()) return
        if (timer != null) return
        tasksTimerServiceManager.startTasksTimerService()

        _active.update { RUNSTATE.RUNNING }
        setCurrentTimer(timerIndex)
        setTimerActiveState(RUNSTATE.RUNNING)

        if (isRemainingTimerOfCurrentTimerZero()) {
            incrementCurrentTimer()
        }

        timer = coroutineScope.launch {
            while (true) {
                delay(1000L)
                if (isRemainingTimerOfCurrentTimerZero()) {
                    if (playSoundAtTimerFinish) alertUserTimerFinished()
                    saveLastEndedAt()
                    if (isNotLastTimerInList()) {
                        incrementCurrentTimer()
                    } else {
                        stopTimer()
                        resetAllTimers()
                        resetCurrentTimer()
                        tasksTimerServiceManager.stopTasksTimerService()
                    }
                } else {
                    decrementTime()
                }
//                    if (isFgService) updateNotification()

//                    if (timeElapsed == 6) stopTimer()
            }
        }
    }

    private fun saveLastEndedAt() {
        val timer: TimerItem = _timers.value[_currentTimerIndex.value]
        val updatedTimer: TimerItem =
            timer.copy(
                lastEndedAt = Calendar.getInstance().timeInMillis
            )

        coroutineScope.launch {
            timersRepo.updateTimer(updatedTimer)
            _timers.update { currentTimers ->
                currentTimers.mapIndexed { index, timer ->
                    if (index == _currentTimerIndex.value) {
                        updatedTimer
                    } else timer
                }
            }
        }
    }

    fun handleNotificationPause() {
        timer?.cancel()
        timer = null
        setTimerActiveState(RUNSTATE.STOPPED)
        tasksTimerServiceManager.stopTasksTimerService()
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
        setTimerActiveState(RUNSTATE.STOPPED)
        tasksTimerServiceManager.stopTasksTimerService()
    }

    fun resetTimer(index: Int) {

        val resetTimer: TimerItem = _timers.value[index].resetTimer()

        coroutineScope.launch {
            timersRepo.updateTimer(resetTimer)
            _timers.update { currentTimers ->
                currentTimers.mapIndexed { i, timer ->
                    if (index == i) {
                        resetTimer
                    } else timer
                }
            }
        }
    }

    private fun isNotLastTimerInList(): Boolean {
        return state.value.currentTimerIndex < state.value.timers.size - 1
    }

    private fun incrementCurrentTimer() {
        _currentTimerIndex.update { it + 1 }
    }

    private fun setCurrentTimer(index: Int) {
        _currentTimerIndex.update { index }
    }

    private fun setTimerActiveState(runState: RUNSTATE) {
        _active.update { runState }
    }

    private fun resetCurrentTimer() {
        _currentTimerIndex.update { 0 }
    }

    fun resetAllTimers() {
        repeat(_timers.value.size) {
            resetTimer(it)
        }

//        _timers.update { currentTimers ->
//            currentTimers.map { timer -> timer.resetTimer() }
//        }
    }

    private fun isRemainingTimerOfCurrentTimerZero(): Boolean {
        return state.value.timers[state.value.currentTimerIndex].remainingTime.toInt() == 0
    }

    private fun decrementTime() {
        val timer: TimerItem = _timers.value[_currentTimerIndex.value]
        val updatedTimer: TimerItem =
            timer.copy(
                remainingTime = (timer.remainingTime.toInt() - 1).toString(),
            )

        coroutineScope.launch {
            timersRepo.updateTimer(updatedTimer)
            _timers.update { currentTimers ->
                currentTimers.mapIndexed { index, timer ->
                    if (index == _currentTimerIndex.value) {
                        updatedTimer
                    } else timer
                }
            }
        }
    }
}