package com.android.taskstimer._other.service

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.android.taskstimer._other.mediaPlayer.Mp
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimers
import com.android.taskstimer.tasks_timer.domain.use_case.UpdateTimer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@AndroidEntryPoint
class TasksTimerService(
) : LifecycleService() {

    var currentTimer = 0
    private var activeTimer: Timer? = null
    private lateinit var timers: List<TimerItem>


    @Inject
    lateinit var getTimers: GetTimers
    lateinit var updateTimer: UpdateTimer

    private lateinit var context: Context


    ////
    // TO DO: PASS BOARDID FROM UI TO SERVICE WITH INTENT TO GET CURRENT TIMERS
    // UPDATE TIMER ON EACH DECREMENT
    // CHANGE TIMERS TO FLOW ON FRONT END TO OBSERVE CHANGES
    // CHANGE TO FG SERVICE WHEN PAUSING UI
    // CHANGE FROM FG TO BG WHEN RESUMING UI


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
        println("onStartCommand")
        println(intent)
//        when (intent.action) {
//            is "start" -> start()
//            is "stop" -> stopSelf()
//        }
//        scopeTest()
//        getTimers()
        // PASS THE WHOLE BOARD TO THE SERVICE
        startTimer()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startTimer(){
        lifecycleScope.launch {
            timers = getTimers(boardId = 0)
//            println(timers)
            if (activeTimer != null) return@launch
            activeTimer = Timer()
            var timeElapsed = 0
            println(timers)

            activeTimer?.scheduleAtFixedRate(object : TimerTask() {
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

                    if (timeElapsed == 5) Mp.play(context)
                    if (timeElapsed == 6) stopTimer()
                }
            }, 0, 10)

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

}