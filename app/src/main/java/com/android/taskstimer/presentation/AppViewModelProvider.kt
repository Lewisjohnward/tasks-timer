package com.android.taskstimer.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.android.taskstimer.TasksTimerApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(tasksTimerApplication().container.timersRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.tasksTimerApplication(): TasksTimerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TasksTimerApplication)
