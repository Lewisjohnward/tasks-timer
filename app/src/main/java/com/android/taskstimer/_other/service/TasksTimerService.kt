package com.android.taskstimer._other.service

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.android.taskstimer._other.mediaPlayer.Mp
import com.android.taskstimer.core.domain.model.TimerItem
import com.android.taskstimer.tasks_timer.domain.use_case.GetTimers
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@AndroidEntryPoint
class TasksTimerService(
) : LifecycleService() {

//    data class TimerItem(
//        val id: Int = 0,
//        val boardId: Int,
//        val name: String,
//        val presetTime: String,
//        val remainingTime: String = presetTime
//    )

    val currentBoard: List<TimerItem> = listOf(
        TimerItem(
            boardId = 0,
            name = "Clean sheets",
            presetTime = "5",
            remainingTime = "5"
        ),
        TimerItem(
            boardId = 0,
            name = "Clean sheets",
            presetTime = "3",
            remainingTime = "3"
        ),
        TimerItem(
            boardId = 0,
            name = "Clean sheets",
            presetTime = "4",
            remainingTime = "4"
        )
    )
    var isTimerActive = false
    var currentTimer = 0
    private lateinit var stopwatchTimer: Timer

    @Inject
    lateinit var getTimers: GetTimers

    private lateinit var context: Context

    private var myInt: Int = 0

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
        startTimer()
        return super.onStartCommand(intent, flags, startId)
    }


    private fun startTimer(){
        if (isTimerActive) return
        stopwatchTimer = Timer()
        var timeElapsed = 0
        stopwatchTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val stopwatchIntent = Intent()
                stopwatchIntent.action = "STOPWATCH_TICK"

                println(timeElapsed)
                timeElapsed++

                if (timeElapsed == 5) Mp.play(context)
                stopwatchIntent.putExtra("TIME_ELAPSED", timeElapsed)
                sendBroadcast(stopwatchIntent)
                if(timeElapsed == 6) stopTimer()
            }
        }, 0, 1000)
    }

    private fun stopTimer(){
        stopwatchTimer.cancel()
        isTimerActive = false
    }

}