package com.android.taskstimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

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
}