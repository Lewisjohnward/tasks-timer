package com.android.taskstimer.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksTimerApp()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        println("onResume - view -> foreground")
//        val intent = Intent(this, TasksTimerService::class.java)
//        intent.putExtra(
//            TasksTimerService.SERVICE_ACTION,
//            TasksTimerService.SCREEN_IN_FOREGROUND
//        )
//        startService(intent)
//    }

//    override fun onPause() {
//        super.onPause()
//        println("onPause - view -> background")
//        val intent = Intent(this, TasksTimerService::class.java)
//        intent.putExtra(
//            TasksTimerService.SERVICE_ACTION,
//            TasksTimerService.SCREEN_IN_BACKGROUND
//        )
//        startService(intent)
//    }
}