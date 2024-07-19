package com.android.taskstimer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.taskstimer.data.TasksTimerDatabase
import com.android.taskstimer.data.board.Board
import com.android.taskstimer.data.board.BoardsRepository
import com.android.taskstimer.data.board.BoardsWithTimers
import com.android.taskstimer.presentation.AppViewModelProvider
import com.android.taskstimer.presentation.screens.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksTimerService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartCommand")
        println(intent)
//        when (intent.action) {
//            is "start" -> start()
//            is "stop" -> stopSelf()
//        }
        return super.onStartCommand(intent, flags, startId)
    }


}