package com.android.taskstimer._other.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.taskstimer._other.service.TasksTimerManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TasksTimerBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var tasksTimerManager: TasksTimerManager

    override fun onReceive(context: Context?, intent: Intent?) {
        val message: String = intent?.getStringExtra(BROADCAST_ACTION) ?: return
        when (message) {
            PAUSE_TIMER -> handleNotificationPause()
        }
    }

    private fun handleNotificationPause() {
        tasksTimerManager.handleNotificationPause()
    }


    companion object {
        const val BROADCAST_ACTION = "BROADCAST_ACTION"

        const val PAUSE_TIMER = "PAUSE_TIMER"
    }
}