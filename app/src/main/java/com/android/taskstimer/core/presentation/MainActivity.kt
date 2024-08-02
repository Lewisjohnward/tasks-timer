package com.android.taskstimer.core.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
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
            TasksTimerApp()
        }
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onPause() {
        super.onPause()
        println("onPause - view -> background - start fg service IF timer is active")
        startService(Intent(this, TasksTimerService::class.java))
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

}