package com.android.taskstimer.core.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.android.taskstimer._other.service.TasksTimerService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val appViewModel: AppViewModel by viewModels()
//        val scheduler = AndroidAlarmScheduler(this)
//        var alarmItem: AlarmItem? = null

        setContent {
//            val uiState by appViewModel.uiState.collectAsState()
//            HomeScreen()
//            AddTimer()
//            TasksTimerApp()
            Button(onClick = {
                val intent = Intent(this, TasksTimerService::class.java)
                intent.putExtra(
                    TasksTimerService.SERVICE_ACTION,
                    TasksTimerService.START_TASKS_TIMER
                )
                startService(intent)
            }) {
                Text(text = "start service")
            }
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
    }

}