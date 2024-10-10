package com.android.taskstimer.core.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.android.taskstimer._other.service.TasksTimerService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var tasksTimerService: TasksTimerService
    private var isBound by mutableStateOf(false)

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val service = binder as TasksTimerService.MyBinder
            tasksTimerService = service.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                TasksTimerApp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("onResume - view -> background - stop fg service IF timer is active")
        val intent = Intent(this, TasksTimerService::class.java)
        intent.putExtra(
            TasksTimerService.SERVICE_ACTION,
            TasksTimerService.MOVE_TO_BACKGROUND
        )
        startService(intent)
    }

    override fun onPause() {
        super.onPause()
        println("onPause - view -> background - start fg service IF timer is active")

        val intent = Intent(this, TasksTimerService::class.java)
        intent.putExtra(
            TasksTimerService.SERVICE_ACTION,
            TasksTimerService.MOVE_TO_FOREGROUND
        )
        startService(intent)
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
        Intent(this, TasksTimerService::class.java).also {
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

}