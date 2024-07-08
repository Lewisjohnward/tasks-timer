package com.android.taskstimer.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.taskstimer.presentation.screens.timers.TimerAddViewModel
import com.android.taskstimer.presentation.AppViewModelProvider


@Composable
fun AddTimer(
    viewModel: TimerAddViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    Column(){
        Text(text = "Add timer")
        Button(onClick = {viewModel.addTimer()}) {
            Text(text = "Add timer")
        }
    }
}