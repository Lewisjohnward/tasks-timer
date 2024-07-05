package com.android.taskstimer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun AddTimer(
    viewModel: AddTimerViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    Column(){
        Text(text = "Add timer")
        Button(onClick = {viewModel.addTimer()}) {
            Text(text = "Add timer")
        }
    }
}