package com.android.taskstimer.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.android.taskstimer.TasksTimerApplication
import com.android.taskstimer.presentation.screens.board.BoardViewModel
import com.android.taskstimer.presentation.screens.home.HomeViewModel
import com.android.taskstimer.presentation.screens.timers.TimerAddViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                timersRepository = tasksTimerApplication().container.timersRepository,
                boardsRepository = tasksTimerApplication().container.boardsRepository
            )
        }
        initializer {
            TimerAddViewModel(
                timersRepository = tasksTimerApplication().container.timersRepository
            )
        }
        initializer {
            BoardViewModel(
                this.createSavedStateHandle(),
                timersRepository = tasksTimerApplication().container.timersRepository,
                boardsRepository = tasksTimerApplication().container.boardsRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.tasksTimerApplication(): TasksTimerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TasksTimerApplication)
