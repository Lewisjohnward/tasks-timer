package com.android.taskstimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.android.taskstimer.data.AppViewModel
import com.android.taskstimer.presentation.TasksTimerApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appViewModel: AppViewModel by viewModels()
        setContent {
            val uiState by appViewModel.uiState.collectAsState()
            TasksTimerApp(uiState = uiState, onEvent = appViewModel::onEvent)

        }
    }


}